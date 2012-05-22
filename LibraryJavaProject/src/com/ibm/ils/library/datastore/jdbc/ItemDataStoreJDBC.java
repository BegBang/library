package com.ibm.ils.library.datastore.jdbc;

import static com.ibm.ils.library.datastore.util.DAOUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import com.ibm.ils.library.datastore.ConnectionFactory;
import com.ibm.ils.library.datastore.CopyDataStore;
import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.ItemDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.ItemNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public class ItemDataStoreJDBC implements ItemDataStore {
	private ConnectionFactory factory;

	private static final String SQL_ADD = "INSERT INTO LIBRARY.ITEM (item_key, type, isbn_equiv, title, author, oversize, volumes, publish_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_ID = "SELECT item_key, type, isbn_equiv, title, author, oversize, volumes, publish_date FROM LIBRARY.ITEM WHERE item_key = ?";
	private static final String SQL_REMOVE = "DELETE FROM LIBRARY.ITEM WHERE item_key = ?";
	private static final String SQL_UPDATE = "UPDATE LIBRARY.ITEM SET type = ?, isbn_equiv = ?, title = ?, author = ?, oversize = ?, volumes = ?, publish_date = ? WHERE item_key = ?";

	private static final String SIZE_OVERSIZED = "O";
	private static final String SIZE_STANDARD = "S";
	private static final String SQLSTATE_ALREADY_EXISTS = "23505";

	public ItemDataStoreJDBC(ConnectionFactory factory) {
		this.factory = factory;
	}

	@Override
	public void add(Item item) throws SystemUnavailableException,
			OperationFailed, ItemExists {
		Connection connection = null;
		PreparedStatement statementInsert = null;

		// get the add prepared statement
		try {
			connection = factory.getConnection();
			statementInsert = connection.prepareStatement(SQL_ADD);

			// do the add
			try {
				populateStatementForAdd(statementInsert, item);
				statementInsert.executeUpdate();
			} catch (SQLException e) {
				if (e.getSQLState().equals(SQLSTATE_ALREADY_EXISTS)) {
					throw new ItemExists(item.getId());
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
	public Item findById(int id) throws SystemUnavailableException,
			OperationFailed, ItemNotFound {
		Connection connection = null;
		PreparedStatement statementFind = null;
		ResultSet resultSet = null;
		Item item = null;

		// get the find prepared statement
		try {
			connection = factory.getConnection();
			statementFind = connection.prepareStatement(SQL_FIND_BY_ID);

			// do the find
			try {
				statementFind.setInt(1, id);
				resultSet = statementFind.executeQuery();
				if (resultSet.next()) {
					item = mapItem(resultSet);
				} else {
					throw new ItemNotFound(id);
				}
			} catch (SQLException e) {
				throw new OperationFailed(e);
			}

		} catch (SQLException e) {
			throw new SystemUnavailableException(e);
		} finally {
			close(resultSet, statementFind, connection);
		}
		return item;
	}

	@Override
	public Collection<Copy> getCopies(Item item) throws OperationFailed,
			SystemUnavailableException {
		CopyDataStore copyDataStore = DataStoreFactory.getCopyDataStore();
		return copyDataStore.findCopiesForItemId(item.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.ils.library.datastore.ItemDataStore#remove(com.ibm.ils.library.
	 * model.Item)
	 */
	@Override
	public void remove(Item item) throws SystemUnavailableException,
			OperationFailed, ItemNotFound {
		Connection connection = null;
		PreparedStatement statementRemove = null;

		// get the delete prepared statement
		try {
			connection = factory.getConnection();
			statementRemove = connection.prepareStatement(SQL_REMOVE);

			// do the delete
			try {
				statementRemove.setInt(1, item.getId());
				int affectedRows = statementRemove.executeUpdate();
				if (affectedRows == 0) {
					throw new ItemNotFound(item.getId());
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
	public void update(Item item) throws SystemUnavailableException,
			ItemNotFound, OperationFailed {
		Connection connection = null;
		PreparedStatement statementUpdate = null;

		// get the update prepared statement
		try {
			connection = factory.getConnection();
			statementUpdate = connection.prepareStatement(SQL_UPDATE);

			// do the update
			try {
				populateStatementForUpdate(statementUpdate, item);
				int affectedRows = statementUpdate.executeUpdate();
				if (affectedRows == 0) {
					throw new ItemNotFound(item.getId());
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

	private static Item mapItem(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);//
		char medium = rs.getString(2).charAt(0);
		String isbnEquivalent = rs.getString(3);
		String title = rs.getString(4);
		String author = rs.getString(5);
		String overString = rs.getString(6);
		boolean oversize;
		if (overString.toUpperCase().equals(SIZE_OVERSIZED)) {
			oversize = true;
		} else {
			oversize = false;
		}
		Integer volumes = (Integer) rs.getObject(7);
		java.sql.Date publishedSql = rs.getDate(8);
		java.util.Date published;
		published = (publishedSql == null) ? null : new Date(
				publishedSql.getTime());

		return new Item(id, medium, isbnEquivalent, title, author, oversize,
				volumes, published);
	}

	private static PreparedStatement populateStatementForAdd(
			PreparedStatement statement, Item item) throws SQLException {
		statement.setInt(1, item.getId());
		statement.setString(2, String.valueOf(item.getMedium()));
		statement.setString(3, item.getIsbnEquivalent());
		statement.setString(4, item.getTitle());
		statement.setString(5, item.getAuthor());
		String size;
		if (item.isOversize()) {
			size = SIZE_OVERSIZED;
		} else {
			size = SIZE_STANDARD;
		}
		statement.setString(6, size);
		statement.setObject(7, item.getVolume());
		java.sql.Date published = (item.getPublished() == null) ? null
				: new java.sql.Date(item.getPublished().getTime());
		statement.setDate(8, published);
		return statement;
	}

	private static PreparedStatement populateStatementForUpdate(
			PreparedStatement statement, Item item) throws SQLException {
		statement.setString(1, String.valueOf(item.getMedium()));
		statement.setString(2, item.getIsbnEquivalent());
		statement.setString(3, item.getTitle());
		statement.setString(4, item.getAuthor());
		String size;
		if (item.isOversize()) {
			size = SIZE_OVERSIZED;
		} else {
			size = SIZE_STANDARD;
		}
		statement.setString(5, size);
		statement.setObject(6, item.getVolume());
		java.sql.Date published = (item.getPublished() == null) ? null
				: new java.sql.Date(item.getPublished().getTime());
		statement.setDate(7, published);
		statement.setInt(8, item.getId());
		return statement;
	}

}
