package com.ibm.ils.library.model;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.PatronDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.datastore.util.LibraryIdGenerator;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

public class Patron implements Serializable {
  private static final long serialVersionUID = -3048224323818206645L;

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

  public Patron(String firstName, String lastName, String password, String email) {
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
      OperationFailed {
    if (this.id == 0) {
      this.id = LibraryIdGenerator.generateId();
    }

    // TODO check password validity

    dataStore.add(this);

  }

  public Patron findByEmail(String email) throws PatronNotFound,
      SystemUnavailableException, OperationFailed {
    return null;
  }

  public Patron findById(int id) throws PatronNotFound,
      SystemUnavailableException, OperationFailed {
    return dataStore.findById(id);
  }

  public Collection<Copy> getCopies(Patron patron)
      throws SystemUnavailableException {
    return null;
  }

  public void remove(Patron patron) throws PatronNotFound,
      SystemUnavailableException, OperationFailed {
    dataStore.remove(patron);
  }

  public Collection<LoanedCopy> retriveLoanedCopies(Patron patron)
      throws SystemUnavailableException {
    return null;
  }

  public void update(Patron patron) throws SystemUnavailableException,
      OperationFailed, PatronNotFound, PatronExists {
    dataStore.update(patron);
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
        "Patron[id=%d,firstName=%s,lastName=%s,password=%s,email=%s]", id,
        firstName, lastName, password, email);
  }

}
