package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;

public class LoanOfferReaderImpl implements LoanOfferReader {

	CustomerDAO customerDAO;
	Connect connect;
	List<FFSObserver> observers = new ArrayList<>();
	
	public LoanOfferReaderImpl() {
		
		try {
			connect = new ConnectImpl();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			return customerDAO.readCustomer(connect.getConnection(), customerID);
		} catch (SQLException e) {
			return new Customer();
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
