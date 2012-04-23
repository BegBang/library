package com.ibm.ils.library.datastore.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.ibm.ils.library.datastore.ConnectionFactory;
import com.ibm.ils.library.datastore.CopyDataStore;
import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

import static com.ibm.ils.library.datastore.util.DAOUtil.close;

public class PatronDataStoreJDBC implements PatronDataStore {
	private ConnectionFactory factory;

	private static final String SQL_ADD = "INSERT INTO LIBRARY.PATRON (patron_id, first_name, last_name, password, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_EMAIL = "SELECT patron_id, first_name, last_name, password, email FROM LIBRARY.PATRON where email = ?";
	private static final String SQL_FIND_BY_ID = "SELECT patron_id, first_name, last_name, password, email FROM LIBRARY.PATRON where patron_id = ?";
	private static final String SQL_REMOVE = "DELETE FROM LIBRARY.PATRON WHERE patron_id = ?";
	private static final String SQL_UPDATE = "UPDATE LIBRARY.PATRON SET first_name = ?, last_name = ?, password = ?, email = ?  WHERE patron_id = ?";

	private static final String SQLSTATE_ALREADY_EXISTS = "23505";

	public PatronDataStoreJDBC(ConnectionFactory factory) {
		this.factory = factory;
	}

	/*
	 * @see
	 * com.ibm.ils.library.datastore.PatronDataStore#add(com.ibm.ils.library
	 * .model.Patron)
	 */
	@Override
	public void add(Patron patron) throws PatronExists,
			SystemUnavailableException, OperationFailed {
		Connection connection = null;
		PreparedStatement statementInsert = null;

		// get the add prepared statement
		try {
			connection = factory.getConnection();
			statementInsert = connection.prepareStatement(SQL_ADD);

			// do the add
			try {
				populateStatementForAdd(statementInsert, patron);
				statementInsert.executeUpdate();
			} catch (SQLException e) {
				if (e.getSQLState().equals(SQLSTATE_ALREADY_EXISTS)) {
					throw new PatronExists(patron.getId(), patron.getEmail());
				} else {
					throw new OperationFailed(e);
				}
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(null, statementInsert, connection);
		}

	}

	@Override
	public Patron findByEmail(String email) throws PatronNotFound,
			SystemUnavailableException, OperationFailed {
		Connection connection = null;
		PreparedStatement statementFind = null;
		ResultSet resultSet = null;
		Patron patron = null;

		// get the find prepared statement
		try {
			connection = factory.getConnection();
			statementFind = connection.prepareStatement(SQL_FIND_BY_EMAIL);

			// do the find
			try {
				statementFind.setString(1, email);
				resultSet = statementFind.executeQuery();
				if (resultSet.next()) {
					patron = mapPatron(resultSet);
				} else {
					throw new PatronNotFound(email);
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(resultSet, statementFind, connection);
		}
		return patron;
	}

	@Override
	public Patron findById(int id) throws PatronNotFound,
			SystemUnavailableException, OperationFailed {
		Connection connection = null;
		PreparedStatement statementFind = null;
		ResultSet resultSet = null;
		Patron patron = null;

		// get the find prepared statement
		try {
			connection = factory.getConnection();
			statementFind = connection.prepareStatement(SQL_FIND_BY_ID);

			// do the find
			try {
				statementFind.setInt(1, id);
				resultSet = statementFind.executeQuery();
				if (resultSet.next()) {
					patron = mapPatron(resultSet);
				} else {
					throw new PatronNotFound(id);
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(resultSet, statementFind, connection);
		}
		return patron;
	}

	@Override
	public Collection<Copy> getCopies(Patron patron)
			throws SystemUnavailableException, OperationFailed {
		CopyDataStore copyDataStore = DataStoreFactory.getCopyDataStore();
		return copyDataStore.findCopiesForPatronId(patron.getId());
	}

	@Override
	public void remove(Patron patron) throws PatronNotFound,
			SystemUnavailableException, OperationFailed {
		Connection connection = null;
		PreparedStatement statementRemove = null;

		// get the delete prepared statement
		try {
			connection = factory.getConnection();
			statementRemove = connection.prepareStatement(SQL_REMOVE);

			// do the delete
			try {
				statementRemove.setInt(1, patron.getId());
				int affectedRows = statementRemove.executeUpdate();
				if (affectedRows == 0) {
					throw new PatronNotFound(patron.getId());
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}

		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(null, statementRemove, connection);
		}

	}

	@Override
	public Collection<LoanedCopy> retriveLoanedCopies(Patron patron)
			throws SystemUnavailableException {
		// TODO Automaticky generovan� stub metody
		return null;
	}

	@Override
	public void update(Patron patron) throws SystemUnavailableException,
			OperationFailed, PatronNotFound, PatronExists {
		// TODO co kdyz se pokousi updatovat validni hodnotu na nevalidni
		Connection connection = null;
		PreparedStatement statementUpdate = null;

		// get the update prepared statement
		try {
			connection = factory.getConnection();
			statementUpdate = connection.prepareStatement(SQL_UPDATE);

			// do the update
			try {
				populateStatementForUpdate(statementUpdate, patron);
				int affectedRows = statementUpdate.executeUpdate();
				System.out.println("affectedRows: " + affectedRows);
				if (affectedRows == 0) {
					throw new PatronNotFound(patron.getId());
				}
			} catch (SQLException e) {
				if (e.getSQLState().equals(SQLSTATE_ALREADY_EXISTS)) {
					// inserted email exists with another patron
					throw new PatronExists(patron.getEmail());
				} else {
					throw new OperationFailed(e);
				}
			}

		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(null, statementUpdate, connection);
		}
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Patron mapPatron(ResultSet rs) throws SQLException {
		return new Patron(rs.getInt(1), rs.getString(2), rs.getString(3),
				rs.getString(4), rs.getString(5));
	}

	/**
	 * Populates PreparedStatement object with data from Patron object.
	 * 
	 * @param statement
	 * @param patron
	 * @return
	 * @throws SQLException
	 */
	private static PreparedStatement populateStatementForAdd(
			PreparedStatement statement, Patron patron) throws SQLException {
		statement.setInt(1, patron.getId());
		statement.setString(2, patron.getFirstName());
		statement.setString(3, patron.getLastName());
		statement.setString(4, patron.getPassword());
		statement.setString(5, patron.getEmail());
		return statement;
	}

	private static PreparedStatement populateStatementForUpdate(
			PreparedStatement statement, Patron patron) throws SQLException {
		statement.setString(1, patron.getFirstName());
		statement.setString(2, patron.getLastName());
		statement.setString(3, patron.getPassword());
		statement.setString(4, patron.getEmail());
		statement.setInt(5, patron.getId());
		return statement;
	}

}
