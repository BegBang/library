package com.ibm.library.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.OperationFailed;

/**
 * Servlet implementation class AddItem
 */
@WebServlet("/AddItem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItem() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		
		context.getRequestDispatcher("AddItem.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String dateString = request.getParameter("date");
		
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {
			date = (Date)formatter.parse(dateString);
		} catch (ParseException e) {
			showErrorMessage("Add Item failed: Date must be in dd.mm.yyyy format!", request, response);
		}
		
		String type = request.getParameter("type");
		String oversize = request.getParameter("oversize");
		String number = request.getParameter("number");
		String isbn = request.getParameter("isbn");
		
		Item item = new Item();
		item.setTitle(title);
		item.setAuthor(author);
		item.setPublished(date);
		if (oversize == null) {
			item.setOversize(false);
		} else {
			item.setOversize(true);
		}
		item.setIsbnEquivalent(isbn);
		
		try {
			item.add(item);
		} catch (SystemUnavailableException e) {
			showErrorMessage("AddItem failure: System is unavailable!", request, response);
		} catch (OperationFailed e) {
			showErrorMessage("AddItem failure: Operation failed!", request, response);
		} catch (ItemExists e) {
			showErrorMessage("AddItem failure: Item allready axists!", request, response);
		}
	}

	private void showErrorMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		request.setAttribute("error", message);
		context.getRequestDispatcher("AddItem.jsp").forward(request,response);
	}
}
