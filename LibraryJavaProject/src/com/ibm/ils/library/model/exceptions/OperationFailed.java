package com.ibm.ils.library.model.exceptions;

public class OperationFailed extends Exception {
	private static final long serialVersionUID = 2509413637735360914L;
	private Exception exception;

	public OperationFailed() {
	}

	public OperationFailed(String message) {
		super(message);
	}

	public OperationFailed(Exception exception) {
		super("Operation failed.");
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
	
	
}
