package com.ibm.ils.library.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.ItemDataStore;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.ItemNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public class Item implements Serializable {
	private static final long serialVersionUID = 1829130523855209126L;

	private int id;

	private char medium;

	private String isbnEquivalent;

	private String title;

	private String author;

	private boolean oversize;

	private Integer volumes;//TODO predelat na Integer

	private Date published;

	private static ItemDataStore dataStore;

	static {
		dataStore = DataStoreFactory.getItemDataStore();
	}

	public Item() {
		this('?', "", "", "", false, 1, null);
	}

	public Item(char medium, String isbnEquivalent, String title,
			String author, boolean oversize, int volumes, Date published) {
		this(0, medium, isbnEquivalent, title, author, oversize, volumes,
				published);
	}

	public Item(int id, char medium, String isbnEquivalent, String title,
			String author, boolean oversize, Integer volumes, Date published) {
		super();
		this.id = id;
		this.medium = medium;
		this.isbnEquivalent = isbnEquivalent;
		this.title = title;
		this.author = author;
		this.oversize = oversize;
		this.volumes = volumes;
		this.published = published;
	}

	// TODO volani metod datastore
	public void add(Item item) throws SystemUnavailableException,
			OperationFailed, ItemExists {
		dataStore.add(item);
	}

	public static Item findById(int id) throws SystemUnavailableException,
			OperationFailed, ItemNotFound {
		return dataStore.findById(id);
	}

	public Collection<Copy> getCopies(Item item) {
		return null;
	}

	public void remove(Item item) throws SystemUnavailableException,
			OperationFailed, ItemNotFound {
		dataStore.remove(item);
	}

	public void update(Item item) throws SystemUnavailableException,
			ItemNotFound, OperationFailed {
		dataStore.update(item);
	}
	
	//
	// Getters and setters
	//

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getMedium() {
		return medium;
	}

	public void setMedium(char medium) {
		this.medium = medium;
	}

	public String getIsbnEquivalent() {
		return isbnEquivalent;
	}

	public void setIsbnEquivalent(String isbnEquivalent) {
		this.isbnEquivalent = isbnEquivalent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isOversize() {
		return oversize;
	}

	public void setOversize(boolean oversize) {
		this.oversize = oversize;
	}

	public Integer getVolume() {
		return volumes;
	}

	public void setVolume(Integer volume) {
		this.volumes = volume;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

}
