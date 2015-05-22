package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logging.ErrorTypes;
import logging.Logger;
import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import dataLayer.LoanOfferDAO;
import dataLayer.LoanOfferDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferReaderImpl implements LoanOfferReader {

	private CustomerDAO customerDAO;
	private LoanOfferDAO loanOfferDAO;
	private Connect connect;
	private Connection connection;
	private Logger logger = new Logger();
	
	public LoanOfferReaderImpl() {
		
		createConnection();
		customerDAO = new CustomerDAOImpl();
		loanOfferDAO = new LoanOfferDAOImpl();
	}
	
	@Override
	public LoanOffer readLoanOffer(int loanOfferID) {
		// TODO Auto-generated method stub
		try {
			Calculator calc = new CalculatorImpl();
			createConnection();
			LoanOffer loanOffer = loanOfferDAO.readLoanOffer(connection, loanOfferID);
			loanOffer.setApr(calc.getApr(loanOffer));
			return loanOffer;
		} catch (SQLException e) {
			logger.log("Database error", "Error retrieving loan offers.\n" + e.getMessage(), ErrorTypes.ERROR);
			return new LoanOffer();
		} finally {
			closeConnection();
		}
		
	}
	
	@Override
	public List<LoanOffer> readAllLoanOffers() {
		
		try {
			createConnection();
			return loanOfferDAO.readAllLoanOffers(connection);
		} catch (SQLException e) {
			logger.log("Database error", "Error retrieving all loan offers.\n" + e.getMessage(), ErrorTypes.ERROR);
			return Collections.emptyList();
		} finally {
			closeConnection();
		}
	}

	@Override
	public Customer readCustomer(String CPR) {
		
		try {
			createConnection();
			return customerDAO.readCustomer(connection, CPR);
		} catch (SQLException e) {
			logger.log("Database error", "Error retrieving customer.\n" + e.getMessage(), ErrorTypes.ERROR);
			return new Customer();
		} finally {
			closeConnection();
		}
		
	}
	
	 @Override
	  public List<Customer> readAllCustomers() {
	   
	   List<Customer> customerList = null;
	    
	    try {
	      createConnection();
	      return customerDAO.readAllCustomers(connection);
	    } catch (SQLException e) {
	    	logger.log("Database error", "Error retrieving customers.\n" + e.getMessage(), ErrorTypes.ERROR);
	    	return Collections.emptyList();
	    } finally {
	      closeConnection();
	    }
	    
	  }
	 
	 private void createConnection() {
			try {
				connect = new ConnectImpl();
				connection = connect.getConnection();
			} catch (SQLException e) {
				logger.log("Database error", "Error creating connection.\n" + e.getMessage(), ErrorTypes.ERROR);
			}
		}

	 private void closeConnection() {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log("Database error", "Error closing connection.\n" + e.getMessage(), ErrorTypes.ERROR);
			}
		}
}
