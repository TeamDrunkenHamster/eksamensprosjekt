package logic;

import domainLayer.Customer;
import domainLayer.LoanOffer;

public interface LoanOfferGenerator {
	
	public void createLoanOffer(LoanOffer loanOffer);
	
	public void createCustomer(Customer customer);
	
	public void addObserver(FFSObserver observer);
	
	public void notifyObservers();
}
