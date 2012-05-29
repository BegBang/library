package com.ibm.library;

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
	private CopyPK id;
	private String loanable;
	private Item item;
	private Set<Onloan> onloans;

    public Copy() {
    }


	@EmbeddedId
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


	//bi-directional many-to-one association to Item
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="ITEM_KEY")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	

	//bi-directional many-to-one association to Onloan
	@OneToMany(mappedBy="copy")
	public Set<Onloan> getOnloans() {
		return this.onloans;
	}

	public void setOnloans(Set<Onloan> onloans) {
		this.onloans = onloans;
	}
	
}