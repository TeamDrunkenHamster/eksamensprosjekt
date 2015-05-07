package dataLayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domainLayer.Customer;

public class CustomerDataAccess {
  
  private static final String SELECT = "SELECT * FROM Customer";

  public Customer readCustomer() throws SQLException {
    
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultset = null;
    Customer customer = null;
    
    try {
      connection = new Connect().getConnection();
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
    
    try {
      statement = connection.prepareStatement(SELECT);
      resultset = statement.executeQuery();
      customer = new Customer();
      
      while (resultset.next()) {
        customer.setId(resultset.getInt("CustomerID"));
        customer.setFirstName(resultset.getString("firstName"));
        customer.setLastName(resultset.getString("lastName"));
        customer.setBadStanding(resultset.getInt("badStanding"));
      }
      
      return customer;
    
    } finally {
      if (resultset != null) {
        resultset.close();
      }
      
      if (statement != null) {
        statement.close();
      }
    }
  }
  
}