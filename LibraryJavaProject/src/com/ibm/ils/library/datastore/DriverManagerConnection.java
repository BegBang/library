package com.ibm.ils.library.datastore;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * For connection using driver manager.
 *
 */
public class DriverManagerConnection implements BuildConnection {
	private String databaseURL;
	private Properties info;

	public DriverManagerConnection(Driver driver, String databaseURL,
			Properties info) throws SQLException {
		this.databaseURL = databaseURL;
		this.info = info;
		DriverManager.registerDriver(driver);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(databaseURL, info);
	}

}
