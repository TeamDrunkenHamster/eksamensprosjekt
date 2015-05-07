package logic;

import dataLayer.CustomerDAO;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferGeneratorImpl implements LoanOfferGenerator {

	LoanOffer loanOffer;
	Customer customer;
	CustomerDAO customerDAO;
	
	public LoanOfferGeneratorImpl() {
		
		customerDAO = new CustomerDAOImpl;
	}
	
	@Override
	public void createLoanOffer(LoanOffer loanOffer) {
		
	}

	@Override
	public void createCustomer(Customer customer) {
		
		customerDAO.createCustomer(customer);
	}

}
