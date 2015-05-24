package logic;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domainLayer.Car;
import domainLayer.Customer;
import domainLayer.LoanOffer;


public class LoanOfferGeneratorTest {
	
	private Calculator calc = new CalculatorImpl();
	private Customer customer = new Customer();
	private Car car = new Car();
	
	
	@Test
	public void periodeOver3Years(){
		
		car.setPrice(100_000);
		
		LoanOffer loanToBeCalulated = new LoanOffer();
		
		loanToBeCalulated.setDownPayment(20_000);
		loanToBeCalulated.setCustomer(customer);
		loanToBeCalulated.setCar(car);
		loanToBeCalulated.setCreditRating("A");
		loanToBeCalulated.setPaymentInMonths(40);		
		
		LoanOffer loanOfferExpected = new LoanOffer();
		
		loanOfferExpected.setDownPayment(loanToBeCalulated.getDownPayment());
		loanOfferExpected.setCustomer(customer);
		loanOfferExpected.setCar(car);
		loanOfferExpected.setCreditRating("A");
		loanOfferExpected.setPaymentInMonths(40);
		loanOfferExpected.setApr(5.97);
		loanOfferExpected.setMonthlyPayment(2398.08);
		loanOfferExpected.setTotalInterestRate(11);
		LoanOffer actual = calc.calculate(loanToBeCalulated);
		
		//skal kunne sætte bankraten til 8, ellers kan vi ikke lave tests
		//skal måske også et lånet op i 3 tests, apr, total interest rate og monthlypayment
		
		assertEquals(loanOfferExpected.getTotalInterestRate(), actual.getTotalInterestRate(),1);
		assertEquals(loanOfferExpected.getMonthlyPayment(), actual.getMonthlyPayment(),1);
		assertEquals(loanOfferExpected.getApr(), actual.getApr(),1); 
	}
	
//	@Test
//	public void periodeOf3Years(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void periodeOf20Months(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void downPaymentIsUnder20Percent(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void downPaymentIs50Percent(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void downPaymentIsAbove50Percent(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void creditRatingB(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void creditRatingC(){
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void creditRatingD(){
//		assertEquals(expected, actual);
//	}
}
