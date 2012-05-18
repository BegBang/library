package com.ibm.library.servlets;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronNotFound;

/**
 * Servlet implementation class ProcessListItems
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = -1776077572612582788L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ServletContext context = getServletContext();
		
		context.getRequestDispatcher("Login.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		try {
			String patronEmail = request.getParameter("patron_email");
			String patronPassword = request.getParameter("password");
			
			Patron p = Patron.findByEmail(patronEmail);
			
			if (p.getPassword().equals(patronPassword)) {
				session.setAttribute("patron", p.getId());
				session.setAttribute("info", "You have been logged!");
				
				response.sendRedirect("Default");
			} else {
				ServletContext context = getServletContext();
				request.setAttribute("error", "Logon failed: Bad combination of user/password!");
				context.getRequestDispatcher("Login.jsp").forward(request,response);
			}
		} catch (NumberFormatException e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed: Patron ID is not in correct format!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		} catch (PatronNotFound e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed: Patron was not found!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		} catch (SystemUnavailableException e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed: System is unavailable!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		} catch (OperationFailed e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed: Operation failed!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		}
	}
}
