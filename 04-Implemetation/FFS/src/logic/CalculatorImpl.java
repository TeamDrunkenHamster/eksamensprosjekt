package logic;

import logging.ErrorTypes;
import logging.Logger;

import com.ferrari.finances.dk.bank.InterestRate;
import com.ferrari.finances.dk.rki.CreditRator;

import domainLayer.LoanOffer;

public class CalculatorImpl implements Calculator {
	
	private LoanOffer loanOffer;
	private double bankRate;
	private Logger logger = new Logger();

	@Override
	public LoanOffer calculate(LoanOffer inputLoanOffer) {
		
		this.loanOffer = inputLoanOffer;
		Thread bankRateThread = getInterestRate();
		Thread creditRateThread = getCreditRate(loanOffer.getCustomer().getCPR());
		bankRateThread.start();
		creditRateThread.start();
		try {
			bankRateThread.join();
			creditRateThread.join();
		} catch (InterruptedException e) {
			logger.log("Threading error", "Connection to bank or RKI failed.", ErrorTypes.ERROR);
		}
		loanOffer.setTotalInterestRate(bankRate);
		calculateTotalInterestRate();
		loanOffer.setLoanSize(loanOffer.getCar().getPrice()-loanOffer.getDownPayment());
		loanOffer.setMonthlyPayment(calculateMonthlyPayment(loanOffer.getLoanSize(), loanOffer.getPaymentInMonths(), loanOffer.getTotalInterestRate()));
		return loanOffer;
	}
	
	private void calculateTotalInterestRate() {

		double totalInterestRate = loanOffer.getTotalInterestRate();
		if (loanOffer.getCreditRating() == "A")
			totalInterestRate += 1.0;
		else if (loanOffer.getCreditRating() == "B")
			totalInterestRate += 2.0;
		else
			totalInterestRate += 3.0;
		
		if (loanOffer.getDownPayment() < 0.5*loanOffer.getCar().getPrice()) //Hvis udbetalingen er mindre 50% af bilens pris, haeves total rentesats med 1%.
		  totalInterestRate += 1.0;
		
		if (loanOffer.getDownPayment() < 0.2*loanOffer.getCar().getPrice()) //Hvis udbetalen er mindre end 20% af bilens pris, afvis tilbud.
		  loanOffer.setRejected(true);
		
		if (loanOffer.getPaymentInMonths() > 36) //Hvis tilbagebetalingsperioden er mere end 3 aar.
		  totalInterestRate += 1.0;

		loanOffer.setTotalInterestRate(totalInterestRate);
	}
	
	private double calculateMonthlyPayment(double loanAmount, int termInMonths, double interestRate) {
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

	@Override
	public double getApr(LoanOffer inputLoanOffer) {

		double sumOfPayments = (inputLoanOffer.getMonthlyPayment() * inputLoanOffer.getPaymentInMonths());
		double oip = (sumOfPayments - inputLoanOffer.getLoanSize()) / inputLoanOffer.getLoanSize();
		double apr = oip / inputLoanOffer.getPaymentInMonths() * 12;
		return apr;
	}
}
