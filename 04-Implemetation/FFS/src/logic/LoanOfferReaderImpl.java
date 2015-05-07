package logic;

import dataLayer.CustomerDAO;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferReaderImpl implements LoanOfferReader {

	CustomerDAO customerDAO;
	
	public LoanOfferReaderImpl() {
		customerDAO = CustomerDAOImpl;
	}
	
	@Override
	public LoanOffer readLoanOffer(int loanOfferID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer readCustomer(int customerID) {
		
		return customerDAO.readCustomer(customerID);
	}

	
}
