package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domainLayer.Salesman;

public interface SalesmanDAO {
  
  public Salesman readSalesman(Connection connection, int salesmanID) throws SQLException;
  
  public void createSalesman(Connection connection, Salesman salesman) throws SQLException;

  public List<Salesman> readAllSalesman(Connection connection) throws SQLException;
  
}
