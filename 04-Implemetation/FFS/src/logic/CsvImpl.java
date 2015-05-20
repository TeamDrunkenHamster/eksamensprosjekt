package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import domainLayer.LoanOffer;

public class CsvImpl implements Csv {
	
	private static final String DELIMITER = ",";
	private static final String NEWLINE = "\n";
	private static final String HEADERS = "LoanID,CPRNumber,CustomerFirstName,CustomerLastName,"
			+ "SalesmanID,CarModel,LoanSize,Downpayment,TotalInterestRate,APR,PaymentInMonths,StartDate,"
			+ "ApprovalStatus,RejectedStatus";

	@Override
	public void exportToCSV(LoanOffer loanOffer, String path) {

		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(path));
			buffer.write(HEADERS);
			buffer.newLine();
			buffer.write(formattedCustomer(loanOffer));
			buffer.close();
		} catch (IOException e) {
			System.out.println("crap");
		}
	}
	
	private String formattedCustomer(LoanOffer loanOffer) {
		
		StringBuilder csvString = new StringBuilder();
		
		csvString.append(loanOffer.getLoanID());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCustomer().getCPR());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCustomer().getFirstName());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCustomer().getLastName());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getSalesman().getId());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCar().getModel());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getLoanSize());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getDownPayment());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getTotalInterestRate());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getApr());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getPaymentInMonths());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getStartDate());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getApprovedStatus());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getRejected());
		csvString.append(NEWLINE);
		
		return csvString.toString();
	}

}
