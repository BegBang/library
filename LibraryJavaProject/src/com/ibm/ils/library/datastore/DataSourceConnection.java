package com.ibm.ils.library.datastore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class DataSourceConnection implements BuildConnection {
	private DataSource source;
	private Properties info;

	public DataSourceConnection(String ctxFactory, String dataSource,
			Properties info) {
		// TODO Automaticky generovaný stub konstruktoru
		//ctxFactory = "com.sun.jndi.ldap.LdapCtxFactory";
		//dataSource = "java:comp/env/ejbDB";
		this.info = info;

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				ctxFactory);
		env.put(Context.PROVIDER_URL, "iiop:///");
		try {
			Context ctx = new InitialContext(env);
			this.source = (javax.sql.DataSource) ctx.lookup(dataSource);
		} catch (NamingException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}

	}

	@Override
	public Connection getConnection() throws SQLException {
		String username = info.getProperty("username");
		String password = info.getProperty("password");
		//return source.getConnection(username, password);
		return source.getConnection();
	}

}
