package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetupImpl implements DatabaseSetup {
 
  //Here will be added the creation of the remaining tables.
  private static final String FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON";
  private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS Customer(customerID INTEGER PRIMARY KEY, cprNumber VARCHAR(10), firstName VARCHAR(255), lastName VARCHAR(255), badStanding BOOLEAN)";
  private static final String CREATE_SALESMAN_TABLE = "CREATE TABLE IF NOT EXISTS Salesman(salesmanID INTEGER PRIMARY KEY, firstName VARCHAR(255), lastName VARCHAR(255), loanValueLimit INTEGER)";
  private static final String CREATE_CAR_TABLE = "CREATE TABLE IF NOT EXISTS Car(carID INTEGER PRIMARY KEY, model VARCHAR(255), price DOUBLE)";
  private static final String CREATE_LOANOFFER_TABLE = "CREATE TABLE IF NOT EXISTS LoanOffer(loanID INTEGER PRIMARY KEY, customerID INTEGER, salesmanID INTEGER, carID INTEGER, totalInterestRate DOUBLE, downPayment DOUBLE, loanSize DOUBLE, paymentPeriodInMonths INTEGER, startDate VARCHAR, approvedStatus BOOLEAN, rejected BOOLEAN, FOREIGN KEY(customerID) REFERENCES Customer(customerID), FOREIGN KEY(salesmanID) REFERENCES Salesman(salesmanID), FOREIGN KEY(carID) REFERENCES Car(carID))";

  public void createDatabase(Connection connection) throws SQLException {
    
    createTable(connection, FOREIGN_KEYS_ON);
    createTable(connection, CREATE_CUSTOMER_TABLE);
    createTable(connection, CREATE_SALESMAN_TABLE);
    createTable(connection, CREATE_CAR_TABLE);
    createTable(connection, CREATE_LOANOFFER_TABLE);
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
  
}
