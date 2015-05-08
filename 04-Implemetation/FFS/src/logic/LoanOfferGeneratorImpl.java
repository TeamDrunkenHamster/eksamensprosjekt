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

public class LoanOfferGeneratorImpl implements LoanOfferGenerator {

	LoanOffer loanOffer;
	Customer customer;
	CustomerDAO customerDAO;
	Connect connect;
	List<FFSObserver> observers = new ArrayList<>();
	
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
		
		notifyObservers();
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
