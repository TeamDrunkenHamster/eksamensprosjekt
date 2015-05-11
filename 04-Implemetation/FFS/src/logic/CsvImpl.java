package logic;

import java.io.FileWriter;
import java.io.IOException;
import domainLayer.LoanOffer;

public class CsvImpl implements Csv {
	
	public static final String DELIMITER = ",";
	public static final String NEWLINE = "\n";

	@Override
	public void exportToCSV(LoanOffer loanOffer, String path) {

		try {
			FileWriter writer = new FileWriter(path);
			writer.write(formattedCustomer(loanOffer));
			writer.close();
		} catch (IOException e) {
			// logger.log(e.getMessage) ?
		}
	}
	
	private String formattedCustomer(LoanOffer loanOffer) {
		
		StringBuilder csvString = new StringBuilder();
		
		csvString.append(loanOffer.getLoanID());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getLoanSize());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getTotalInterestRate());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getDownPayment());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getPaymentInMonths());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getApr());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getSalesman().getId());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCustomer().getId());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCustomer().getFirstName());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCustomer().getLastName());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCar().getModel());
		csvString.append(DELIMITER);
		csvString.append(loanOffer.getCar().getPrice());
		csvString.append(NEWLINE);
		
		return csvString.toString();
	}

}
