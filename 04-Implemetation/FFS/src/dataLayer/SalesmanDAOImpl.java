package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domainLayer.Salesman;

public class SalesmanDAOImpl implements SalesmanDAO {
  
  private static final String SELECT_FROM_ID = "SELECT salesmanID, firstName, lastName, loanValueLimit FROM Salesman WHERE salesmanID = ?";
  private static final String CREATE = "INSERT INTO Salesman (firstName, lastName, loanValueLimit) VALUES (?, ?, ?)";
  
  
  public Salesman readSalesman( Connection connection, int salesmanID ) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Salesman salesman = null;
      
    try {
      statement = connection.prepareStatement( SELECT_FROM_ID );
      statement.setInt(1, salesmanID);
      resultSet = statement.executeQuery();
      salesman = new Salesman();
      
      while(resultSet.next()) {
        salesman.setId(resultSet.getInt("customerID"));
        salesman.setFirstName(resultSet.getString("firstName"));
        salesman.setLastName(resultSet.getString("lastName"));
        salesman.setLoanValueLimit(resultSet.getInt("loanValueLimit"));
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return salesman;
  }

  public void createSalesman( Connection connection, Salesman salesman ) throws SQLException {
    
    PreparedStatement statement = null;
    
    try {
      statement = connection.prepareStatement( CREATE );
      statement.setString(1, salesman.getFirstName());
      statement.setString(2, salesman.getLastName());
      statement.setInt(3, salesman.getLoanValueLimit());
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }
  }

}
