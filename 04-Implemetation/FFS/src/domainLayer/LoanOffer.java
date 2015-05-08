package domainLayer;

public class LoanOffer {

	private int loanID;
	private int paymentInMonths;
	private double totalIntrestRate;
	private double downPayment;
	private double loanSize;
	private double apr;
	private String cprNumber;
	private Salesman salesman;
	private Customer customer;
	private Car car;
	
	public int getLoanID() {
		
		return loanID;
	}
	
	public void setLoanID(int loanID) {
		
		this.loanID = loanID;
	}
	
	public int getPaymentInMonths() {
		
		return paymentInMonths;
	}
	
	public void setPaymentInMonths(int paymentInMonths) {
		
		this.paymentInMonths = paymentInMonths;
	}
	
	public double getTotalIntrestRate() {
		
		return totalIntrestRate;
	}
	
	public void setTotalIntrestRate(double totalIntrestRate) {
		
		this.totalIntrestRate = totalIntrestRate;
	}
	
	public double getDownPayment() {
		
		return downPayment;
	}
	
	public void setDownPayment(double downPayment) {
		
		this.downPayment = downPayment;
	}
	
	public double getLoanSize() {
		
		return loanSize;
	}
	
	public void setLoanSize(double loanSize) {
		
		this.loanSize = loanSize;
	}
	
	public double getApr() {
		
		return apr;
	}
	
	public void setApr(double apr) {
		
		this.apr = apr;
	}
	
	public String getCprNumber() {
		
		return cprNumber;
	}
	
	public void setCprNumber(String cprNumber) {
		
		this.cprNumber = cprNumber;
	}
	
	public Salesman getSalesman() {
		
		return salesman;
	}
	
	public void setSalesman(Salesman salesman) {
		
		this.salesman = salesman;
	}
	
	public Customer getCustomer() {
		
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		
		this.customer = customer;
	}
	
	public Car getCar() {
		
		return car;
	}
	
	public void setCar(Car car) {
		
		this.car = car;
	}

}
