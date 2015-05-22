package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domainLayer.Car;
import domainLayer.Salesman;

public class DatabaseSetupImpl implements DatabaseSetup {
	
  //Here will be added the creation of the remaining tables.
  private static final String FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON";
  private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS Customer(customerID INTEGER PRIMARY KEY, cprNumber VARCHAR(10) UNIQUE, firstName VARCHAR(255), lastName VARCHAR(255), badStanding BOOLEAN)";
  private static final String CREATE_SALESMAN_TABLE = "CREATE TABLE IF NOT EXISTS Salesman(salesmanID INTEGER PRIMARY KEY, loanValueLimit INTEGER)";
  private static final String CREATE_CAR_TABLE = "CREATE TABLE IF NOT EXISTS Car(carID INTEGER PRIMARY KEY, model VARCHAR(255), price DOUBLE)";
  private static final String CREATE_LOANOFFER_TABLE = "CREATE TABLE IF NOT EXISTS LoanOffer(loanID INTEGER PRIMARY KEY, customerID INTEGER, salesmanID INTEGER, carID INTEGER, totalInterestRate DOUBLE, monthlyPayment DOUBLE, downPayment DOUBLE, loanSize DOUBLE, paymentPeriodInMonths INTEGER, startDate VARCHAR, approvedStatus BOOLEAN, rejected BOOLEAN, FOREIGN KEY(customerID) REFERENCES Customer(customerID), FOREIGN KEY(salesmanID) REFERENCES Salesman(salesmanID), FOREIGN KEY(carID) REFERENCES Car(carID))";
  
  public void createDatabase(Connection connection) throws SQLException {
    
    createTable(connection, FOREIGN_KEYS_ON);
    createTable(connection, CREATE_CUSTOMER_TABLE);
    createTable(connection, CREATE_SALESMAN_TABLE);
    createTable(connection, CREATE_CAR_TABLE);
    createTable(connection, CREATE_LOANOFFER_TABLE);
    DefaultData(connection);
  }
  
  private void createTable(Connection connection, String sql) throws SQLException {
    
    PreparedStatement statement = null;
    
    try {
      statement = connection.prepareStatement(sql);
      statement.execute();
      connection.commit();
    } finally {
      if (statement != null)
        statement.close();
    }
  }
  
  private void DefaultData(Connection connection){
	  
	  CarDAO carDAO = new CarDAOImpl();
	  SalesmanDAO salesmanDAO = new SalesmanDAOImpl();
	  
	  List <Car> defaultCars = new ArrayList<Car>();
	  
	  Salesman salesman = new Salesman();
	  salesman.setLoanValueLimit(1_000_000);
	  
	  defaultCars.add(addDefaultCar("458", 1_900_000));
	  defaultCars.add(addDefaultCar("FF", 2_000_000));
	  defaultCars.add(addDefaultCar("LaFerrari", 9_500_000));
	  
	  try {
		if(carDAO.readAllCars(connection).isEmpty()){
			for (Car car : defaultCars) {
				carDAO.createCar(connection, car);
			}
			
		  }
		if(salesmanDAO.readAllSalesman(connection).isEmpty()){
			salesmanDAO.createSalesman(connection, salesman);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  
  private Car addDefaultCar(String model, double price) {
	  
	  	Car car = new Car();
	  	car.setModel(model);
	  	car.setPrice(price);
	
	  	return car;
  	}

}
