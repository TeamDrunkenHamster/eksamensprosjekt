package logic;

import java.sql.SQLException;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferGeneratorImpl implements LoanOfferGenerator {

	LoanOffer loanOffer;
	Customer customer;
	CustomerDAO customerDAO;
	Connect connect;
	
	public LoanOfferGeneratorImpl() {
		
		try {
			connect = new ConnectImpl();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customerDAO = new CustomerDAOImpl();
	}
	
	@Override
	public void createLoanOffer(LoanOffer loanOffer) {
		
	}

	@Override
	public void createCustomer(Customer customer) {
		
		try {
			
			customerDAO.createCustomer(connect.getConnection(), customer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
