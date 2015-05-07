package dataLayer;

import domainLayer.Customer;

public interface CustomerDAO {
	
	public void createCustomer(Customer customer);
	
	public Customer readCustomer(int customerID);
}
