package logic;

import java.sql.Connection;

import dataLayer.Connect;
import dataLayer.ConnectImpl;

public class DatabaseBuilderImpl implements DatabaseBuilder {
	
	@Override
	public void createDatabase() {

		Connect connect = new ConnectImpl();
		Connection connection = connect.getConnection();
		DatabaseSetup dbSetup = new DatabaseSetup();
		dbSetup.createTables(connection);
		connection.close();
	}

}
