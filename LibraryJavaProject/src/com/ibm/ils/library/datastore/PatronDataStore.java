package com.ibm.ils.library.datastore;

import java.sql.SQLException;
import java.util.Collection;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.Patron;

public interface PatronDataStore {

	/**
	 * @param patron
	 * @throws SystemUnavailableException
	 */
	public void add(Patron patron) throws SystemUnavailableException;

	public Patron findByEmail(String email) throws SQLException;

	/**
	 * @param id id of patron
	 * @return patron matching the id, otherwise null 
	 */
	public Patron findById(int id);

	public Collection<Copy> getCopies(Patron patron) throws SQLException;

	public void remove(Patron patron) throws SQLException;

	public Collection<LoanedCopy> retriveLoanedCopies(Patron patron)
			throws SQLException;

	public void update(Patron patron) throws SQLException;

}
