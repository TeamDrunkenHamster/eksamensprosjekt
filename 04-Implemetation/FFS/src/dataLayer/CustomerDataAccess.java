package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domainLayer.Customer;

public class CustomerDataAccess {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
      
      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultset = null;
      Customer customer = null;
      
      try {
        connection = new Connect().getConnection();
      } 
      finally {
        if (connection != null)
          connection.close();
      }
      
      try {
        statement = connection.prepareStatement("SELECT * FROM customer");
        resultset = statement.executeQuery();
        customer = new Customer();
        
        while (resultset.next()) {
          customer = new Customer();
          customer.setAlbumTitel(resultset.getString("albumtitel"));
          customer.setUdgivelse(resultset.getString("udgivelse"));
          customer.setAid(resultset.getInt("aid"));
          list.add(customer);
        }     
        return list;
  }
}