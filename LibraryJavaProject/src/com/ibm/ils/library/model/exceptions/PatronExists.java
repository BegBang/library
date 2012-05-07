package com.ibm.ils.library.model.exceptions;

public class PatronExists extends Exception {
  private static final long serialVersionUID = 5921279562036599843L;

  

  public PatronExists(int id, String email) {
    super("Patron with id: " + id + " or email: " + email + " allready exists.");
  }
  
  public PatronExists(String email) {
    super("Patron with email: " + email + " allready exists.");
  }

}
