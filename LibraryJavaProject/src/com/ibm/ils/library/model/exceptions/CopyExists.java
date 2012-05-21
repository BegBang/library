package com.ibm.ils.library.model.exceptions;



public class CopyExists extends Exception {
	private static final long serialVersionUID = -6120961877880202467L;

	public CopyExists(int itemId, int copyNumber) {
		super("Copy with id: " + itemId + " and number: " + copyNumber
				+ " already exists.");
	}
}