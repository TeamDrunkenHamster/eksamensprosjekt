package domainLayer;

public class Salesman extends Person {
  private int salesmanID;
  private String firstName;
  private String lastName;
  private int loanValueLimit;
  
  public int getSalesmanID() {
    return salesmanID;
  }
  public void setSalesmanID( int salesmanID ) {
    this.salesmanID = salesmanID;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName( String firstName ) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }

  public int getLoanValueLimit() {
    return loanValueLimit;
  }
  public void setLoanValueLimit( int loamValueLimit ) {
    this.loanValueLimit = loamValueLimit;
  }

}
