package logic;

import java.sql.Connection;
import java.sql.SQLException;

import logging.ErrorTypes;
import logging.Logger;
import dataLayer.Connect;
import dataLayer.ConnectImpl;
import dataLayer.DatabaseSetup;
import dataLayer.DatabaseSetupImpl;

public class DatabaseBuilderImpl implements DatabaseBuilder {
	
	private Logger logger = new Logger();
	
	@Override
	public void createDatabase() {

		try {
			Connect connect = new ConnectImpl();
			Connection connection = connect.getConnection();
			DatabaseSetup dbSetup = new DatabaseSetupImpl();
			dbSetup.createDatabase(connection);
			connection.close();
			ObserverSingleton.instance().notifyObservers();
		} catch (SQLException e) {
			logger.log("Database error", "Error setting up database for first time use.\n" + e.getMessage(), ErrorTypes.ERROR);
		}
	}

}
