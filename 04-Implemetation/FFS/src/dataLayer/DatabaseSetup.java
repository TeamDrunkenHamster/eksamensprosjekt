package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseSetup {
  
  public void createDatabase( Connection connection ) throws SQLException;

}
