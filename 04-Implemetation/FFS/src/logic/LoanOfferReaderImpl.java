package logic;

import java.sql.SQLException;

import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferReaderImpl implements LoanOfferReader {

	CustomerDAO customerDAO;
	
	public LoanOfferReaderImpl() {
		
		customerDAO = new CustomerDAOImpl();
	}
	
	@Override
	public LoanOffer readLoanOffer(int loanOfferID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer readCustomer(int customerID) {
		
		try {
			return customerDAO.readCustomer(customerID);
		} catch (SQLException e) {
			return new Customer();
		}
	}

	
}
