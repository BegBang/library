package com.ibm.ils.library.datastore;

import java.util.Collection;

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

public interface CopyDataStore {

	public void add(Copy copy) throws SystemUnavailableException,
			OperationFailed, CopyExists;

	public Collection<Copy> findCopiesForItemId(int id) throws OperationFailed,
			SystemUnavailableException;

	public Collection<Copy> findCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed;

	public Collection<LoanedCopy> findLoanedCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed;

	public Item getItem(Copy copy) throws SystemUnavailableException,
			OperationFailed, ItemNotFound;

	public Patron getPatron(Copy copy) throws PatronNotFound,
			SystemUnavailableException, OperationFailed;

	public void remove(Copy copy) throws OperationFailed,
			SystemUnavailableException, CopyNotFound;

	public void renewCopy(Copy copy, java.sql.Date dueDate, int timesRenewed)
			throws CopyNotFound, OperationFailed, SystemUnavailableException;

	public void update(Copy copy) throws CopyNotFound, OperationFailed,
			SystemUnavailableException;

}
