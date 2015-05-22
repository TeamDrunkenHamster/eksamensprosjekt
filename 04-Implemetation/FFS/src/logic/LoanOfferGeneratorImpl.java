package logic;

import java.sql.Connection;
import java.sql.SQLException;

import logging.ErrorTypes;
import logging.Logger;

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

	private LoanOffer loanOffer;
	private Customer customer;
	private Salesman salesman;
	private CustomerDAO customerDAO;
	private SalesmanDAO salesmanDAO;
	private LoanOfferDAO loanOfferDAO;
	private Connect connect;
	private Connection connection;
	private double bankRate;
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

		Thread bankRateThread = getInterestRate();

		Thread creditRateThread = getCreditRate(loanOffer.getCustomer().getCPR());

		bankRateThread.start();
		creditRateThread.start();

		try {
			bankRateThread.join();
			creditRateThread.join();
		} catch (InterruptedException e1) {
			logger.log("Threading error", "Bank or RKI connection has been interrupted.\n" + e1.getMessage(), ErrorTypes.ERROR);
		}
		
		calculateTotalInterestRate(bankRate);
		loanOffer.setLoanSize(loanOffer.getCar().getPrice()-loanOffer.getDownPayment()); //loanOffer mangler en bil tror jeg
		loanOffer.setMontlyPayment(calculateMonthlyPayment(loanOffer.getLoanSize(), loanOffer.getPaymentInMonths(), loanOffer.getTotalInterestRate()));
		
		if (loanOffer.getLoanSize() < salesman.getLoanValueLimit())
			loanOffer.setApprovedStatus(true);
		
		
		try {
			if (!salesmanExists)
				salesmanDAO.createSalesman(connection, salesman);
			loanOfferDAO.createLoanOffer(connection, loanOffer);
		} catch (SQLException e) {
			logger.log("Database error", "Error while creating loan offer.\n" + e.getMessage(), ErrorTypes.ERROR);
		}
		closeConnection();
		ObserverSingleton.instance().notifyObservers();
	}

	private Thread getInterestRate() {
		Thread bankRateThread = new Thread() {

			@Override
			public void run() {

				bankRate = InterestRate.i().todaysRate();
			}
		};
		return bankRateThread;
	}

	private Thread getCreditRate(String cpr) {
		Thread creditRateThread = new Thread() {

			@Override
			public void run() {
				String creditRating = CreditRator.i().rate(cpr).toString();
				loanOffer.setCreditRating(creditRating);
			}
		};
		return creditRateThread;
	}

	private void calculateTotalInterestRate(double bankRate) {

		double totalInterestRate = bankRate;
		if (loanOffer.getCreditRating() == "A")
			totalInterestRate += 1.0;
		else if (loanOffer.getCreditRating() == "B")
			totalInterestRate += 2.0;
		else
			totalInterestRate += 3.0;
		
		if (loanOffer.getDownPayment() < 0.5*loanOffer.getCar().getPrice()) //Hvis udbetalingen er mindre 50% af bilens pris, haeves total rentesats med 1%.
		  totalInterestRate += 1.0;
		
		if (loanOffer.getDownPayment() < 0.2*loanOffer.getCar().getPrice()) //Hvis udbetalen er mindre end 20% af bilens pris, afvis tilbud.
		  rejectOffer();
		
		if (loanOffer.getPaymentInMonths() > 36) //Hvis tilbagebetalingsperioden er mere end 3 aar.
		  totalInterestRate += 1.0;

		loanOffer.setTotalInterestRate(totalInterestRate);
	}
	
	public double calculateMonthlyPayment(double loanAmount, int termInMonths, double interestRate) {
		// Convert interest rate into a decimal
		// eg. 6.5% = 0.065
		interestRate /= 100.0;
		// Monthly interest rate
		// is the yearly rate divided by 12
		double monthlyRate = interestRate / 12.0;
		// The length of the term in months
		// is the number of years times 12
		//int termInMonths = termInYears * 12;
		// Calculate the monthly payment
		// Typically this formula is provided so
		// we won't go into the details
		// The Math.pow() method is used calculate values raised to a power
		double monthlyPayment = (loanAmount * monthlyRate)
				/ (1 - Math.pow(1 + monthlyRate, -termInMonths));
		return monthlyPayment;
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
