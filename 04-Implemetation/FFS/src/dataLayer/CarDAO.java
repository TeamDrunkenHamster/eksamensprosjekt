package dataLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import domainLayer.Car;

public interface CarDAO {
  
  public List<Car> readAllCars(Connection connection) throws SQLException;
  
  public Car readCar(Connection connection, int carID) throws SQLException;
  
  public void createCar(Connection connection, Car car) throws SQLException;

}
