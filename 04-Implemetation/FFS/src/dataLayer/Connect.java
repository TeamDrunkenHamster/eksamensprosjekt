package dataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	private static final String CONNECTION_URL = "jdbc:sqlite:C:/FFS/test.db";
	private Connection connection = null;
	
	public Connect() throws SQLException {
		connection = DriverManager.getConnection(CONNECTION_URL);
		connection.setAutoCommit(false);
	}
	
	public Connection getConnection() {
		return connection;
	}
}