package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import dataLayer.LoanOfferDAO;
import dataLayer.LoanOfferDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferReaderImpl implements LoanOfferReader {

	CustomerDAO customerDAO;
	LoanOfferDAO loanOfferDAO;
	Connect connect;
	Connection connection;
	List<FFSObserver> observers = new ArrayList<>();
	
	public LoanOfferReaderImpl() {
		
		createConnection();
		customerDAO = new CustomerDAOImpl();
		loanOfferDAO = new LoanOfferDAOImpl();
	}
	
	@Override
	public LoanOffer readLoanOffer(int loanOfferID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<LoanOffer> readAllLoanOffers() {
		
		try {
			createConnection();
			return loanOfferDAO.readAllLoanOffers(connection);
		} catch (SQLException e) {
			return Collections.<LoanOffer>emptyList();
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
	      return customerList = new ArrayList<Customer>();
	    } finally {
	      closeConnection();
	    }
	  }
	 
	 private void createConnection() {
			try {
				connect = new ConnectImpl();
				connection = connect.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	 private void closeConnection() {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	 
	@Override
	public void addObserver(FFSObserver observer) {

		if (observer != null && !observers.contains(observer))
            observers.add(observer);
	}

	@Override
	public void notifyObservers() {

		for (FFSObserver observer : observers)
			observer.update();
	}



	
}
