package com.ibm.ils.library.datastore;

import com.ibm.ils.library.model.Patron;

public interface PatronDataStore {
	
	public void add(Patron patron);
	public Patron findById(int id);

}
