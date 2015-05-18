package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ferrari.finances.dk.bank.InterestRate;
import com.ferrari.finances.dk.rki.CreditRator;

import dataLayer.CarDAO;
import dataLayer.CarDAOImpl;
import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import dataLayer.LoanOfferDAO;
import dataLayer.LoanOfferDAOImpl;
import dataLayer.SalesmanDAO;
import dataLayer.SalesmanDAOImpl;
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
	double bankRate;

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

		this.customer = loanOffer.getCustomer();

		boolean badStanding = getCustomerStanding(connection,
				customer.getCPR());

		if (badStanding) {
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

				loanOffer.setCreditRating(CreditRator.i()
						.rate(loanOffer.getCprNumber()).toString());
			}
		};

		bankRateThread.start();
		creditRateThread.start();

		try {
			bankRateThread.join();
			creditRateThread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		calculateLoanOffer(bankRate);
		try {
			loanOfferDAO.createLoanOffer(connection, loanOffer);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void calculateLoanOffer(double bankRate) {

		double totalInterestRate = bankRate;
		if (loanOffer.getCreditRating() == "A")
			totalInterestRate += 1.0;
		else if (loanOffer.getCreditRating() == "B")
			totalInterestRate += 2.0;
		else
			totalInterestRate += 3.0;

		loanOffer.setTotalInterestRate(totalInterestRate);
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

	private boolean getCustomerStanding(Connection connection, String CPR) {

		try {
			return customerDAO.readCustomer(this.connection, CPR)
					.getBadStanding();
		} catch (SQLException e) {
			return false;
		}
	}
}
