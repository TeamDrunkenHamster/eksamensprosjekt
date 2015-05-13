package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domainLayer.Car;

public class CarDAOImpl implements CarDAO {
  
  private static final String SELECT_FROM_ID = "SELECT carID, model, price FROM Car WHERE carID = ?";
  private static final String CREATE = "INSERT INTO Car (model, price) VALUES (?, ?)";

  public Car readCar( Connection connection, int carID ) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Car car = null;
      
    try {
      statement = connection.prepareStatement( SELECT_FROM_ID );
      statement.setInt(1, carID);
      resultSet = statement.executeQuery();
      car = new Car();
      
      while(resultSet.next()) {
        car.setId(resultSet.getInt("carID"));
        car.setModel(resultSet.getString("model"));
        car.setPrice(resultSet.getDouble("price"));
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return car;
  }

  public void createCar( Connection connection, Car car ) throws SQLException {
    
    PreparedStatement statement = null;
    
    try {
      statement = connection.prepareStatement( CREATE );
      statement.setString(1, car.getModel());
      statement.setDouble(2, car.getPrice());
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }
  }

}
