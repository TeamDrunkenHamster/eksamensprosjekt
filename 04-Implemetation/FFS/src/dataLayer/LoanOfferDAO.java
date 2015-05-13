package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domainLayer.LoanOffer;

public interface LoanOfferDAO {
  
  public List<LoanOffer> readAllLoanOffers(Connection connection) throws SQLException;
  
  public LoanOffer readLoanOffer(Connection connection, int loanID) throws SQLException;
  
  public void createLoanOffer(Connection connection, LoanOffer loanOffer) throws SQLException;

}
