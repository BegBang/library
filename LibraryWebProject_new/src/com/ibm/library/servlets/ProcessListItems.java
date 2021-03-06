package com.ibm.library.servlets;

import java.io.IOException;
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
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronNotFound;
import com.ibm.library.beans.LoanedCopyListBean;

/**
 * Servlet implementation class ProcessListItems
 * Servlet for display and renew checked out items
 * Get request show checked out items and user can choose items to renew
 * Post request works renewed items and display renew message to user
 * 
 */
@WebServlet("/ProcessListItems")
public class ProcessListItems extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessListItems() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		int patronId = getPatronId(request, response, session);
		
		if (patronId == -1) {
			return;
		}
		
		ServletContext context = getServletContext();
		Collection<LoanedCopy> loanedCopyList = null;
		try {
			loanedCopyList = Copy.findLoanedCopiesForPatronId(patronId);
			session.setAttribute("copyList", loanedCopyList);
			
			if (loanedCopyList != null && loanedCopyList.size() != 0) {
				LoanedCopyListBean bean = new LoanedCopyListBean();
				bean.setLoanedCopyList(loanedCopyList);
				session.setAttribute("listitems", bean);
				context.getRequestDispatcher("ListItems.jsp")
				.forward(request,response);
			} else {
				showErrorMessage("You have no items on loan!", request, response);
			}
		} catch (SystemUnavailableException e) {
			showErrorMessage("Error: System is unavailable!", request, response);
		} catch (OperationFailed e) {
			showErrorMessage("Error: Operation failed!", request, response);
		}
	}
	
	/**
	 * Verify if user have logged, otherwise, the error message appears
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected int getPatronId(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		try {
			return Integer.parseInt(session.getAttribute("patron").toString());
		} catch (NumberFormatException e) {
			showErrorMessage("Error: Patron ID is in bad format!", request, response);
		} catch (NullPointerException e) {
			showErrorMessage("Error: You are not logged in!", request, response);
		}
		
		return -1;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String[] checkBoxes = request.getParameterValues("renew");
		
		int patronId = getPatronId(request, response, session);
		
		ServletContext context = getServletContext();
		
		Patron patron = null;
		try {
			patron = Patron.findById(patronId);
		} catch (PatronNotFound e) {
			showErrorMessage(e.getMessage(), request, response);
			return;
		} catch (SystemUnavailableException e) {
			showErrorMessage(e.getMessage(), request, response);
			return;
		} catch (OperationFailed e) {
			showErrorMessage(e.getMessage(), request, response);
			return;
		}
		
		@SuppressWarnings("unchecked")
		Collection<LoanedCopy> loanedCopyList = (Collection<LoanedCopy>)session.getAttribute("copyList");
		
		if (checkBoxes != null) {
			for( String s : checkBoxes ) {
				Iterator<LoanedCopy> i = loanedCopyList.iterator();
				for (int a = 1; a < Integer.parseInt(s); a++) {
					i.next();
				}
				
				i.next().setRenewRequest(true);
			}
			
			try {
				patron.renew(loanedCopyList);
				
				session.setAttribute("copyList", loanedCopyList);
				
				if (loanedCopyList != null && loanedCopyList.size() != 0) {
					LoanedCopyListBean bean = new LoanedCopyListBean();
					bean.setLoanedCopyList(loanedCopyList);
					session.setAttribute("listitems", bean);
					context.getRequestDispatcher("ListItems.jsp").forward(request,response);
				}
			} catch (OperationFailed e) {
				showErrorMessage(e.getMessage(), request, response);
			} catch (SystemUnavailableException e) {
				showErrorMessage(e.getMessage(), request, response);
			}
		}
		
	}

	private void showErrorMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		request.setAttribute("error", message);
		context.getRequestDispatcher("ListItems.jsp").forward(request,response);
	}
}
