package com.ibm.ils.library.datastore;

import java.util.Collection;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

public interface PatronDataStore {

  /**
   * @param patron
   * @throws SystemUnavailableException
   * @throws OperationFailed
   */
  public void add(Patron patron) throws PatronExists,
      SystemUnavailableException, OperationFailed;

  public Patron findByEmail(String email) throws PatronNotFound,
      SystemUnavailableException, OperationFailed;

  /**
   * @param id
   *          id of patron
   * @return patron matching the id, otherwise null
   * @throws OperationFailed
   */
  public Patron findById(int id) throws PatronNotFound,
      SystemUnavailableException, OperationFailed;

  public Collection<Copy> getCopies(Patron patron)
      throws SystemUnavailableException;

  public void remove(Patron patron) throws PatronNotFound,
      SystemUnavailableException, OperationFailed;

  public Collection<LoanedCopy> retriveLoanedCopies(Patron patron)
      throws SystemUnavailableException;

  public void update(Patron patron) throws SystemUnavailableException,
      OperationFailed, PatronNotFound, PatronExists;

}
