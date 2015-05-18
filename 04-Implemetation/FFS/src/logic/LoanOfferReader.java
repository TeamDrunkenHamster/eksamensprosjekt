package logic;

import java.util.List;

import domainLayer.Customer;
import domainLayer.LoanOffer;

public interface LoanOfferReader {

	public LoanOffer readLoanOffer(int loanOfferID);
	
	public List<LoanOffer> readAllLoanOffers();

	public Customer readCustomer(String CPR);

	public List<Customer> readAllCustomers();
}
