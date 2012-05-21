package com.ibm.ils.library.datastore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * NOT IMPLEMENTED.
 * 
 */
public class DataSourceConnection implements BuildConnection {
	@SuppressWarnings("unused")
	private DataSource source;
	@SuppressWarnings("unused")
	private Properties info;

	public DataSourceConnection(String ctxFactory, String dataSource,
			Properties info) {

	}

	@Override
	public Connection getConnection() throws SQLException {
		return null;
	}

}
