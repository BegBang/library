package com.ibm.library.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Copy;
import com.ibm.ils.library.model.LoanedCopy;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.library.beans.LoanedCopyListBean;

/**
 * Servlet implementation class ProcessListItems
 */
@WebServlet("/ProcessListItems")
public class ProcessListItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LoanedCopy = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessListItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		int patronId = -1;
		try {
			patronId = Integer.parseInt(session.getAttribute("patron").toString());
		} catch (NumberFormatException e) {
			showErrorMessage("Error: Patron ID is in bad format!", request, response);
		} catch (NullPointerException e) {
			showErrorMessage("Error: You are not logged in!", request, response);
		}
		
		
		ServletContext context = getServletContext();
		Copy copyX = new Copy();
		Collection<LoanedCopy> loanedCopyList = null;
		try {
			loanedCopyList = copyX.findLoanedCopiesForPatronId(patronId);
			request.setAttribute("copyList", loanedCopyList);
		} catch (SystemUnavailableException e) {
			showErrorMessage("Error: System is unavailable!", request, response);
		} catch (OperationFailed e) {
			showErrorMessage("Error: Operation failed!", request, response);
		}
		if (loanedCopyList != null && loanedCopyList.size() != 0) {
			LoanedCopyListBean bean = new LoanedCopyListBean();
			bean.setLoanedCopyList(loanedCopyList);
			session.setAttribute("listitems", bean);
			context.getRequestDispatcher("ListItems.jsp")
			.forward(request,response);
		} else {
			showErrorMessage("You have no items on loan!", request, response);
		}
			
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] checkBoxes = request.getParameterValues("renew");
		
		Collection<LoanedCopy> loanedCopyList = (Collection<LoanedCopy>)request.getAttribute("copyList");
		
		if (checkBoxes != null) {
			for( String s : checkBoxes ) {
				Iterator<LoanedCopy> i = loanedCopyList.iterator();
				for (int a = 1; a < Integer.parseInt(s); a++) {
					i.next();
				}
				LoanedCopy copy = (LoanedCopy)i.next();
				copy.setRenewRequest(true);
				System.out.println( "<br />checkBox: "+s );
			}
		}
		
	}

	private void showErrorMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		request.setAttribute("error", message);
		context.getRequestDispatcher("ListItems.jsp").forward(request,response);
	}
}
