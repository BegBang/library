package com.ibm.ils.library.datastore.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.ibm.ils.library.datastore.ConnectionFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import static com.ibm.ils.library.datastore.util.DAOUtil.close;

public class PatronDataStoreJDBC implements PatronDataStore {
	private ConnectionFactory factory;

	private static final String SQL_INSERT = "INSERT INTO LIBRARY.PATRON (patron_id, first_name, last_name, password, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_EMAIL = "SELECT patron_id, first_name, last_name, password, email FROM LIBRARY.PATRON where email = ?";
	private static final String SQL_FIND_BY_ID = "SELECT patron_id, first_name, last_name, password, email FROM LIBRARY.PATRON where patron_id = ?";
	private static final String SQL_REMOVE = "DELETE FROM LIBRARY.PATRON WHERE patron_id = ?";
	private static final String SQL_UPDATE = "UPDATE LIBRARY.PATRON SET first_name = ?, last_name = ?, password = ?, email = ?  WHERE patron_id = ?";

	private static final int PATRON_ID_IND = 1;
	private static final int FIRST_NAME_IND = 2;
	private static final int LAST_NAME_IND = 3;
	private static final int PASSWORD_IND = 4;
	private static final int EMAIL_IND = 5;

	public PatronDataStoreJDBC(ConnectionFactory factory) {
		this.factory = factory;
	}

	@Override
	public void add(Patron patron) throws SystemUnavailableException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = factory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INSERT);
			preparedStatement.setObject(PATRON_ID_IND, patron.getId());
			preparedStatement.setObject(FIRST_NAME_IND, patron.getFirstName());
			preparedStatement.setObject(LAST_NAME_IND, patron.getLastName());
			preparedStatement.setObject(PASSWORD_IND, patron.getPassword());
			preparedStatement.setObject(EMAIL_IND, patron.getEmail());
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new SystemUnavailableException(
						"Adding patron failed, no rows affected.");
			}
		} catch (SQLException e) {
			// TODO: analyzovat sql kod a hodit vyjimku PatronExists
			// pokud uz email existuje?
		} finally {
			close(null, preparedStatement, connection);
		}

	}

	@Override
	public Patron findByEmail(String email) throws SQLException {
		// TODO Automaticky generovaný stub metody
		return null;
	}

	@Override
	public Patron findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Patron patron = null;

		try {
			connection = factory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
			preparedStatement.setObject(PATRON_ID_IND, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				patron = mapPatron(resultSet);
			}

		} catch (SQLException e) {
			// TODO 
			e.printStackTrace();
		} finally {
			close(resultSet, preparedStatement, connection);
		}
		return patron;
	}

	@Override
	public Collection<Copy> getCopies(Patron patron) throws SQLException {
		// TODO Automaticky generovaný stub metody
		return null;
	}

	@Override
	public void remove(Patron patron) throws SQLException {
		// TODO Automaticky generovaný stub metody

	}

	@Override
	public Collection<LoanedCopy> retriveLoanedCopies(Patron patron)
			throws SQLException {
		// TODO Automaticky generovaný stub metody
		return null;
	}

	@Override
	public void update(Patron patron) throws SQLException {
		// TODO Automaticky generovaný stub metody

	}

	private static Patron mapPatron(ResultSet rs) throws SQLException {
		return new Patron(rs.getInt(PATRON_ID_IND), rs
				.getString(FIRST_NAME_IND), rs.getString(LAST_NAME_IND), rs
				.getString(PASSWORD_IND), rs.getString(EMAIL_IND));
	}

}
