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
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public class CopyDataStoreJDBC implements CopyDataStore {
  private ConnectionFactory factory;

  private static final String SQL_FIND_COPIES_FOR_PATRON_ID = "SELECT "
      + "COPY.item_key, COPY.copy_number, COPY.loanable, "
      + "ONLOAN.patron_id, ONLOAN.times_renewed, ONLOAN.due_date "
      + "FROM LIBRARY.COPY COPY, LIBRARY.ONLOAN ONLOAN "
      + "WHERE COPY.item_key = ONLOAN.item_key "
      + "AND COPY.copy_number = ONLOAN.copy_number "
      + "AND ONLOAN.patron_id = ?";

  private static final String SQL_FIND_LOANED_COPIES_FOR_PATRON_ID = "SELECT "
      + "COPY.item_key, COPY.copy_number, " + "ITEM.author, ITEM.title, "
      + "ONLOAN.due_date, ONLOAN.times_renewed "
      + "FROM LIBRARY.COPY COPY, LIBRARY.ONLOAN ONLOAN, LIBRARY.ITEM ITEM "
      + "WHERE COPY.item_key = ONLOAN.item_key "
      + "AND COPY.item_key = ITEM.item_key "
      + "AND COPY.copy_number = ONLOAN.copy_number "
      + "AND ONLOAN.patron_id = ? ";

  public CopyDataStoreJDBC(ConnectionFactory factory) {
    this.factory = factory;
  }

  @Override
  public void add(Copy patron) {
    // TODO Automaticky generovan� stub metody

  }

  @Override
  public Collection<Copy> findCopiesForItemId(int id) {
    // TODO Automaticky generovan� stub metody
    return null;
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
        statementFind.setObject(1, id);
        resultSet = statementFind.executeQuery();
        mapCopies(resultSet, copies);
      }
      catch (SQLException e) {
        e.printStackTrace();
        throw new OperationFailed(e);
      }
    }
    catch (SQLException e) {
      throw new SystemUnavailableException(e);
    }
    finally {
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
        statementFind.setObject(1, id);
        resultSet = statementFind.executeQuery();
        mapLoanedCopies(resultSet, copies);
      }
      catch (SQLException e) {
        e.printStackTrace();
        throw new OperationFailed(e);
      }
    }
    catch (SQLException e) {
      throw new SystemUnavailableException(e);
    }
    finally {
      close(resultSet, statementFind, connection);
    }

    return copies;
  }

  @Override
  public Item getItem(Copy copy) {
    // TODO Automaticky generovan� stub metody
    return null;
  }

  @Override
  public Patron getPatron(Copy copy) {
    // TODO Automaticky generovan� stub metody
    return null;
  }

  @Override
  public void remove(Copy copy) {
    // TODO Automaticky generovan� stub metody

  }

  @Override
  public void renewCopy(Copy copy, Date dueDate) {
    // TODO Automaticky generovan� stub metody

  }

  @Override
  public void update(Copy copy) {
    // TODO Automaticky generovan� stub metody

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
      if (rs.wasNull()) {
        dueUtil = null;
      }
      else {
        dueUtil = new Date(dueSql.getTime());
      }
      itemId = rs.getInt(1);
      patronId = rs.getInt(4);
      timesRenewed = rs.getInt(5);
      copyNumber = rs.getInt(2);

      copy = new Copy(isLoanable, dueUtil, itemId, patronId, timesRenewed,
          copyNumber);
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
      if (rs.wasNull()) {
        dueUtil = null;
      }
      else {
        dueUtil = new Date(dueSql.getTime());
      }
      timesRenewed = rs.getInt(6);

      loanCopy = new LoanedCopy(author, title, copyNumber, dueUtil, itemId,
          timesRenewed);
      copies.add(loanCopy);
    }
  }

}
