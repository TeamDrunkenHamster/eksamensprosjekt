package logic;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domainLayer.Car;
import domainLayer.Customer;
import domainLayer.LoanOffer;


public class CalculatorImplTest {
	
	private Calculator calc = new CalculatorImpl();
	private Customer customer = new Customer();
	private Car car = new Car();
	
	
	@Test
	public void periodeOver3Years(){
		
		car.setPrice(100_000);
		customer.setCPR("0000000000"); //creditRate A
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(20_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(40);		
		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(5.97, 2398.08, 11);
		
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(), 0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(),0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void periodeOf3Years(){
		car.setPrice(100_000);
		customer.setCPR("0000000000"); //creditRate A 
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(20_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(36);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(5.39, 2581.37, 10);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(),0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(),0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void periodeOf20Months(){
		car.setPrice(100_000);
		customer.setCPR("0000000000"); //creditRate A 
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(30_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(5.39, 3814.29, 10);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(),0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(),0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void downPaymentIsUnder20Percent(){
		car.setPrice(100_000);
		customer.setCPR("0000000000"); //creditRate A 
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(10);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer.setRejected(true);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getRejected(), actualLoanOffer.getRejected());
	}
	
	@Test
	public void downPaymentIs50Percent(){
		car.setPrice(100_000);
		customer.setCPR("0000000000"); //creditRate A 
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(50_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(4.84, 2701.53, 9);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(),0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(),0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void downPaymentIsAbove50Percent(){
		car.setPrice(100_000);
		customer.setCPR("0000000000"); //creditRate A 
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(60_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(4.84, 2161.23, 9);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(),0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(),0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void creditRatingB(){
		car.setPrice(100_000);
		customer.setCPR("0000000001"); //creditRate B
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(60_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(5.39, 2179.6, 10);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(),0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(), 0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void creditRatingC(){
		car.setPrice(100_000);
		customer.setCPR("0000000002"); //creditRate C
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(60_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer = setExpectedLoanOffer(5.94, 2198.06, 11);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		
		assertEquals(expectedLoanOffer.getTotalInterestRate(), actualLoanOffer.getTotalInterestRate(),0.01);
		assertEquals(expectedLoanOffer.getMonthlyPayment(), actualLoanOffer.getMonthlyPayment(),0.01);
		assertEquals(expectedLoanOffer.getApr(), actualLoanOffer.getApr(),0.01);
	}
	
	@Test
	public void creditRatingD(){
		car.setPrice(100_000);
		customer.setCPR("0000000003"); //creditRate D
		LoanOffer loanToBeCalulated = new LoanOffer();
		loanToBeCalulated.setDownPayment(60_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setPaymentInMonths(20);		
		LoanOffer expectedLoanOffer = new LoanOffer();
		
		expectedLoanOffer.setRejected(true);
		LoanOffer actualLoanOffer = calc.calculateLoanOffer(loanToBeCalulated);
		assertEquals(expectedLoanOffer.getRejected(), actualLoanOffer.getRejected());
	}
	
	private LoanOffer setExpectedLoanOffer(double apr, double monthlyPay,double totalInterestRate) {
		LoanOffer expected = new LoanOffer();
		expected.setApr(apr);
		expected.setMonthlyPayment(monthlyPay);
		expected.setTotalInterestRate(totalInterestRate);
		return expected;
	}
}
