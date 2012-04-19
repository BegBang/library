package com.ibm.ils.library.datastore;

import java.util.Collection;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.ItemNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public interface ItemDataStore {
	
	public void add(Item item) throws SystemUnavailableException, OperationFailed, ItemExists;
	
	public Item findById(int id) throws SystemUnavailableException, OperationFailed, ItemNotFound;
	
	public Collection<Copy> getCopies(Item item);
	
	public void remove(Item item) throws SystemUnavailableException, OperationFailed, ItemNotFound;
	
	public void update(Item item) throws SystemUnavailableException, ItemNotFound, OperationFailed;

}
