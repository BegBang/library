package com.ibm.ils.library.jpa.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the COPY database table.
 * 
 */
@Entity
public class Copy implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CopyPK id;

	private String loanable;

	//bi-directional many-to-one association to Item
    @ManyToOne
	@JoinColumn(name="ITEM_KEY")
	private Item item;

	//bi-directional many-to-one association to Onloan
	@OneToMany(mappedBy="copy")
	private Set<Onloan> onloans;

    public Copy() {
    }

	public CopyPK getId() {
		return this.id;
	}

	public void setId(CopyPK id) {
		this.id = id;
	}
	
	public String getLoanable() {
		return this.loanable;
	}

	public void setLoanable(String loanable) {
		this.loanable = loanable;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public Set<Onloan> getOnloans() {
		return this.onloans;
	}

	public void setOnloans(Set<Onloan> onloans) {
		this.onloans = onloans;
	}
	
}