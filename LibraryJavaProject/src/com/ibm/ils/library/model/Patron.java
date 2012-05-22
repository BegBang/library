package com.ibm.ils.library.model;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.datastore.util.LibraryIdGenerator;
import com.ibm.ils.library.model.exceptions.InvalidPassword;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

/**
 * Represents client of the library.
 *
 */
public class Patron implements Serializable {
	private static final long serialVersionUID = -3048224323818206645L;
	private static final int MIN_PASSWORD_LENGTH = 5;

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private static PatronDataStore dataStore;

	static {
		dataStore = DataStoreFactory.getPatronDataStore();
	}

	public Patron() {
		this(0, null, null, null, null);
	}

	public Patron(String firstName, String lastName, String password,
			String email) {
		this(0, firstName, lastName, password, email);
	}

	public Patron(int id, String firstName, String lastName, String password,
			String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public void add() throws PatronExists, SystemUnavailableException,
			OperationFailed, InvalidPassword {
		// generate id
		if (getId() == 0) {
			setId(LibraryIdGenerator.generateId());
		}

		// check password validity
		verifyPassword();

		dataStore.add(this);

	}

	public static Patron findByEmail(String email) throws PatronNotFound,
			SystemUnavailableException, OperationFailed {
		return dataStore.findByEmail(email);
	}

	public static Patron findById(int id) throws PatronNotFound,
			SystemUnavailableException, OperationFailed {
		return dataStore.findById(id);
	}

	public Collection<Copy> getCopies() throws SystemUnavailableException,
			OperationFailed {
		return dataStore.getCopies(this);
	}

	public void remove() throws PatronNotFound, SystemUnavailableException,
			OperationFailed {
		dataStore.remove(this);
	}

	public Collection<LoanedCopy> retriveLoanedCopies()
			throws SystemUnavailableException, OperationFailed {
		return dataStore.retriveLoanedCopies(this);
	}

	public void update() throws SystemUnavailableException, OperationFailed,
			PatronNotFound, PatronExists {
		dataStore.update(this);
	}	

	public void renew(Collection<LoanedCopy> list) throws OperationFailed,
			SystemUnavailableException {
		for (LoanedCopy loanedCopy : list) {
			if (loanedCopy.isRenewRequest()) {
				loanedCopy.renew(getId());
			}
		}
	}

	private void verifyPassword() throws InvalidPassword {
		String password = getPassword();
		// password shouldn't be empty or null
		if (password == null || password.length() == 0) {
			throw new InvalidPassword("Password is missing");
		}

		// password shouldn't contain any blank
		for (int i = 0; i < password.length(); i++) {
			if (Character.isWhitespace(password.charAt(i))) {
				throw new InvalidPassword(
						"Password contains one or more blank characters");
			}
		}

		// password must be at least 5 characters long
		if (password.length() < MIN_PASSWORD_LENGTH) {
			throw new InvalidPassword("Password must be at least "
					+ MIN_PASSWORD_LENGTH + " characters long");
		}
	}

	//
	// Getters and setters
	//
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format(
				"Patron[id=%d,firstName=%s,lastName=%s,password=%s,email=%s]",
				id, firstName, lastName, password, email);
	}

}
