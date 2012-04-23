package com.ibm.ils.library.model.exceptions;

@SuppressWarnings("serial")
public class CopyNotFound extends Exception {

	public CopyNotFound(int itemId, int copyNumber) {
		super("Copy with id: " + itemId + " and number: " + copyNumber
				+ " not found.");
	}
}
