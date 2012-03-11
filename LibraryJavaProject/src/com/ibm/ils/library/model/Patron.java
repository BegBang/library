package com.ibm.ils.library.model;

import com.ibm.ils.library.datastore.PatronDataStore;

public class Patron {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private PatronDataStore dataStore;

	static {
		// DataStoreFactory.getPatronDataStore
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

	public Patron(int id, String firstName, String lastName, String email,
			String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public void add() {

	}
	
	public void findById(int id) {
		
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

}
