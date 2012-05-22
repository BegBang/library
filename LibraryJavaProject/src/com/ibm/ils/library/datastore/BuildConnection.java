package com.ibm.ils.library.datastore;

import java.sql.Connection;
import java.sql.SQLException;

public interface BuildConnection {
	
	/**
	 * @return connection to database
	 * @throws SQLException if an error occurs
	 */
	public Connection getConnection() throws SQLException;

}
