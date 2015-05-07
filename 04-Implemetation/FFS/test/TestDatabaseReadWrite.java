import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

import dataLayer.CustomerDAO;


public class TestDatabaseReadWrite {

	CustomerDAO customerDAO;
	Customer customer;
	
	
	@Before
	public void setUp() throws Exception {
		
		customerDAO = new CustomerDAO();
		customer = new Customer();
	}

	@Test
	public void testInsertAndReadCustomer() {
		
		customer.setFirstName("Hans");
		customer.setLastName("Iversen");
		customer.setBadStanding(1);
		
		customerDAO.insertCustomer(customer);
		
		assertThat(customerDAO.readCustomer(1), is(equalTo(customer)));
		
	}

}
