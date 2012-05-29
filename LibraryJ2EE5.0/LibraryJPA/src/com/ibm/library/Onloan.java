package com.ibm.library;

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
	private OnloanPK id;
	private Date dueDate;
	private int timesRenewed;
	private Copy copy;
	private Patron patron;

    public Onloan() {
    }


	@EmbeddedId
	public OnloanPK getId() {
		return this.id;
	}

	public void setId(OnloanPK id) {
		this.id = id;
	}
	

    @Temporal( TemporalType.DATE)
	@Column(name="DUE_DATE")
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	@Column(name="TIMES_RENEWED")
	public int getTimesRenewed() {
		return this.timesRenewed;
	}

	public void setTimesRenewed(int timesRenewed) {
		this.timesRenewed = timesRenewed;
	}


	//bi-directional many-to-one association to Copy
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="COPY_NUMBER", referencedColumnName="COPY_NUMBER"),
		@JoinColumn(name="ITEM_KEY", referencedColumnName="ITEM_KEY")
		})
	public Copy getCopy() {
		return this.copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	

	//bi-directional many-to-one association to Patron
    @ManyToOne
	@JoinColumn(name="PATRON_ID")
	public Patron getPatron() {
		return this.patron;
	}

	public void setPatron(Patron patron) {
		this.patron = patron;
	}
	
}