package com.ibm.library.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
		HttpSession session = request.getSession(true);// TODO pozdeji na false
		ServletContext context = getServletContext();
		Copy copyX = new Copy();
		Collection<LoanedCopy> loanedCopyList = null;
		try {
			loanedCopyList = copyX.findLoanedCopiesForPatronId(2);
		} catch (SystemUnavailableException e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		} catch (OperationFailed e) {
			// TODO Automaticky generovaný blok catch
			e.printStackTrace();
		}
		if (loanedCopyList != null && loanedCopyList.size() != 0) {
			LoanedCopyListBean bean = new LoanedCopyListBean();
			bean.setLoanedCopyList(loanedCopyList);
			session.setAttribute("listitems", bean);
			context.getRequestDispatcher("/WEB-INF/jsp/ListItems.jsp")
			.forward(request,response);
		} else {
			//TODO no items
			System.out.println("No items");
		}
			
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
