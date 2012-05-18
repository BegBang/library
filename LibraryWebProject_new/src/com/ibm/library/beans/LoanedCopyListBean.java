package com.ibm.library.beans;

import java.io.Serializable;
import java.util.Collection;

import com.ibm.ils.library.model.LoanedCopy;

public class LoanedCopyListBean implements Serializable {
	private static final long serialVersionUID = 4822856317667555431L;
	
	private Collection<LoanedCopy> loanedCopyList;

	public LoanedCopyListBean() {}

	public Collection<LoanedCopy> getLoanedCopyList() {
		return loanedCopyList;
	}

	public void setLoanedCopyList(Collection<LoanedCopy> loanedCopyList) {
		this.loanedCopyList = loanedCopyList;
	}
	
	

}
