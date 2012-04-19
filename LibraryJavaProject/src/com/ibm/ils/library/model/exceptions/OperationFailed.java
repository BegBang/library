package com.ibm.ils.library.model.exceptions;

//obecna vyjimka, pouzivam tam kde zatim nevim jaka vyjimka je vhodna
public class OperationFailed extends Exception {
	private static final long serialVersionUID = 2509413637735360914L;
	@SuppressWarnings("unused")
  private Exception exception;

	public OperationFailed() {
		// TODO Automaticky generovan� stub konstruktoru
	}
	
	public OperationFailed(String message) {
		super(message);
	}
	
	 public OperationFailed(Exception exception) {
	    super("Operation failed.");
	    this.exception = exception;
	  }
}
