package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domainLayer.Customer;

public interface CustomerDAO {
  
  public List<Customer> readAllCustomers(Connection connection) throws SQLException;
  
  public Customer readCustomer(Connection connection, int customerID) throws SQLException;
	
	public void createCustomer(Connection connection, Customer customer) throws SQLException;
	
	public void deleteCustomer(Connection connection, int customerID) throws SQLException;
}
