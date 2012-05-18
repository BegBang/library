<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Register</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<h1>Register a New Patron</h1>

<% if (request.getAttribute("error") != null) {
	out.print("<p>" + request.getAttribute("error") + "</p>");
} %>

<form method="post" action="Register">
	<table>
		<tr>
			<td>First name:</td>
			<td><input type="text" id="first_name" name="first_name" /></td>
		</tr>
		<tr>
			<td>Last name:</td>
			<td><input type="text" id="last_name" name="last_name" /></td>
		</tr>
		<tr>
			<td>Email address:</td>
			<td><input type="text" id="email" name="email" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" id="password" name="password" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Register">
</form>

</body>
</html>