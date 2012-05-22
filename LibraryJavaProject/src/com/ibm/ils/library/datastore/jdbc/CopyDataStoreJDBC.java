package com.ibm.ils.library.datastore.jdbc;

import static com.ibm.ils.library.datastore.util.DAOUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.ibm.ils.library.datastore.ConnectionFactory;
import com.ibm.ils.library.datastore.CopyDataStore;
import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.ItemDataStore;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.CopyExists;
import com.ibm.ils.library.model.exceptions.CopyNotFound;
import com.ibm.ils.library.model.exceptions.ItemNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

public class CopyDataStoreJDBC implements CopyDataStore {
	private ConnectionFactory factory;

	private static final String SQLSTATE_ALREADY_EXISTS = "23505";
	private static final String SQLSTATE_INVALID_FOREIGN_KEY = "23503";

	private static final String SQL_ADD = "INSERT INTO LIBRARY.COPY "
			+ "(item_key, copy_number, loanable) VALUES (?, ?, ?)";	

	private static final String SQL_FIND_COPIES_FOR_ITEM_ID = "SELECT " +
			"COPY.item_key, COPY.copy_number, COPY.loanable, 0, 0, null " +
			"FROM LIBRARY.COPY COPY WHERE COPY.item_key = ?";

	private static final String SQL_FIND_COPIES_FOR_PATRON_ID = "SELECT "
			+ "COPY.item_key, COPY.copy_number, COPY.loanable, "
			+ "ONLOAN.patron_id, ONLOAN.times_renewed, ONLOAN.due_date "
			+ "FROM LIBRARY.COPY COPY, LIBRARY.ONLOAN ONLOAN "
			+ "WHERE COPY.item_key = ONLOAN.item_key "
			+ "AND COPY.copy_number = ONLOAN.copy_number "
			+ "AND ONLOAN.patron_id = ?";

	private static final String SQL_FIND_LOANED_COPIES_FOR_PATRON_ID = "SELECT "
			+ "COPY.item_key, COPY.copy_number, "
			+ "ITEM.author, ITEM.title, "
			+ "ONLOAN.due_date, ONLOAN.times_renewed "
			+ "FROM LIBRARY.COPY COPY, LIBRARY.ONLOAN ONLOAN, LIBRARY.ITEM ITEM "
			+ "WHERE COPY.item_key = ONLOAN.item_key "
			+ "AND COPY.item_key = ITEM.item_key "
			+ "AND COPY.copy_number = ONLOAN.copy_number "
			+ "AND ONLOAN.patron_id = ? ";

	private static final String SQL_FIND_MAX_COPY_NUMBER = "SELECT "
			+ "MAX(copy_number) FROM LIBRARY.COPY WHERE item_key = ?";

	private static final String SQL_REMOVE_COPY = "DELETE FROM LIBRARY.COPY "
			+ "WHERE item_key = ? AND copy_number = ?";

	private static final String SQL_REMOVE_ONLOAN = "DELETE FROM LIBRARY.ONLOAN "
			+ "WHERE patron_id = ? AND item_key = ? AND copy_number = ?";

	private static final String SQL_RENEW = "UPDATE LIBRARY.ONLOAN SET "
			+ "due_date = ?, times_renewed = ? "
			+ "WHERE patron_id = ? AND item_key = ? AND copy_number = ?";

	private static final String SQL_UPDATE_COPY = "UPDATE LIBRARY.COPY SET "
			+ "loanable = ? WHERE item_key = ? AND copy_number = ?";

	private static final String SQL_UPDATE_ONLOAN = "UPDATE LIBRARY.ONLOAN SET "
			+ "times_renewed = ?, due_date = ? "
			+ "WHERE patron_id = ? AND item_key = ? AND copy_number = ?";

	

	public CopyDataStoreJDBC(ConnectionFactory factory) {
		this.factory = factory;
	}

	@Override
	public void add(Copy copy) throws SystemUnavailableException,
			OperationFailed, CopyExists {

		Connection connection = null;
		PreparedStatement statementInsert = null;
		int nextId = 0;

		// get the add prepared statement
		try {
			connection = factory.getConnection();

			// do the add
			try {
				// get next id
				nextId = findMaxCopyNumber(copy.getItemId(), connection) + 1;
				// add copy to database
				statementInsert = connection.prepareStatement(SQL_ADD);
				statementInsert.setInt(1, copy.getItemId());
				statementInsert.setInt(2, nextId);
				statementInsert.setString(3, "T");
				statementInsert.executeUpdate();
				copy.setCopyNumber(nextId);
			} catch (SQLException e) {
				if (e.getSQLState().equals(SQLSTATE_ALREADY_EXISTS)) {
					throw new CopyExists(copy.getItemId(), nextId);
				} else if (e.getSQLState().equals(SQLSTATE_INVALID_FOREIGN_KEY)) {
					throw new OperationFailed("Item with id: "
							+ copy.getItemId() + " doesn't exists.");
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

	private int findMaxCopyNumber(int itemKey, Connection connection)
			throws OperationFailed, SystemUnavailableException {
		PreparedStatement statementFindMax = null;
		ResultSet resultSet = null;
		int max = 0;

		// get the find prepared statement
		try {
			statementFindMax = connection
					.prepareStatement(SQL_FIND_MAX_COPY_NUMBER);

			// do the find
			try {
				statementFindMax.setInt(1, itemKey);
				resultSet = statementFindMax.executeQuery();
				if (resultSet.next()) {
					max = resultSet.getInt(1);
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		}
		return max;
	}

	@Override
	public Collection<Copy> findCopiesForItemId(int id) throws OperationFailed,
			SystemUnavailableException {
		Connection connection = null;
		PreparedStatement statementFind = null;
		ResultSet resultSet = null;
		Collection<Copy> copies = new ArrayList<Copy>();

		// get the find prepared statement
		try {
			connection = factory.getConnection();
			statementFind = connection
					.prepareStatement(SQL_FIND_COPIES_FOR_ITEM_ID);

			// get list of copies
			try {
				statementFind.setInt(1, id);
				resultSet = statementFind.executeQuery();
				mapCopies(resultSet, copies);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new OperationFailed(e);
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(resultSet, statementFind, connection);
		}

		return copies;
	}

	@Override
	public Collection<Copy> findCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed {
		Connection connection = null;
		PreparedStatement statementFind = null;
		ResultSet resultSet = null;
		Collection<Copy> copies = new ArrayList<Copy>();

		// get the find prepared statement
		try {
			connection = factory.getConnection();
			statementFind = connection
					.prepareStatement(SQL_FIND_COPIES_FOR_PATRON_ID);

			// get list of copies
			try {
				statementFind.setInt(1, id);
				resultSet = statementFind.executeQuery();
				mapCopies(resultSet, copies);
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(resultSet, statementFind, connection);
		}

		return copies;
	}

	@Override
	public Collection<LoanedCopy> findLoanedCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed {
		Connection connection = null;
		PreparedStatement statementFind = null;
		ResultSet resultSet = null;
		Collection<LoanedCopy> copies = new ArrayList<LoanedCopy>();

		// get the find prepared statement
		try {
			connection = factory.getConnection();
			statementFind = connection
					.prepareStatement(SQL_FIND_LOANED_COPIES_FOR_PATRON_ID);

			// get list of copies
			try {
				statementFind.setInt(1, id);
				resultSet = statementFind.executeQuery();
				mapLoanedCopies(resultSet, copies);
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}
		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(resultSet, statementFind, connection);
		}

		return copies;
	}

	@Override
	public Item getItem(Copy copy) throws SystemUnavailableException,
			OperationFailed, ItemNotFound {
		ItemDataStore itemDataStore = DataStoreFactory.getItemDataStore();
		return itemDataStore.findById(copy.getItemId());
	}

	@Override
	public Patron getPatron(Copy copy) throws PatronNotFound,
			SystemUnavailableException, OperationFailed {
		PatronDataStore patronDataStore = DataStoreFactory.getPatronDataStore();
		return patronDataStore.findById(copy.getPatronId());
	}

	@Override
	public void remove(Copy copy) throws OperationFailed,
			SystemUnavailableException, CopyNotFound {
		Connection connection = null;
		PreparedStatement stRemoveOnloan = null;
		PreparedStatement stRemoveCopy = null;

		// get the delete prepared statement
		try {
			connection = factory.getConnection();
			// first delete onloan for this copy
			stRemoveOnloan = connection.prepareStatement(SQL_REMOVE_ONLOAN);
			stRemoveCopy = connection.prepareStatement(SQL_REMOVE_COPY);

			// do the delete
			try {
				// execute remove onloan if patron_id is set
				if (copy.getPatronId() > 0) {
					stRemoveOnloan.setInt(1, copy.getPatronId());
					stRemoveOnloan.setInt(2, copy.getItemId());
					stRemoveOnloan.setInt(3, copy.getCopyNumber());
					stRemoveOnloan.executeUpdate();
				}

				// execute remove copy
				stRemoveCopy.setInt(1, copy.getItemId());
				stRemoveCopy.setInt(2, copy.getCopyNumber());
				int affectedRows = stRemoveCopy.executeUpdate();
				if (affectedRows == 0) {
					throw new CopyNotFound(copy.getItemId(),
							copy.getCopyNumber());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new OperationFailed(e);
			}

		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(null, stRemoveOnloan, connection);
			close(null, stRemoveCopy, connection);
		}

	}

	@Override
	public void renewCopy(Copy copy, java.sql.Date dueDate, int timesRenewed)
			throws CopyNotFound, OperationFailed, SystemUnavailableException {
		Connection connection = null;
		PreparedStatement statementUpdate = null;

		// get the update prepared statement
		try {
			connection = factory.getConnection();
			statementUpdate = connection.prepareStatement(SQL_RENEW);

			// do the update
			try {
				statementUpdate.setDate(1, dueDate);
				statementUpdate.setInt(2, timesRenewed);
				statementUpdate.setInt(3, copy.getPatronId());
				statementUpdate.setInt(4, copy.getItemId());
				statementUpdate.setInt(5, copy.getCopyNumber());
				int affectedRows = statementUpdate.executeUpdate();
				if (affectedRows == 0) {
					throw new CopyNotFound(copy.getItemId(),
							copy.getCopyNumber());
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}

		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(null, statementUpdate, connection);
		}

	}

	@Override
	public void update(Copy copy) throws CopyNotFound, OperationFailed,
			SystemUnavailableException {
		Connection connection = null;
		PreparedStatement stUpdateOnloan = null;
		PreparedStatement stUpdateCopy = null;

		// get the update prepared statement
		try {
			connection = factory.getConnection();
			stUpdateOnloan = connection.prepareStatement(SQL_UPDATE_ONLOAN);
			stUpdateCopy = connection.prepareStatement(SQL_UPDATE_COPY);

			// do the update
			try {
				if (copy.getPatronId() > 0) {
					// execute update onloan (if exists)
					populateStatementForUpdateOnloan(stUpdateOnloan, copy);
					stUpdateOnloan.executeUpdate();
				}

				// execute update copy (exists or error)
				populateStatementForUpdateCopy(stUpdateCopy, copy);
				int affectedRows = stUpdateCopy.executeUpdate();
				if (affectedRows == 0) {
					throw new CopyNotFound(copy.getItemId(),
							copy.getCopyNumber());
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}

		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(null, stUpdateOnloan, connection);
			close(null, stUpdateCopy, connection);
		}

	}

	private static void populateStatementForUpdateOnloan(
			PreparedStatement statement, Copy copy) throws SQLException {
		statement.setInt(1, copy.getTimesRenewed());
		java.sql.Date date = (copy.getDue() != null) ? new java.sql.Date(copy
				.getDue().getTime()) : null;
		statement.setDate(2, date);
		statement.setInt(3, copy.getPatronId());
		statement.setInt(4, copy.getItemId());
		statement.setInt(5, copy.getCopyNumber());
	}

	private static void populateStatementForUpdateCopy(
			PreparedStatement statement, Copy copy) throws SQLException {
		String value = copy.isLoanable() ? "T" : "F";
		statement.setString(1, value);
		statement.setInt(2, copy.getItemId());
		statement.setInt(3, copy.getCopyNumber());
	}

	private static void mapCopies(ResultSet rs, Collection<Copy> copies)
			throws SQLException {
		boolean isLoanable;
		java.util.Date dueUtil;
		java.sql.Date dueSql;
		int itemId;
		int patronId;
		int timesRenewed;
		int copyNumber;
		Copy copy = null;

		while (rs.next()) {
			isLoanable = rs.getString(3).equalsIgnoreCase("T");
			dueSql = rs.getDate(6);
			dueUtil = (dueSql == null) ? null : new Date(dueSql.getTime());
			itemId = rs.getInt(1);
			patronId = rs.getInt(4);
			timesRenewed = rs.getInt(5);
			copyNumber = rs.getInt(2);

			copy = new Copy(isLoanable, dueUtil, itemId, patronId,
					timesRenewed, copyNumber);
			copies.add(copy);
		}

	}

	private static void mapLoanedCopies(ResultSet rs,
			Collection<LoanedCopy> copies) throws SQLException {
		int itemId;
		int copyNumber;
		String author;
		String title;
		java.util.Date dueUtil;
		java.sql.Date dueSql;
		int timesRenewed;
		LoanedCopy loanCopy = null;

		while (rs.next()) {
			itemId = rs.getInt(1);
			copyNumber = rs.getInt(2);
			author = rs.getString(3);
			title = rs.getString(4);
			dueSql = rs.getDate(5);
			dueUtil = (dueSql == null) ? null : new Date(dueSql.getTime());
			timesRenewed = rs.getInt(6);

			loanCopy = new LoanedCopy(author, title, copyNumber, dueUtil,
					itemId, timesRenewed);
			copies.add(loanCopy);
		}
	}

}
