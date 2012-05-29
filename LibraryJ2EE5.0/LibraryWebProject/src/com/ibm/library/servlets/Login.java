package com.ibm.library.servlets;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.library.Patron;
import com.ibm.library.ejb.PatronEJB;
import com.ibm.library.ejb.PatronEJBLocal;


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
		HttpSession session = request.getSession(false);
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
			
			PatronEJBLocal patronEJB = new PatronEJB();
			Patron p = patronEJB.findByEmail(patronEmail);
			
			if (p.getPassword().equals(patronPassword)) {
				session.setAttribute("patron", p.getPatronId());
				session.setAttribute("info", "You have been logged!");
				
				response.sendRedirect("Default");
			} else {
				ServletContext context = getServletContext();
				request.setAttribute("error", "Logon failed: Bad combination of user/password!");
				context.getRequestDispatcher("Login.jsp").forward(request,response);
			}
		} catch (NoResultException e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed: Patron was not found!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		}  catch (IllegalArgumentException e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		}  catch (IllegalStateException e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		}  catch (NonUniqueResultException e) {
			ServletContext context = getServletContext();
			request.setAttribute("error", "Logon failed: Patron is not unique!");
			context.getRequestDispatcher("Login.jsp").forward(request,response);
		}
	}
}
