package com.ibm.ils.library.datastore;

import java.util.Collection;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.ItemNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public interface ItemDataStore {

	/**
	 * Add new item to the database.
	 * @param item the new item
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws ItemExists if an item with this id already exists
	 */
	public void add(Item item) throws SystemUnavailableException,
			OperationFailed, ItemExists;

	/**
	 * Find an item with specified id.
	 * @param id the id
	 * @return item
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws ItemNotFound if item with the id not found
	 */
	public Item findById(int id) throws SystemUnavailableException,
			OperationFailed, ItemNotFound;

	/**
	 * Gets copies for the item.
	 * @param item
	 * @return collection of copies
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 */
	public Collection<Copy> getCopies(Item item) throws OperationFailed,
			SystemUnavailableException;

	/**
	 * Remove item from the database.
	 * @param item
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 * @throws ItemNotFound if item with the id not found
	 */
	public void remove(Item item) throws SystemUnavailableException,
			OperationFailed, ItemNotFound;

	/**
	 * Update item in the database.
	 * @param item if item with the id not found
	 * @throws SystemUnavailableException
	 *             if database connection can not be established
	 * @throws ItemNotFound
	 * @throws OperationFailed
	 *             if an unspecified error during execution SQL statement occurs
	 */
	public void update(Item item) throws SystemUnavailableException,
			ItemNotFound, OperationFailed;

}
