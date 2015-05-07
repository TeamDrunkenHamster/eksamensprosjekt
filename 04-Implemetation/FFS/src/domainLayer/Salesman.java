package domainLayer;

public class Salesman extends Person {
  private int loanValueLimit;

  public int getLoanValueLimit() {
    return loanValueLimit;
  }
  public void setLoanValueLimit( int loamValueLimit ) {
    this.loanValueLimit = loamValueLimit;
  }

}
