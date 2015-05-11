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
  private static final String SELECT_FROM_ID = "SELECT customerID, firstName, lastName, badStanding FROM Customer WHERE customerID = ?";
  private static final String CREATE = "INSERT INTO Customer (firstName, lastName, badStanding) VALUES (?, ?, ?)";
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
        customer.setBadStanding(resultSet.getInt("badStanding"));
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

  
  public Customer readCustomerFromID(Connection connection, int customerID) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Customer customer = null;
      
    try {
      statement = connection.prepareStatement( SELECT_FROM_ID );
      statement.setInt(1, customerID);
      resultSet = statement.executeQuery();
      customer = new Customer();
      
      while(resultSet.next()) {
        customer.setId(resultSet.getInt("customerID"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        customer.setBadStanding(resultSet.getInt("badStanding"));
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return customer;
  }
  
  public void createCustomer(Connection connection, Customer customer) throws SQLException {
    
    PreparedStatement statement = null;
    
    try {
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


  @Override
  public Customer readCustomer( Connection connection, int customerID ) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }
  
}
  