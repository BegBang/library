package com.ibm.ils.library.datastore.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.ils.library.datastore.ConnectionFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.model.Patron;

public class PatronDataStoreJDBC implements PatronDataStore {
	private ConnectionFactory factory;

	private static final String SQL_INSERT = "INSERT INTO patron (patron_id, first_name, last_name, password, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_FIND = "SELECT patron_id FROM LIBRARY.PATRON";

	public PatronDataStoreJDBC(ConnectionFactory factory) {
		this.factory = factory;
	}

	@Override
	public void add(Patron patron) {
		// TODO Automaticky generovaný stub metody

	}

	@Override
	public Patron findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Patron user = null;
		
		try {
			connection = factory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND);
			resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet);
		} catch (SQLException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}
		// TODO Automaticky generovaný stub metody
		return null;
	}

}
