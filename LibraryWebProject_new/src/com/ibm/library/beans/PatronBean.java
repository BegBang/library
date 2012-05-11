package com.ibm.library.beans;

import java.io.Serializable;
import com.ibm.ils.library.model.Patron;

public class PatronBean implements Serializable {
	private static final long serialVersionUID = -921318297660625762L;

	private Patron patron;
	
	public PatronBean() {}

	public Patron getPatron() {
		return patron;
	}

	public void setPatron(Patron p) {
		this.patron = p;
	}
}
