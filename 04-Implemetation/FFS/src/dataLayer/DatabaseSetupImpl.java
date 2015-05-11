package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetupImpl implements DatabaseSetup {
 
  //Here will be added the creation of the remaining tables.
  private static final String CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS Customer(customerID INTEGER PRIMARY KEY, firstName varchar(255), lastName varchar(255), badStanding INTEGER)";
  private static final String CREATE_SALESMAN = "CREATE TABLE IF NOT EXISTS Salesman(salesmanID INTEGER PRIMARY KEY, firstName varchar(255), lastName varchar(255), loanValueLimit INTEGER)";
  
  public void createDatabase(Connection connection) throws SQLException {
    createTables(connection, CREATE_CUSTOMER);
    createTables(connection, CREATE_SALESMAN);
  }
  
  private void createTables(Connection connection, String sql) throws SQLException {
    
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
