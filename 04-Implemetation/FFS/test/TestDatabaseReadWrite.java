import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.CustomerDAOImpl;
import domainLayer.Customer;


public class TestDatabaseReadWrite {

	CustomerDAOImpl customerDAO = null;
	Customer testCustomer = null;
	Connect connect = null;
	
	@Before
	public void setUp() throws Exception {
		
		customerDAO = new CustomerDAOImpl();
		testCustomer = new Customer();
		connect = new ConnectImpl();
	}

	@Test
	public void testInsertAndReadCustomer() throws SQLException {
		
		testCustomer.setFirstName("Hans");
		testCustomer.setLastName("Iversen");
		testCustomer.setBadStanding(1);
		
//		customerDAO.createCustomer(testCustomer);
		
		Customer fromDatabase = customerDAO.readCustomer(connect.getConnection(), 1);
		
		assertThat(fromDatabase.getFirstName(), is(equalTo(testCustomer.getFirstName())));
		assertThat(fromDatabase.getLastName(), is(equalTo(testCustomer.getLastName())));
		assertThat(fromDatabase.getBadStanding(), is(equalTo(testCustomer.getBadStanding())));
		//Dette virker ikke pga det skabte Customer objekt ikke har nogen ID endnu,
		//men Customer objektet som readCustomer() returnerer har en ID.
	}

}
