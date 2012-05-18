package com.ibm.ils.library.datastore;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.ils.library.datastore.jdbc.CopyDataStoreJDBC;
import com.ibm.ils.library.datastore.jdbc.ItemDataStoreJDBC;
import com.ibm.ils.library.datastore.jdbc.PatronDataStoreJDBC;

public class DataStoreFactory {
	private static ConnectionFactory factory;
	private static CopyDataStore copyDataStore;
	private static ItemDataStore itemDataStore;
	private static PatronDataStore patronDataStore;

	private static final String PROPERTIES_FILE = "databaseInformation.properties";
	private static final String TYPE = "type";
	private static final String TYPE_DRIVER_MANAGER = "dm";
	private static final String TYPE_DATA_SOURCE = "ds";
	private static final String USERID = "userid";
	private static final String PASSWORD = "password";
	private static final String DRIVER = "driver";
	private static final String URL = "url";

	static {
		// load properties file and instantiates factory
		loadProperties(PROPERTIES_FILE);

		// instantiates data store objects
		copyDataStore = new CopyDataStoreJDBC(factory);
		itemDataStore = new ItemDataStoreJDBC(factory);
		patronDataStore = new PatronDataStoreJDBC(factory);
	}

	public static CopyDataStore getCopyDataStore() {
		return copyDataStore;
	}

	public static ItemDataStore getItemDataStore() {
		return itemDataStore;
	}

	public static PatronDataStore getPatronDataStore() {
		return patronDataStore;
	}

	private static void loadProperties(String filename) {
		InputStream inputStream = DataStoreFactory.class.getClassLoader()
				.getResourceAsStream(PROPERTIES_FILE);
		System.out.println(DataStoreFactory.class.getClassLoader().getResource(".").getFile());

//TODO on url	
		//TODO nesikovna chybova hlaska hlasi missing i kdyz je soubor exists
		if (inputStream == null) {
			processError(filename);
		}

		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			String type = properties.getProperty(TYPE);
			if (type == null) {
				processMissingProperty(TYPE);
			}
			if (type.equalsIgnoreCase(TYPE_DRIVER_MANAGER)) {
				String userid = properties.getProperty(USERID);
				String password = properties.getProperty(PASSWORD);
				String url = properties.getProperty(URL);
				String driver = properties.getProperty(DRIVER);
				instantiateFactory(driver, url, userid, password);
			} else if (type.equalsIgnoreCase(TYPE_DATA_SOURCE)) {

			} else {
				processError(filename);
			}

		} catch (IOException e) {
			processError(filename);
		}
	}

	private static void processError(String filename) {
		System.err.println("File: " + filename + " is missing.");
		System.exit(-1);
	}

	private static void processMissingProperty(String property) {
		System.err.println("Property: " + property
				+ " is missing. Fix the properties file.");
		System.exit(-1);
	}

	private static void instantiateFactory(String driverName, String url,
			String userid, String password) {
		Properties info = new Properties();
		info.put("user", "root"); // Set user ID for the connection
		info.put("password", "redhat"); // Set password for the connection

		try {
			Driver driver = (Driver) Class.forName(driverName).newInstance();
			factory = new ConnectionFactory(driver, url, info);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void instantiateFactoryDataSource() {
		// datasource
		String ctxFactory = "com.ibm.websphere.naming.WsnInitialContextFactory";
		String dataSource = "java:comp/env/jdbc/library";
		// factory = new ConnectionFactory(ctxFactory, dataSource, info);
	}
}
