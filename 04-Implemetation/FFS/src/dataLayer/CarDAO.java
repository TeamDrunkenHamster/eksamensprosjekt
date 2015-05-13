package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;

import domainLayer.Car;

public interface CarDAO {
  
  public Car readCar(Connection connection, int carID) throws SQLException;
  
  public void createCar(Connection connection, Car car) throws SQLException;

}
