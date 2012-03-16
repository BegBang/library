package com.ibm.ils.library.model;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.datastore.util.LibraryIdGenerator;

public class Patron {
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
		this.id = 0;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.password = null;
	}

	public Patron(String firstName, String lastName, String email,
			String password) {
		this.id = 0;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Patron(int id, String firstName, String lastName, String password,
			String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public void add() {
		if (this.id == 0) {
			this.id = LibraryIdGenerator.generateId();
		}

		// TODO check password validity

		try {
			dataStore.add(this);
		} catch (SystemUnavailableException e) {
			// TODO nepodarilo se pridat patrona
			e.printStackTrace();
		}
		// odchytani vyjimky existujici patron a
		// zrejme poslani vyjimky dal

	}

	public void findById(int id) {
		// ma to tu byt ?
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
				"User[id=%d,firstName=%s,lastName=%s,password=%s,email=%s]",
				id, firstName, lastName, password, email);
	}

}
