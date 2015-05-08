package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domainLayer.Customer;

public class CustomerDAOImpl implements CustomerDAO { //Jeg kunne ikke huske, hvad vi blev enige om at kalde klassen.
  
  private static final String SELECT_FROM_ID = "SELECT customerID, firstName, lastName, badStanding FROM Customer WHERE customerID = ?";
  private static final String CREATE = "INSERT INTO Customer (firstName, lastName, badStanding) VALUES (?, ?, ?)";
  private static final String DELETE_FROM_ID = "DELETE FROM Customer WHERE customerID = ?";
  
  public Customer readCustomer(int customerID) throws SQLException {
    
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Customer customer = null;
      
    try {
      connection = new ConnectImpl().getConnection();
      statement = connection.prepareStatement( SELECT_FROM_ID );
      statement.setInt(1, customerID);
      resultSet = statement.executeQuery();
      customer = new Customer();
      
      while(resultSet.next()) {
        customer.setId(resultSet.getInt("customerID"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        customer.setId(resultSet.getInt("customerID"));
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return customer;
  }
  
  public void createCustomer(Customer customer) throws SQLException {
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      connection = new ConnectImpl().getConnection();
      statement = connection.prepareStatement( CREATE );
      statement.setString(1, customer.getFirstName());
      statement.setString(2, customer.getLastName());
      statement.setInt(3, customer.getBadStanding());
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }
  }
  
  public void deleteCustomer(int customerID) throws SQLException { //troede måske vi fik brug for den til unit tests.
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      connection = new ConnectImpl().getConnection();
      statement = connection.prepareStatement(DELETE_FROM_ID);
      statement.setInt(1, customerID);
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }
  }
  
}
  