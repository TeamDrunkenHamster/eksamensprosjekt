package dataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectImpl implements Connect {
	private static final String CONNECTION_URL = "jdbc:sqlite:ffsdatabase.db";
	private Connection connection = null;
	
	public ConnectImpl() throws SQLException {
		connection = DriverManager.getConnection(CONNECTION_URL);
		connection.setAutoCommit(false);
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
}