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
import javax.servlet.http.HttpSession;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Item;
import com.ibm.ils.library.model.exceptions.ItemExists;
import com.ibm.ils.library.model.exceptions.OperationFailed;

/**
 * Servlet implementation class AddItem
 * Operates page AddItem.jsp
 * For get request nothing to do (only show static form)
 * For post request collects form data and saves it to database
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
		HttpSession session = request.getSession(false);
		
		String title = request.getParameter("title");
		if (title.equals("") || title == null) {
			showErrorMessage("Add Item failed: Title is requied!", request, response);
			return;
		}
		
		String author = request.getParameter("author");
		if (author.equals("") || author == null) {
			showErrorMessage("Add Item failed: Author is requied!", request, response);
			return;
		}
		
		String dateString = request.getParameter("date");
		
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {
			date = (Date)formatter.parse(dateString);
		} catch (ParseException e) {
			showErrorMessage("Add Item failed: Date must be in dd.mm.yyyy format!", request, response);
			return;
		}
		
		char type = request.getParameter("type").charAt(0);
		String oversize = request.getParameter("oversize");
		
		int number = 1;
		if (!request.getParameter("number").equals("")) {
			try {
				number = Integer.parseInt(request.getParameter("number"));
			} catch (NumberFormatException e) {
				showErrorMessage("Add Item failed: Number of volumes must be number!", request, response);
				return;
			}
		}
		
		
		String isbn = request.getParameter("isbn");
		if (isbn.equals("") || isbn == null) {
			showErrorMessage("Add Item failed: ISBN number is requied!", request, response);
			return;
		}
		
		Item item = new Item();
		item.setTitle(title);
		item.setAuthor(author);
		item.setPublished(date);
		item.setVolume(number);
		item.setMedium(type);
		if (oversize == null) {
			item.setOversize(false);
		} else {
			item.setOversize(true);
		}
		item.setIsbnEquivalent(isbn);
		
		try {
			item.add();
			
			session.setAttribute("info", "A new item has been added!");
			
			response.sendRedirect("Default");
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
