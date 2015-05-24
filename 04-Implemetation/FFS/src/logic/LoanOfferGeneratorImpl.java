package logic;

import java.sql.Connection;
import java.sql.SQLException;

import logging.ErrorTypes;
import logging.Logger;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAO;
import dataLayer.CustomerDAOImpl;
import dataLayer.LoanOfferDAO;
import dataLayer.LoanOfferDAOImpl;
import dataLayer.SalesmanDAO;
import dataLayer.SalesmanDAOImpl;
import domainLayer.Customer;
import domainLayer.LoanOffer;
import domainLayer.Salesman;

public class LoanOfferGeneratorImpl implements LoanOfferGenerator {

	private LoanOffer loanOffer;
	private Customer customer;
	private Salesman salesman;
	private CustomerDAO customerDAO;
	private SalesmanDAO salesmanDAO;
	private LoanOfferDAO loanOfferDAO;
	private Connect connect;
	private Connection connection;
	private Logger logger = new Logger();

	public LoanOfferGeneratorImpl() {

		createConnection();
		customerDAO = new CustomerDAOImpl();
		loanOfferDAO = new LoanOfferDAOImpl();
		salesmanDAO = new SalesmanDAOImpl();
	}

	private void createConnection() {

		try {
			connect = new ConnectImpl();
			connection = connect.getConnection();
		} catch (SQLException e) {
			logger.log("Connection error", "Error while connecting to database.\n" + e.getMessage(), ErrorTypes.ERROR);
		}
	}

	@Override
	public void createLoanOffer(LoanOffer inputLoanOffer) {

		boolean salesmanExists = false;
		this.loanOffer = inputLoanOffer;
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
			logger.log("Database error", "Error while inspecting customer.\n" + e.getMessage(), ErrorTypes.ERROR);
		}
		
		this.customer = loanOffer.getCustomer();
		try {
			this.salesman = salesmanDAO.readSalesman(connection, loanOffer.getSalesman().getId());
			salesmanExists = true;
		} catch (SQLException e2) {
			logger.log("Database error", "Error while getting salesman.\n" + e2.getMessage(), ErrorTypes.ERROR);
		}
		
		boolean badStanding = getCustomerStanding(connection, loanOffer.getCustomer().getCPR());

		if (badStanding) {
			rejectOffer();
			return;
		}

		Calculator calc = new CalculatorImpl();
		loanOffer = calc.calculateLoanOffer(loanOffer);
		
		
		if (loanOffer.getLoanSize() < salesman.getLoanValueLimit())
			loanOffer.setApprovedStatus(true);
		
		
		try {
			if (!salesmanExists)
				salesmanDAO.createSalesman(connection, salesman);
			if (!loanOffer.getRejected())
				loanOfferDAO.createLoanOffer(connection, loanOffer);
		} catch (SQLException e) {
			logger.log("Database error", "Error while creating loan offer.\n" + e.getMessage(), ErrorTypes.ERROR);
		}
		closeConnection();
		ObserverSingleton.instance().notifyObservers();
	}


	private void rejectOffer() {

		loanOffer.setRejected(true);
		// setRandom = null?
	}

	private void closeConnection() {
		
		try {
			connection.close();
		} catch (SQLException e) {
			logger.log("Database error", "Error closing connection to database.\n" + e.getMessage(), ErrorTypes.ERROR);
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
