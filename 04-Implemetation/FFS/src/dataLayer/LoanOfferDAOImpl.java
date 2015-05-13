package dataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domainLayer.Car;
import domainLayer.Customer;
import domainLayer.LoanOffer;
import domainLayer.Salesman;

public class LoanOfferDAOImpl implements LoanOfferDAO {
  
  private static final String SELECT_ALL = "SELECT Customer.customerID, Customer.firstName, Customer.lastName, Salesman.salesmanID, Car.model, Car.carID, loanID, totalInterestRate, downPayment, loanSize, paymentPeriodInMonths, startDate, approvedStatus, rejected FROM LoanOffer " + 
      "LEFT JOIN Customer ON LoanOffer.customerID=Customer.customerID LEFT JOIN Salesman ON LoanOffer.salesmanID=Salesman.salesmanID LEFT JOIN Car ON LoanOffer.carID=Car.carID WHERE LoanID = LoanID";

  private static final String SELECT_FROM_ID = "SELECT Customer.customerID, Customer.firstName, Customer.lastName, Salesman.salesmanID, Car.model, Car.carID, loanID, totalInterestRate, downPayment, loanSize, paymentPeriodInMonths, startDate, approvedStatus, rejected FROM LoanOffer " + 
                                             "LEFT JOIN Customer ON LoanOffer.customerID=Customer.customerID LEFT JOIN Salesman ON LoanOffer.salesmanID=Salesman.salesmanID LEFT JOIN Car ON LoanOffer.carID=Car.carID WHERE LoanID = ?";
  
  private static final String CREATE = "INSERT INTO LoanOffer (customerID, salesmanID, carID, totalInterestRate, downPayment, loanSize, paymentPeriodInMonths, startDate, approvedStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                        //Skal loanOffers creates med approvedStatus på false? og hvad med rejected?

  public List<LoanOffer> readAllLoanOffers(Connection connection) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Customer customer = null;
    Salesman salesman = null;
    Car car = null;
    LoanOffer loanOffer = null;
    List<LoanOffer> loanOfferList = null;
      
    try {
      statement = connection.prepareStatement( SELECT_ALL );
      resultSet = statement.executeQuery();
      loanOfferList = new ArrayList<LoanOffer>();

      while(resultSet.next()) {
        customer = new Customer();
        salesman = new Salesman();
        car = new Car();
        loanOffer = new LoanOffer();
        
        customer.setId(resultSet.getInt("customerID"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        loanOffer.setCustomer(customer);
        salesman.setId(resultSet.getInt("salesmanID"));
        loanOffer.setSalesman(salesman);
        car.setId(resultSet.getInt("carID"));
        car.setModel(resultSet.getString("model"));
        loanOffer.setCar(car);
        loanOffer.setLoanID(resultSet.getInt("loanID"));
        loanOffer.setTotalInterestRate(resultSet.getDouble("totalInterestRate"));
        loanOffer.setDownPayment(resultSet.getDouble("downPayment"));
        loanOffer.setLoanSize(resultSet.getDouble("loanSize"));
        loanOffer.setPaymentInMonths(resultSet.getInt("paymentPeriodInMonths"));
        loanOffer.setStartDate(resultSet.getString("startDate"));
        loanOffer.setApprovedStatus(resultSet.getBoolean("approvedStatus"));
        loanOffer.setRejected(resultSet.getBoolean("rejected"));
        loanOfferList.add(loanOffer);
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return loanOfferList;
  }

  public LoanOffer readLoanOffer( Connection connection, int loanID ) throws SQLException {
    
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Customer customer = null;
    Salesman salesman = null;
    Car car = null;
    LoanOffer loanOffer = null;
      
    try {
      statement = connection.prepareStatement( SELECT_FROM_ID );
      statement.setInt(1, loanID);
      resultSet = statement.executeQuery();

      while(resultSet.next()) {
        customer = new Customer();
        salesman = new Salesman();
        car = new Car();
        loanOffer = new LoanOffer();
        
        customer.setId(resultSet.getInt("customerID"));
        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        loanOffer.setCustomer(customer);
        salesman.setId(resultSet.getInt("salesmanID"));
        loanOffer.setSalesman(salesman);
        car.setId(resultSet.getInt("carID"));
        car.setModel(resultSet.getString("model"));
        loanOffer.setCar(car);
        loanOffer.setLoanID(resultSet.getInt("loanID"));
        loanOffer.setTotalInterestRate(resultSet.getDouble("totalInterestRate"));
        loanOffer.setDownPayment(resultSet.getDouble("downPayment"));
        loanOffer.setLoanSize(resultSet.getDouble("loanSize"));
        loanOffer.setPaymentInMonths(resultSet.getInt("paymentPeriodInMonths"));
        loanOffer.setStartDate(resultSet.getString("startDate"));
        loanOffer.setApprovedStatus(resultSet.getBoolean("approvedStatus"));
        loanOffer.setRejected(resultSet.getBoolean("rejected"));
      }
    } finally {
      if (statement != null)
        statement.close();
      if (resultSet != null)
        resultSet.close();
    }
    return loanOffer;
  }

  
  public void createLoanOffer( Connection connection, LoanOffer loanOffer ) throws SQLException {
    
    PreparedStatement statement = null;
    
    try {
      statement = connection.prepareStatement( CREATE );
      statement.setInt(1, loanOffer.getCustomer().getId());
      statement.setInt(2, loanOffer.getSalesman().getId());
      statement.setInt(3, loanOffer.getCar().getId());
      statement.setDouble(4, loanOffer.getTotalInterestRate());
      statement.setDouble(5, loanOffer.getDownPayment());
      statement.setDouble(6, loanOffer.getLoanSize());
      statement.setInt(7, loanOffer.getPaymentInMonths());
      statement.setString(8, loanOffer.getStartDate());
      statement.setBoolean(9, loanOffer.getApprovedStatus());
      
      statement.execute();
      connection.commit();
      
    } finally {
      if (statement != null)
        statement.close();
    }

  }

}
