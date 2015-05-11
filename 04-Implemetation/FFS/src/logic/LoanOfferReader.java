package logic;

import java.util.List;

import domainLayer.Customer;
import domainLayer.LoanOffer;

public interface LoanOfferReader {

	public LoanOffer readLoanOffer(int loanOfferID);
	
	public Customer readCustomer(int customerID);
	
	public void addObserver(FFSObserver observer);
	
	public void notifyObservers();

  public List<Customer> readAllCustomers();
}
