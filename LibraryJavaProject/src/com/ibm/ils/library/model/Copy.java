package com.ibm.ils.library.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.ibm.ils.library.datastore.CopyDataStore;
import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.exceptions.OperationFailed;

public class Copy implements Serializable {
	private static final long serialVersionUID = -1829277270477731043L;

	private boolean loanable;

	private Date due;

	private int itemId;

	private int patronId;

	private int timesRenewed;

	private int copyNumber;

	private static CopyDataStore datastore;

	static {
		datastore = DataStoreFactory.getCopyDataStore();
	}
	
	public Copy() {
    // TODO Auto-generated constructor stub
  }

	
	public Copy(boolean loanable, Date due, int itemId, int patronId,
			int timesRenewed, int copyNumber) {
		super();
		this.loanable = loanable;
		this.due = due;
		this.itemId = itemId;
		this.patronId = patronId;
		this.timesRenewed = timesRenewed;
		this.copyNumber = copyNumber;
	}
	
	public Collection<Copy> findCopiesForPatronId(int id)
      throws SystemUnavailableException, OperationFailed {
	  return datastore.findCopiesForPatronId(id);
	}
	
	public Collection<LoanedCopy> findLoanedCopiesForPatronId(int id)
      throws SystemUnavailableException, OperationFailed {
	  return datastore.findLoanedCopiesForPatronId(id);
	}
	
	public void renew() {
		
	}

	//
	// Getters and setters
	//
	public boolean isLoanable() {
		return loanable;
	}

	public void setLoanable(boolean loanable) {
		this.loanable = loanable;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getPatronId() {
		return patronId;
	}

	public void setPatronId(int patronId) {
		this.patronId = patronId;
	}

	public int getTimesRenewed() {
		return timesRenewed;
	}

	public void setTimesRenewed(int timesRenewed) {
		this.timesRenewed = timesRenewed;
	}

	public int getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Copy[loanable=%b,due=%s,itemId=%d,patronId=%d,timesRenewed=%d,copyNumber=%d]",
				loanable, due, itemId, patronId, timesRenewed, copyNumber);
	}
	
}
