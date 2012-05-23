package com.ibm.library.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.ils.library.datastore.exceptions.SystemUnavailableException;
import com.ibm.ils.library.model.Patron;
import com.ibm.ils.library.model.exceptions.InvalidPassword;
import com.ibm.ils.library.model.exceptions.OperationFailed;
import com.ibm.ils.library.model.exceptions.PatronExists;

/**
 * Servlet implementation class Register
 * Get request only show register form
 * Post request take values from form and save it into database (if they are correct)
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
		HttpSession session = request.getSession(false);
		
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email != "" && (email.indexOf("@") == -1 || email.indexOf(".") == -1)) {
			showErrorMessage("Registration failure: Email is not in correct format!", request, response);
			return;
		}
		
		
		if (checkValues(firstName, lastName, email, password)) {
			Patron p = new Patron();
			p.setFirstName(firstName);
			p.setLastName(lastName);
			p.setEmail(email);
			p.setPassword(password);
			try {
				p.add();
				
				session.setAttribute("info", "A new patron has been registered!");
				
				response.sendRedirect("Default");
			} catch (PatronExists e) {
				showErrorMessage("Registration failure: Patron already exists!", request, response);
			} catch (SystemUnavailableException e) {
				showErrorMessage("Registration failure: System is unavailable!", request, response);
			} catch (OperationFailed e) {
				showErrorMessage("Registration failure: Operation failed!", request, response);
			} catch (InvalidPassword e) {
				showErrorMessage(e.getMessage(), request, response);
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
