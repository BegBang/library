<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Default</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h1>IBM Library</h1>

<% if (session.getAttribute("info") != null) {
	out.print("<p>" + session.getAttribute("info") + "</p>");
} %>

	<a href="Register">Register a new Patron</a>
	<br />
	<a href="Login">Login</a>
</body>
</html>