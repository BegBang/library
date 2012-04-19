package com.ibm.ils.library.model.exceptions;

//obecna vyjimka, pouzivam tam kde zatim nevim jaka vyjimka je vhodna
public class DAOException extends Exception {
	private static final long serialVersionUID = 2509413637735360914L;

	public DAOException() {
		// TODO Automaticky generovaný stub konstruktoru
	}
	
	public DAOException(String message) {
		super(message);
	}
}
