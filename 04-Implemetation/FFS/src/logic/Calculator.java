package logic;

import domainLayer.LoanOffer;

public interface Calculator {

	public LoanOffer calculate(LoanOffer loanOffer);
	
	public double getApr(LoanOffer loanOffer);
}
