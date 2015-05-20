package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domainLayer.Salesman;

public class SalesmanDAOImpl implements SalesmanDAO {
  
  private static final String SELECT_ALL = "SELECT * FROM Salesman";
  private static final String SELECT_FROM_ID = "SELECT salesmanID, loanValueLimit FROM Salesman WHERE salesmanID = ?";
  private static final String CREATE = "INSERT INTO Salesman (loanValueLimit) VALUES (?)";
  
  
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
      statement.setInt(1, salesman.getLoanValueLimit());
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }
  }

@Override
public List<Salesman> readAllSalesman(Connection connection) throws SQLException {
	
	PreparedStatement statement = null;
    ResultSet resultSet = null;
    List<Salesman> salesmanList = null;
	
    try {
        statement = connection.prepareStatement( SELECT_ALL );
        resultSet = statement.executeQuery();
        salesmanList = new ArrayList<Salesman>();
        Salesman salesman = new Salesman();
        
        while(resultSet.next()) {
          salesman.setSalesmanID((resultSet.getInt("salesmanID")));
          salesman.setLoanValueLimit(resultSet.getInt("loanValueLimit"));
          salesmanList.add(salesman);
        }
      } finally {
        if (statement != null)
          statement.close();
        if (resultSet != null)
          resultSet.close();
      }
      return salesmanList;
}

}
