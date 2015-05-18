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
		
		try {
			Customer tempCustomer = customerDAO.readCustomer(connection, loanOffer.getCustomer().getCPR());
			if (tempCustomer.getCPR() != null) {
				loanOffer.setCustomer(tempCustomer);
			}
			else {
				loanOffer.getCustomer().setId(customerDAO.createCustomer(connection, loanOffer.getCustomer()));
			}
		} catch (SQLException e) {
		}
		
		this.customer = loanOffer.getCustomer();
		this.salesman = loanOffer.getSalesman();
		this.salesman.setLoanValueLimit(1000000);
		
		boolean badStanding = getCustomerStanding(connection, loanOffer.getCustomer().getCPR());

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
				String creditRating = CreditRator.i().rate(customer.getCPR()).toString();
				loanOffer.setCreditRating(creditRating);
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

//		calculateLoanOffer(bankRate);
		try {
			
			salesmanDAO.createSalesman(connection, salesman);
			loanOfferDAO.createLoanOffer(connection, loanOffer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ObserverSingleton.instance().notifyObservers();
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
	public int createCustomer(Customer customer) {

		createConnection();
		try {
			return customerDAO.createCustomer(connection, customer);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				closeConnection();
		}
		ObserverSingleton.instance().notifyObservers();
		return -1;
	}

	private void closeConnection() {
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
