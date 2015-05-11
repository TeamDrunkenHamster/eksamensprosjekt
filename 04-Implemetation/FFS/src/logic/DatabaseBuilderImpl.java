package logic;

import java.sql.Connection;
import java.sql.SQLException;

import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.DatabaseSetup;
import dataLayer.DatabaseSetupImpl;

public class DatabaseBuilderImpl implements DatabaseBuilder {
	
	@Override
	public void createDatabase() {

		try {
			Connect connect = new ConnectImpl();
			Connection connection = connect.getConnection();
			DatabaseSetup dbSetup = new DatabaseSetupImpl();
			dbSetup.createTables(connection);
			connection.close();
		} catch (SQLException e) {
			System.out.println("database error!");
		}
	}

}
