package com.ibm.ils.library.model;

import java.util.Date;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.exceptions.CopyNotFound;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.RenewFailed;

/**
 * Represents loaned copy. Useful for view layer.
 *
 */
public class LoanedCopy {

	private String author;

	private String title;

	private int copyNumber;

	private Date due;

	private int itemId;

	private boolean renewAccomplished;

	private String renewMessage;

	private boolean renewRequest;

	private int timesRenewed;

	public LoanedCopy() {
		this("", "", 0, null, 0, false, "", false, 0);
	}

	public LoanedCopy(String author, String title, int copyNumber, Date due,
			int itemId, int timesRenewed) {
		this(author, title, copyNumber, due, itemId, false, "", false,
				timesRenewed);
	}

	public LoanedCopy(String author, String title, int copyNumber, Date due,
			int itemId, boolean renewAccomplished, String renewMessage,
			boolean renewRequest, int timesRenewed) {
		super();
		this.author = author;
		this.title = title;
		this.copyNumber = copyNumber;
		this.due = due;
		this.itemId = itemId;
		this.renewAccomplished = renewAccomplished;
		this.renewMessage = renewMessage;
		this.renewRequest = renewRequest;
		this.timesRenewed = timesRenewed;
	}

	public void renew(int patron_id) throws OperationFailed, SystemUnavailableException {
		Copy copy = new Copy(false, getDue(), getItemId(), patron_id,
				getTimesRenewed(), getCopyNumber());
		
		try {
			boolean renewable = copy.renew();
			if (renewable) {
				// it was renewed, update the LoanedCopy object
				setRenewAccomplished(true);
				setRenewMessage("Renew successful");
				setDue(copy.getDue());
				setTimesRenewed(copy.getTimesRenewed());
			}
			
		} catch (CopyNotFound e) {
			setRenewAccomplished(false);
			setRenewMessage("Renew failed");
		}  catch (RenewFailed e) {
			setRenewAccomplished(false);
			setRenewMessage("Renew failed: " + e.getMessage());
		}
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
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

	public boolean isRenewAccomplished() {
		return renewAccomplished;
	}

	public void setRenewAccomplished(boolean renewAccomplished) {
		this.renewAccomplished = renewAccomplished;
	}

	public String getRenewMessage() {
		return renewMessage;
	}

	public void setRenewMessage(String renewMessage) {
		this.renewMessage = renewMessage;
	}

	public boolean isRenewRequest() {
		return renewRequest;
	}

	public void setRenewRequest(boolean renewRequest) {
		this.renewRequest = renewRequest;
	}

	public int getTimesRenewed() {
		return timesRenewed;
	}

	public void setTimesRenewed(int timesRenewed) {
		this.timesRenewed = timesRenewed;
	}

	@Override
	public String toString() {
		return String
				.format("LoanedCopy[author=%s, title=%s, copyNumber=%d, due=%s, itemId=%d, renewAccomplished=%s, renewMessage=%s, renewRequest=%s, timesRenewed=%d]",
						author, title, copyNumber, due, itemId,
						renewAccomplished, renewMessage, renewRequest,
						timesRenewed);
	}

}
