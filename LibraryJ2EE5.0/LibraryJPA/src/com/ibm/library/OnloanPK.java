package com.ibm.library;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ONLOAN database table.
 * 
 */
@Embeddable
public class OnloanPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private int patronId;
	private int itemKey;
	private int copyNumber;

    public OnloanPK() {
    }

	@Column(name="PATRON_ID")
	public int getPatronId() {
		return this.patronId;
	}
	public void setPatronId(int patronId) {
		this.patronId = patronId;
	}

	@Column(name="ITEM_KEY")
	public int getItemKey() {
		return this.itemKey;
	}
	public void setItemKey(int itemKey) {
		this.itemKey = itemKey;
	}

	@Column(name="COPY_NUMBER")
	public int getCopyNumber() {
		return this.copyNumber;
	}
	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OnloanPK)) {
			return false;
		}
		OnloanPK castOther = (OnloanPK)other;
		return 
			(this.patronId == castOther.patronId)
			&& (this.itemKey == castOther.itemKey)
			&& (this.copyNumber == castOther.copyNumber);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.patronId;
		hash = hash * prime + this.itemKey;
		hash = hash * prime + this.copyNumber;
		
		return hash;
    }
}