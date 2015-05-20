package domainLayer;

public class Salesman extends Person {
  private int salesmanID;
  private int loanValueLimit;
  
  public int getSalesmanID() {
    return salesmanID;
  }
  public void setSalesmanID( int salesmanID ) {
    this.salesmanID = salesmanID;
  }

  public int getLoanValueLimit() {
    return loanValueLimit;
  }
  public void setLoanValueLimit( int loanValueLimit ) {
    this.loanValueLimit = loanValueLimit;
  }

}
