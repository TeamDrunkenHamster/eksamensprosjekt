package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;

import domainLayer.Salesman;

public interface SalesmanDAO {
  
  public Salesman readSalesman(Connection connection, int salesmanID) throws SQLException;
  
  public void createSalesman(Connection connection, Salesman salesman) throws SQLException;
  

}
