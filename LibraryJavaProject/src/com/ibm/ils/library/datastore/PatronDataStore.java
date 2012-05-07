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
	 * Add a new patron to the database.
	 * 
	 * @param patron
	 *            the new patron
	 * @throws PatronExists
	 *             if a patron with this id or email already exists
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public void add(Patron patron) throws PatronExists,
			SystemUnavailableException, OperationFailed;

	/**
	 * Find a patron with specified email address.
	 * 
	 * @param email
	 *            the email address
	 * @return the patron with specified email address
	 * @throws PatronNotFound
	 *             if a patron does not exists
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public Patron findByEmail(String email) throws PatronNotFound,
			SystemUnavailableException, OperationFailed;

	/**
	 * Find a patron with specified id.
	 * 
	 * @param id
	 *            the id
	 * @return the patron with specified id
	 * @throws PatronNotFound
	 *             if a patron does not exists
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public Patron findById(int id) throws PatronNotFound,
			SystemUnavailableException, OperationFailed;

	/**
	 * Get copies loaned by the patron.
	 * 
	 * @param patron
	 *            the patron
	 * @return collection of copies (Copy objects) loaned by the patron
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public Collection<Copy> getCopies(Patron patron)
			throws SystemUnavailableException, OperationFailed;

	/**
	 * Remove the patron.
	 * 
	 * @param patron
	 *            the patron
	 * @throws PatronNotFound
	 *             if a patron with specified id does not exists
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public void remove(Patron patron) throws PatronNotFound,
			SystemUnavailableException, OperationFailed;

	/**
	 * Get copies loaned by the patron.
	 * 
	 * @param patron
	 *            the patron
	 * @return collection of copies (LoanedCopy objects) loaned by the patron
	 * @throws SystemUnavailableException
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws OperationFailed 
	 */
	public Collection<LoanedCopy> retriveLoanedCopies(Patron patron)
			throws SystemUnavailableException, OperationFailed;

	/**
	 * Update the patron.
	 * 
	 * @param patron
	 *            the patron
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws PatronNotFound
	 *             if a patron with specified id does not exists
	 * @throws PatronExists
	 *             if a patron with updated this id or email already exists
	 */
	public void update(Patron patron) throws SystemUnavailableException,
			OperationFailed, PatronNotFound, PatronExists;

}
