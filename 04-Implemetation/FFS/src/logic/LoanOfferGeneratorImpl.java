package logic;

import java.sql.SQLException;

import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferGeneratorImpl implements LoanOfferGenerator {

	LoanOffer loanOffer;
	Customer customer;
	CustomerDAO customerDAO;
	
	public LoanOfferGeneratorImpl() {
		
		customerDAO = new CustomerDAOImpl();
	}
	
	@Override
	public void createLoanOffer(LoanOffer loanOffer) {
		
	}

	@Override
	public void createCustomer(Customer customer) {
		
		try {
			customerDAO.createCustomer(customer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
