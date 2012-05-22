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

	/**
	 * Add copy to the database.
	 * 
	 * @param copy copy to add
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws CopyExists
	 *             if an copy with this key already exists
	 */
	public void add(Copy copy) throws SystemUnavailableException,
			OperationFailed, CopyExists;

	/**
	 * Find copies for item with specified item id.
	 * 
	 * @param id id of item 
	 * @return collection of copies
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 */
	public Collection<Copy> findCopiesForItemId(int id) throws OperationFailed,
			SystemUnavailableException;

	/**
	 * Find copies for item with specified patron id.
	 * 
	 * @param id id of patron
	 * @return collection of copies
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public Collection<Copy> findCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed;

	/**
	 * Find loaned copies for item with specified patron id.
	 * 
	 * @param id id of patron
	 * @return collection of loaned copies
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public Collection<LoanedCopy> findLoanedCopiesForPatronId(int id)
			throws SystemUnavailableException, OperationFailed;

	/**
	 * Get item for the copy.
	 * 
	 * @param copy the copy
	 * @return an item
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws ItemNotFound
	 *             if an item with this id is not found
	 */
	public Item getItem(Copy copy) throws SystemUnavailableException,
			OperationFailed, ItemNotFound;

	/**
	 * Get patron for the copy.
	 * 
	 * @param copy the copy
	 * @return a patron
	 * @throws PatronNotFound
	 *             if a patron does not exists
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public Patron getPatron(Copy copy) throws PatronNotFound,
			SystemUnavailableException, OperationFailed;

	/**
	 * Remove copy from the database.
	 * 
	 * @param copy the copy
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws CopyNotFound
	 *             if an copy with this key is not found
	 */
	public void remove(Copy copy) throws OperationFailed,
			SystemUnavailableException, CopyNotFound;

	/**
	 * Renew loaned copy for a patron.
	 * 
	 * @param copy the copy
	 * @param dueDate new due date
	 * @param timesRenewed times renewed count
	 * @throws CopyNotFound
	 *             if an copy with this key is not found
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 */
	public void renewCopy(Copy copy, java.sql.Date dueDate, int timesRenewed)
			throws CopyNotFound, OperationFailed, SystemUnavailableException;

	/**
	 * Update copy in the database.
	 * 
	 * @param copy the copy
	 * @throws CopyNotFound
	 *             if an copy with this key is not found
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 */
	public void update(Copy copy) throws CopyNotFound, OperationFailed,
			SystemUnavailableException;

}
