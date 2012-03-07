package com.ibm.ils.library.datastore;

import java.sql.Connection;
import java.sql.SQLException;

public interface BuildConnection {
	
	public Connection getConnection() throws SQLException;

}
