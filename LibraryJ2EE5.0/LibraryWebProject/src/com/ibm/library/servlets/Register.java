package com.ibm.library.servlets;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.openjpa.lib.jdbc.ReportingSQLException;

import com.ibm.library.Patron;
import com.ibm.library.ejb.PatronEJB;
import com.ibm.library.ejb.PatronEJBLocal;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		
		context.getRequestDispatcher("Register.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email != "" && (email.indexOf("@") == -1 || email.indexOf(".") == -1)) {
			showErrorMessage("Registration failure: Email is not in correct format!", request, response);
		}
		
		
		if (checkValues(firstName, lastName, email, password)) {
			Patron p = new Patron();
			p.setFirstName(firstName);
			p.setLastName(lastName);
			p.setEmail(email);
			p.setPassword(password);
			try {
				PatronEJBLocal patronEJB = new PatronEJB();
				patronEJB.add(p);
				
				System.out.println("Registration success");
				session.setAttribute("info", "A new patron has been registered!");
				
				response.sendRedirect("Default");
			} catch (EntityExistsException e) {
				showErrorMessage("Registration failure: Patron already exists!", request, response);
			} catch (RollbackException e) {
				showErrorMessage("Registration failure: Patron register failed!", request, response);
			} catch (IllegalStateException e) {
				showErrorMessage("Registration failure: Patron register failed!", request, response);
			}
			
		} else {
			showErrorMessage("Registration failure: All fields must be filled!", request, response);
		}
	}
	
	private boolean checkValues(String firstName, String lastName, String email, String password) {
		if (firstName == null || firstName.equals("")) {
			return false;
		}
		else if (lastName == null || lastName.equals("")) {
			return false;
		}
		else if (email == null || email.equals("")) {
			return false;
		}
		else if (password == null || password.equals("")) {
			return false;
		}
		
		return true;
	}

	private void showErrorMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		request.setAttribute("error", message);
		context.getRequestDispatcher("Register.jsp").forward(request,response);
	}
}
