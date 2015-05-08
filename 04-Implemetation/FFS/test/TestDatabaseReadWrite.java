import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import dataLayer.CustomerDAOImpl;
import domainLayer.Customer;


public class TestDatabaseReadWrite {

	CustomerDAOImpl customerDAO = null;
	Customer customer = null;
	
	
	@Before
	public void setUp() throws Exception {
		
		customerDAO = new CustomerDAOImpl();
		customer = new Customer();
	}

	@Test
	public void testInsertAndReadCustomer() throws SQLException {
		
		customer.setFirstName("Hans");
		customer.setLastName("Iversen");
		customer.setBadStanding(1);
		
		customerDAO.createCustomer(customer);
		
		assertThat(customerDAO.readCustomer(1), is(equalTo(customer)));
		//Dette virker ikke pga det skabte Customer objekt ikke har nogen ID endnu,
		//men Customer objektet som readCustomer() returnerer har en ID.
	}

}
