package com.ibm.ils.library.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


import java.util.Date;


/**
 * The persistent class for the ONLOAN database table.
 * 
 */
@Entity
public class Onloan implements Serializable {
	private static final long serialVersionUID = 1L;

    @Temporal( TemporalType.DATE)
	@Column(name="DUE_DATE")
	private Date dueDate;

	@Column(name="TIMES_RENEWED")
	private int timesRenewed;

	//bi-directional many-to-one association to Patron
	@Id
    @ManyToOne
	@JoinColumn(name="PATRON_ID")
	private Patron patron;

	//bi-directional many-to-one association to Copy
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="COPY_NUMBER", referencedColumnName="COPY_NUMBER"),
		@JoinColumn(name="ITEM_KEY", referencedColumnName="ITEM_KEY")
		})
	private Copy copy;

    public Onloan() {
    }

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getTimesRenewed() {
		return this.timesRenewed;
	}

	public void setTimesRenewed(int timesRenewed) {
		this.timesRenewed = timesRenewed;
	}

	public Patron getPatron() {
		return this.patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}
	
	public Copy getCopy() {
		return this.copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	
}