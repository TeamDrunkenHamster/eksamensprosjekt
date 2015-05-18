package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domainLayer.Customer;

public class CustomerDAOImpl implements CustomerDAO {
  
  private static final String SELECT_ALL = "SELECT * FROM Customer";
  private static final String SELECT_FROM_CPR = "SELECT customerID, firstName, lastName, badStanding FROM Customer WHERE cprNumber = ?";
  private static final String CREATE = "INSERT INTO Customer (cprNumber, firstName, lastName, badStanding) VALUES (?, ?, ?, ?)";
  private static final String DELETE_FROM_ID = "DELETE FROM Customer WHERE customerID = ?";
  
  
  public List<Customer> readAllCustomers(Connection connection) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Customer customer = null;
    List<Customer> customerList = null;  
    
    try {
      statement = connection.prepareStatement( SELECT_ALL );
      resultSet = statement.executeQuery();
      customerList = new ArrayList<Customer>();
      
      while(resultSet.next()) {
        customer = new Customer();
        customer.setId(resultSet.getInt("customerID"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        customer.setBadStanding(resultSet.getBoolean("badStanding"));
        customerList.add(customer);
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return customerList;
  }

  
  public Customer readCustomer(Connection connection, String CPR) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Customer customer = null;
      
    try {
      statement = connection.prepareStatement( SELECT_FROM_CPR );
      statement.setString(1, CPR);
      resultSet = statement.executeQuery();
      customer = new Customer();
      
      while(resultSet.next()) {
        customer.setId(resultSet.getInt("customerID"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        customer.setBadStanding(resultSet.getBoolean("badStanding"));
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return customer;
  }
  
  public int createCustomer(Connection connection, Customer customer) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultset = null;
    
    try {
      statement = connection.prepareStatement( CREATE );
      statement.setString(1, customer.getFirstName());
      statement.setString(2, customer.getLastName());
      statement.setBoolean(3, customer.getBadStanding());
      statement.execute();
      connection.commit();
      resultset = statement.getGeneratedKeys();
      if (resultset.next()){
			return resultset.getInt(1);
		}
      
      
    } finally {
      if (statement != null)
        statement.close();
    }
    return -1;
  }
  
  public void deleteCustomer(Connection connection, int customerID) throws SQLException { //troede måske vi fik brug for den til unit tests.
    
    PreparedStatement statement = null;
    
    try {
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
  