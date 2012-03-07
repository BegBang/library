package com.ibm.ils.library.datastore;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private BuildConnection connection;

	/**
	 * @param driver
	 * @param databaseURL
	 * @param info connection properties e.g.: username, password
	 * @throws SQLException 
	 */
	public ConnectionFactory(Driver driver, String databaseURL,
			Properties info) throws SQLException {
		this.connection = new DriverManagerConnection(driver, databaseURL,
				info);
	}

	public ConnectionFactory(String ctxFactory, String dataSource, Properties info) {
		this.connection = new DataSourceConnection(ctxFactory, dataSource, info);
	}

	public Connection getConnection() throws SQLException {
		return connection.getConnection();
	}

}
