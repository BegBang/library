package com.ibm.ils.library.model.exceptions;

@SuppressWarnings("serial")
public class PatronNotFound extends Exception {	
	
	public PatronNotFound(int id) {
		super("Patron with id: " + id + " not found.");
	}
	
	public PatronNotFound(String email) {
    super("Patron with email: " + email + " not found.");
  }
}
