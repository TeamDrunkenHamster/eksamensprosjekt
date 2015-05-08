package dataLayer;

import java.sql.SQLException;

import domainLayer.Customer;

public interface CustomerDAO {
	
	public void createCustomer(Customer customer) throws SQLException;
	
	public Customer readCustomer(int customerID) throws SQLException;
	
	public void deleteCustomer(int customerID) throws SQLException;
}
