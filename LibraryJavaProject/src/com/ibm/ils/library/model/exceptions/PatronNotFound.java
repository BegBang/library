package com.ibm.ils.library.model.exceptions;

public class PatronNotFound extends Exception {	
	private static final long serialVersionUID = -9002733017784851723L;

	public PatronNotFound(int id) {
		super("Patron with id: " + id + " not found.");
	}
	
	public PatronNotFound(String email) {
    super("Patron with email: " + email + " not found.");
  }
}
