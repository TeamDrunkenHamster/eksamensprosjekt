package domainLayer;

public class LoanOffer {

	private int loanID;
	private int paymentInMonths;
	private double totalInterestRate;
	private double montlyPayment; //=ydelse. Ved ikke hvad vi skal kalde den.
  private String startDate;
	private double downPayment;
	private double loanSize;
	private double apr;
	private String creditRating;
	private String cprNumber;
	private Salesman salesman;
	private Customer customer;
	private Car car;
	private boolean approvedStatus;
  private boolean rejected;
  
  public double getMontlyPayment() {
    return montlyPayment;
  }

  public void setMontlyPayment( double montlyRepayment ) {
    this.montlyPayment = montlyRepayment;
  }

  public boolean getApprovedStatus() {
    return approvedStatus;
  }

  public void setApprovedStatus( boolean approvedStatus ) {
    this.approvedStatus = approvedStatus;
  }

	
	public boolean getRejected() {
		
		return rejected;
	}
	
	public void setRejected(boolean rejected) {
		
		this.rejected = rejected;
	}
	
	public String getStartDate() {

		return startDate;
	}

	public void setStartDate(String startDate) {
		
		this.startDate = startDate;
	}

	public String getCreditRating() {
		
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		
		this.creditRating = creditRating;
	}

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
	
	public double getTotalInterestRate() {
		
		return totalInterestRate;
	}
	
	public void setTotalInterestRate(double totalInterestRate) {
		
		this.totalInterestRate = totalInterestRate;
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
