package test.dummy;

import domainLayer.Car;
import domainLayer.Customer;
import domainLayer.LoanOffer;
import domainLayer.Salesman;

public class DummyObjects {
	
	private LoanOffer loanOffer;
	private Customer customer;
	private Salesman salesman;
	private Car car;
	
	public DummyObjects() {
		loanOffer = new LoanOffer();
		customer = new Customer();
		salesman = new Salesman();
		car = new Car();
		setObjectInfo();
	}
	
	private void setObjectInfo() {

		setCar();
		setCustomer();
		setSalesman();
		setLoanOffer();
	}

	private void setLoanOffer() {

		loanOffer.setLoanID(1);
		loanOffer.setCprNumber(customer.getCPR());
		loanOffer.setLoanSize(1500000);
		loanOffer.setCreditRating("A");
		loanOffer.setCar(car);
		loanOffer.setCustomer(customer);
		loanOffer.setSalesman(salesman);
		loanOffer.setApprovedStatus(true);
		loanOffer.setApr(10);
		loanOffer.setDownPayment(100000);
		loanOffer.setPaymentInMonths(48);
		loanOffer.setRejected(false);
		loanOffer.setTotalInterestRate(13);
		loanOffer.setStartDate("2.15");
	}

	private void setSalesman() {
	
		salesman.setId(1);
		salesman.setLoanValueLimit(5000000);
	}

	private void setCustomer() {

		customer.setCPR("1404758320");
		customer.setBadStanding(false);
		customer.setFirstName("Bob");
		customer.setLastName("Kelso");
	}

	private void setCar() {

		car.setId(1);
		car.setModel("F50");
		car.setPrice(2000000);
	}

	public LoanOffer getDummyLoanOffer() {
		return loanOffer;
	}
	
	public Customer getDummyCustomer() {
		return customer;
	}
	
	public Salesman getDummySalesman() {
		return salesman;
	}
	
	public Car getDummyCar() {
		return car;
	}

}
