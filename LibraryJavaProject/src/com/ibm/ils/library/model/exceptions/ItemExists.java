package com.ibm.ils.library.model.exceptions;

public class ItemExists extends Exception {
  private static final long serialVersionUID = 6050296627674230013L;
  
  public ItemExists(int id) {
    super("Item with id: " + id + " allready exists.");
  }

}
