package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetupImpl implements DatabaseSetup {
 
  //Here will be added the creation of the remaining tables.
  private static final String CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS Customer(customerID INTEGER PRIMARY KEY, firstName varchar(255), lastName varchar(255), badStanding INTEGER)";
  
  public void createTables(Connection connection) throws SQLException {
    
    PreparedStatement statement = null;
    
    try {
      statement = connection.prepareStatement(CREATE_CUSTOMER);
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }
  }
}
