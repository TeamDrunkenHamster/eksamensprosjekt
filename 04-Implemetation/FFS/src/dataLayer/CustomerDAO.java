package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;

import domainLayer.Customer;

public interface CustomerDAO {
	
	public void createCustomer(Connection connection, Customer customer) throws SQLException;
	
	public Customer readCustomer(Connection connection, int customerID) throws SQLException;
	
	public void deleteCustomer(Connection connection, int customerID) throws SQLException;
}
