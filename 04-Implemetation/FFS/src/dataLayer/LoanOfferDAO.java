package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;

import domainLayer.LoanOffer;

public interface LoanOfferDAO {
  
  public LoanOffer readLoanOffer(Connection connection, int loanID) throws SQLException;
  
  public void createLoanOffer(Connection connection, LoanOffer loanOffer) throws SQLException;

}
