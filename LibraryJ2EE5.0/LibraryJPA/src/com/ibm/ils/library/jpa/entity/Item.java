package com.ibm.ils.library.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ITEM database table.
 * 
 */
@Entity
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ITEM_KEY")
	private int itemKey;

	private String author;

	@Column(name="ISBN_EQUIV")
	private String isbnEquiv;

	private String oversize;

    @Temporal( TemporalType.DATE)
	@Column(name="PUBLISH_DATE")
	private Date publishDate;

	private String title;

	private String type;

	private int volumes;

	//bi-directional many-to-one association to Copy
	@OneToMany(mappedBy="item")
	private Set<Copy> copies;

    public Item() {
    }

	public int getItemKey() {
		return this.itemKey;
	}

	public void setItemKey(int itemKey) {
		this.itemKey = itemKey;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbnEquiv() {
		return this.isbnEquiv;
	}

	public void setIsbnEquiv(String isbnEquiv) {
		this.isbnEquiv = isbnEquiv;
	}

	public String getOversize() {
		return this.oversize;
	}

	public void setOversize(String oversize) {
		this.oversize = oversize;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVolumes() {
		return this.volumes;
	}

	public void setVolumes(int volumes) {
		this.volumes = volumes;
	}

	public Set<Copy> getCopies() {
		return this.copies;
	}

	public void setCopies(Set<Copy> copies) {
		this.copies = copies;
	}
	
}