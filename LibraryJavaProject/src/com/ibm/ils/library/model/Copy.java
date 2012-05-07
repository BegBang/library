package com.ibm.ils.library.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.ibm.ils.library.datastore.CopyDataStore;
import com.ibm.ils.library.datastore.DataStoreFactory;
import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.exceptions.CopyNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.RenewFailed;

public class Copy implements Serializable {
	private static final long serialVersionUID = -1829277270477731043L;
	private static final int MAX_TIMES_RENEW = 3;
	private static final long DAYS_TO_RENEW = 28;
	private static final long ONE_DAY_MS = 1000 * 60 * 60 * 24;

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

	public void renew() throws CopyNotFound, OperationFailed,
			SystemUnavailableException, RenewFailed {
		Date now = new Date();
		Date due = getDue();

		Calendar todayCalendar = Calendar.getInstance();
		Calendar dueCalendar = Calendar.getInstance();
		dueCalendar.setTime(due);
		int todayYear = todayCalendar.get(Calendar.YEAR);
		int todayDayOfYear = todayCalendar.get(Calendar.DAY_OF_YEAR);
		int dueYear = dueCalendar.get(Calendar.YEAR);
		int dueDayOfYear = dueCalendar.get(Calendar.DAY_OF_YEAR);

		if (todayYear < dueYear
				|| (todayYear == dueYear && todayDayOfYear <= dueDayOfYear)) {
			// check to make sure the book has not been
			// renewed too many times
			if (getTimesRenewed() < MAX_TIMES_RENEW) {
				// renew the book for the standard time

				java.sql.Date newDueDate = new java.sql.Date(now.getTime()
						+ DAYS_TO_RENEW * ONE_DAY_MS);
				datastore.renewCopy(this, newDueDate);
				// update copy due date, times renewed
				setDue(new Date(newDueDate.getTime()));
				setTimesRenewed(getTimesRenewed() + 1);
			} else {
				throw new RenewFailed("Maximum number of renewals exceeded");
			}
		} else {
			throw new RenewFailed("Renewal is not allowed after the due date");
		}
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
		return String
				.format("Copy[loanable=%b,due=%s,itemId=%d,patronId=%d,timesRenewed=%d,copyNumber=%d]",
						loanable, due, itemId, patronId, timesRenewed,
						copyNumber);
	}

}
