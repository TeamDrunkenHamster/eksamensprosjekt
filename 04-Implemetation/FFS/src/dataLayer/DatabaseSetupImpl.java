package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetupImpl implements DatabaseSetup {
 
  //Here will be added the creation of the remaining tables.
  private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS Customer(customerID INTEGER PRIMARY KEY, firstName VARCHAR(255), lastName VARCHAR(255), badStanding INTEGER)";
  private static final String CREATE_SALESMAN_TABLE = "CREATE TABLE IF NOT EXISTS Salesman(salesmanID INTEGER PRIMARY KEY, firstName VARCHAR(255), lastName VARCHAR(255), loanValueLimit INTEGER)";
  
  public void createDatabase(Connection connection) throws SQLException {
    createTable(connection, CREATE_CUSTOMER_TABLE);
    createTable(connection, CREATE_SALESMAN_TABLE);
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
