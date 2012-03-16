package com.ibm.ils.library.datastore;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.ils.library.datastore.jdbc.PatronDataStoreJDBC;

public class DataStoreFactory {
	private static ConnectionFactory factory;
	private static PatronDataStore patronDataStore;
	
	static {
		// info from properties file
		// parametry pro Driver manager connection
		String JDBCurl = "jdbc:db2://server1:50000/LIBRARY";
		Properties info =  new Properties(); 
		info.put("user", "root");         // Set user ID for the connection
		info.put("password", "redhat");     // Set password for the connection
		
		try {
			//Class<?> 
			Driver driver = (Driver) Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			factory = new ConnectionFactory(driver, JDBCurl, info);
		} catch (ClassNotFoundException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}
		
		
		patronDataStore = new PatronDataStoreJDBC(factory);
	}

	public static PatronDataStore getPatronDataStore() {
		return patronDataStore;
	}
	
	

}
