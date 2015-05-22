package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import logging.ErrorTypes;
import logging.Logger;
import domainLayer.LoanOffer;

public class CsvPaymentPlanImpl implements Csv {
  
  private static final String DELIMITER = ",";
  private static final String NEWLINE = "\n";
  private static final String HEADERS = "Termin,Dato,Ydelse,Rente,Afdrag,Rest";
  private Logger logger = new Logger();

  @Override
  public void exportToCSV(LoanOffer loanOffer, String path) {

    try {
      BufferedWriter buffer = new BufferedWriter(new FileWriter(path));
      buffer.write(HEADERS);
      buffer.newLine();
      buffer.write(formattedPaymentPlan(loanOffer));
      buffer.close();
    } catch (IOException e) {
    	logger.log("IO Error", "Error writing csv payment plan file." + "\n" + e.getMessage(), ErrorTypes.ERROR);
    }
  }
  
  private String formattedPaymentPlan(LoanOffer loanOffer) {
    
    StringBuilder csvString = new StringBuilder();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = new GregorianCalendar();
    String dateAsString = loanOffer.getStartDate(); 
    Date date; 

    try { 
        date = simpleDateFormat.parse(dateAsString);
        calendar.setTime(date);   
    } catch (ParseException e) { 
      logger.log("Date format error", "Error using: " + simpleDateFormat, ErrorTypes.ERROR);
    }
    
    int termin = 0;
    loanOffer.setMontlyPayment(new LoanOfferGeneratorImpl().calculateMonthlyPayment(loanOffer.getLoanSize(), loanOffer.getPaymentInMonths(), loanOffer.getTotalInterestRate()));
                            //dumt men det var loesningen lige nu. vi burde tilfoeje ydelse til loanOffer table i databasen?
    double ydelse = loanOffer.getMontlyPayment();
    double rente = 0;
    double afdrag = 0;
    double rest = loanOffer.getLoanSize();
    double rentesats = loanOffer.getTotalInterestRate();
     
    for(int i=loanOffer.getPaymentInMonths(); i>=0; i--) {
      csvString.append(termin);
      csvString.append(DELIMITER);
      csvString.append(ydelse);
      csvString.append(DELIMITER);
      csvString.append(rente);
      csvString.append(DELIMITER);
      csvString.append(afdrag);
      csvString.append(DELIMITER);
      csvString.append(rest);
      csvString.append(DELIMITER);
      csvString.append(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1));
      csvString.append(NEWLINE);
      
      rente = rest*(rentesats/100)/12;
      afdrag = ydelse-rente;
      rest -= afdrag;
      termin++;
      calendar.add(Calendar.MONTH, 1);
    }
    
    return csvString.toString();
  }

}
