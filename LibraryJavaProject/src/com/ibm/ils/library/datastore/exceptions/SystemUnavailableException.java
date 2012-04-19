package com.ibm.ils.library.datastore.exceptions;

@SuppressWarnings("serial")
public class SystemUnavailableException extends Exception {
	@SuppressWarnings("unused")
  private Exception exception;

	public SystemUnavailableException(String message) {
		super(message);
	}

	public SystemUnavailableException(Exception exception) {
		super("System is currently unavailable");
		this.exception = exception;
	}

}
