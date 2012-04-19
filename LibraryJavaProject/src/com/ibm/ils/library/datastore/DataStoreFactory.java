package com.ibm.ils.library.datastore;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.ils.library.datastore.jdbc.CopyDataStoreJDBC;
import com.ibm.ils.library.datastore.jdbc.PatronDataStoreJDBC;

public class DataStoreFactory {
  private static ConnectionFactory factory;
  private static CopyDataStore copyDataStore;
  private static ItemDataStore itemDataStore;
  private static PatronDataStore patronDataStore;

  static {
    // info from properties file

    // parametry pro Driver manager connection
    String JDBCurl = "jdbc:db2://server1:50000/LIBRARY";
    Properties info = new Properties();
    info.put("user", "root"); // Set user ID for the connection
    info.put("password", "redhat"); // Set password for the connection

    try {
      Driver driver = (Driver) Class.forName("com.ibm.db2.jcc.DB2Driver")
          .newInstance();

      factory = new ConnectionFactory(driver, JDBCurl, info);
      // factory = new ConnectionFactory(ctxFactory, dataSource, info);

    }
    catch (ClassNotFoundException e) {
      // TODO Automaticky generovan� blok catch
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      // TODO Automaticky generovan� blok catch
      e.printStackTrace();
    }
    catch (InstantiationException e) {
      // TODO Automaticky generovan� blok catch
      e.printStackTrace();
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    patronDataStore = new PatronDataStoreJDBC(factory);
    copyDataStore = new CopyDataStoreJDBC(factory);
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

}
