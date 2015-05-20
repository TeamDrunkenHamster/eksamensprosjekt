package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import domainLayer.LoanOffer;

public class CsvPaymentPlanImpl implements Csv {
  
  private static final String DELIMITER = ",";
  private static final String NEWLINE = "\n";
  private static final String HEADERS = "LoanID,CarPrice,LoanSize,InterestRate,PaymentPeriod,MonthlyPaymentWithInterest,StartDate,";

  @Override
  public void exportToCSV(LoanOffer loanOffer, String path) {

    try {
      BufferedWriter buffer = new BufferedWriter(new FileWriter(path));
      buffer.write(HEADERS + createPaymentPlanHeaders(loanOffer));
      buffer.newLine();
      buffer.write(formattedPaymentPlan(loanOffer));
      buffer.close();
    } catch (IOException e) {
      System.out.println("shit");
    }
  }
  
  private String createPaymentPlanHeaders(LoanOffer loanOffer) {
    
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = new GregorianCalendar();
    String dateAsString = loanOffer.getStartDate(); 
    Date date; 

    try { 
        date = simpleDateFormat.parse(dateAsString);
        cal.setTime(date);   
    } catch (ParseException e) { 
        System.out.println("Error using: " + simpleDateFormat); 
    }
    
    StringBuilder paymentPlanHeaders = new StringBuilder();
    
    for(int i=loanOffer.getPaymentInMonths(); i>0; i--) {
      paymentPlanHeaders.append(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + ",");
      cal.add(Calendar.MONTH, 1);
    }
    
    return paymentPlanHeaders.toString();
  }
  
  private String formattedPaymentPlan(LoanOffer loanOffer) {
    
    StringBuilder csvString = new StringBuilder();
    
    csvString.append(loanOffer.getLoanID());
    csvString.append(DELIMITER);
    csvString.append(loanOffer.getCar().getPrice());
    csvString.append(DELIMITER);
    csvString.append(String.valueOf(loanOffer.getLoanSize()));
    csvString.append(DELIMITER);
    csvString.append(String.valueOf(loanOffer.getTotalInterestRate()));
    csvString.append(DELIMITER);
    csvString.append(String.valueOf(loanOffer.getPaymentInMonths()));
    csvString.append(DELIMITER);
    csvString.append(loanOffer.getMontlyRepaymentPlusInterest());
    csvString.append(DELIMITER);
    csvString.append(loanOffer.getStartDate());
    csvString.append(DELIMITER);
    
    double remaining = loanOffer.getLoanSize()*(1+(loanOffer.getTotalInterestRate()/100));
//    double payment = loanOffer.getMontlyRepaymentPlusInterest();
    double payment = 10500; //test, da jeg ikke kan genere loans lige nu
    for(int i=loanOffer.getPaymentInMonths(); i>0; i--) {
      csvString.append(remaining);
      remaining -= payment;
      csvString.append(DELIMITER);
    }
    
    csvString.append(NEWLINE);
    
    return csvString.toString();
  }

}
