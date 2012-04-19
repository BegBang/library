package com.ibm.ils.library.datastore;

import java.util.Collection;
import java.util.Date;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public interface CopyDataStore {

	public void add(Copy patron);

	public Collection<Copy> findCopiesForItemId(int id);

	public Collection<Copy> findCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed;

  public Collection<LoanedCopy> findLoanedCopiesForPatronId(int id)
      throws SystemUnavailableException, OperationFailed;

	public Item getItem(Copy copy);

	public Patron getPatron(Copy copy);

	public void remove(Copy copy);

	public void renewCopy(Copy copy, Date dueDate);

	public void update(Copy copy);

}
