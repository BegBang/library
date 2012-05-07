package com.ibm.ils.library.model.exceptions;

public class ItemNotFound extends Exception {
  private static final long serialVersionUID = -7065282417756678070L;

  
  public ItemNotFound(int id) {
    super("Item with id: " + id + " not found.");
  }
}
