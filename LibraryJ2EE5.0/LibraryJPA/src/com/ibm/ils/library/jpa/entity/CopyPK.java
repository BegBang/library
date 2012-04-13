package com.ibm.ils.library.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the COPY database table.
 * 
 */
@Embeddable
public class CopyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ITEM_KEY")
	private int itemKey;

	@Column(name="COPY_NUMBER")
	private int copyNumber;

    public CopyPK() {
    }
	public int getItemKey() {
		return this.itemKey;
	}
	public void setItemKey(int itemKey) {
		this.itemKey = itemKey;
	}
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
		if (!(other instanceof CopyPK)) {
			return false;
		}
		CopyPK castOther = (CopyPK)other;
		return 
			(this.itemKey == castOther.itemKey)
			&& (this.copyNumber == castOther.copyNumber);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.itemKey;
		hash = hash * prime + this.copyNumber;
		
		return hash;
    }
}