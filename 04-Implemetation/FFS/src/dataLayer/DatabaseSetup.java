package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseSetup {
  
  public void createTables( Connection connection ) throws SQLException;

}
