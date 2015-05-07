package logic;

import domainLayer.Customer;
import domainLayer.LoanOffer;

public interface LoanOfferReader {

	public LoanOffer readLoanOffer(int loanOfferID);
	
	public Customer readCustomer(int customerID);
}
