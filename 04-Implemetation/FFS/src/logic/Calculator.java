package logic;

import domainLayer.LoanOffer;

public interface Calculator {

	public LoanOffer calculateLoanOffer(LoanOffer loanOffer);
	
	public double calculateApr(LoanOffer loanOffer);
}
