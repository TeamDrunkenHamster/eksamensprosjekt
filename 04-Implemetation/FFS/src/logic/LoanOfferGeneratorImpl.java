package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.bank.InterestRate;
import com.ferrari.finances.dk.rki.CreditRator;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import dataLayer.SalesmanDAO;
import domainLayer.Car;
import domainLayer.Customer;
import domainLayer.LoanOffer;
import domainLayer.Salesman;

public class LoanOfferGeneratorImpl implements LoanOfferGenerator {

	private API api;
	private LoanOffer loanOffer;
	private Customer customer;
	private Car car;
	private Salesman salesman;
	private CustomerDAO customerDAO;
	private CarDAO carDAO;
	private SalesmanDAO salesmanDAO;
	private LoanOfferDAO loanOfferDAO;
	private Connect connect;
	private Connection connection;
	private List<FFSObserver> observers = new ArrayList<>();
	
	public LoanOfferGeneratorImpl() {
		
		createConnection();
		customerDAO = new CustomerDAOImpl();
		loanOfferDAO = new LoanOfferDAOImpl();
		salesmanDAO = new SalesmanDAOImpl();
		carDAO = new CarDAOImpl();
		api = new APIImpl();
	}

	private void createConnection() {
		
		try {
			connect = new ConnectImpl();
			connection = connect.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void createLoanOffer(LoanOffer loanOffer) {
		
		createConnection();
		double bankRate;
		
		this.customer = loanOffer.getCustomer();
		
		int badStanding = getHistory(customer.getId());
		
		if (badStanding == 0) {
			rejectOffer();
			return;
		}
		
		Thread bankRateThread = new Thread() {

			@Override
			public void run() {

				bankRate = InterestRate.i().todaysRate();
			}
		};
		
		Thread creditRateThread = new Thread() {

			@Override
			public void run() {

				loanOffer.setCreditRating(CreditRator.i().rate(loanOffer.getCprNumber()).toString());
			}
		};
		
		bankRateThread.start();
		creditRateThread.start();
		
		bankRateThread.join();
		creditRateThread.join();
		
		
		calculateLoanOffer(bankRate);
		
		if (badStanding == -1)
			customerDAO.createCustomer(connection, customer);
		
		loanOfferDAO.createLoanOffer(loanOffer);
		
	}

	private void calculateLoanOffer(double bankRate) {
		
		double totalInterestRate = bankRate;
		if (loanOffer.getCreditRating() == "A")
			totalInterestRate += 1.0;
		else if (loanOffer.getCreditRating() == "B")
			totalInterestRate += 2.0;
		else
			totalInterestRate += 3.0;
		
		
	}

	private void rejectOffer() {
		
		loanOffer.setRejected(true);
		// setRandom = null?
	}

	@Override
	public void createCustomer(Customer customer) {
		
		createConnection();
		try {
			customerDAO.createCustomer(connection, customer);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				closeConnection();
		}
		
		notifyObservers();
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
	
	private boolean getCustomerStanding(Connection connection, String CPR ){
		try {
			Customer temp = customerDAO.readCustomer(this.connection, CPR);
			if(temp.getBadStanding() >= 1){
				return true;
			}else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
