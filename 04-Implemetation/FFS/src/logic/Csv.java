package logic;

import domainLayer.LoanOffer;

public interface Csv {

	public void exportToCSV(LoanOffer loanOffer, String path);
}
