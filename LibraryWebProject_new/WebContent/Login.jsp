<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h1>IBM Library System</h1>

<h3>Please Log In:</h3>

<% if (request.getAttribute("error") != null) {
	out.print("<p>" + request.getAttribute("error") + "</p>");
} %>

<form method="post" action="Login">
	<table>
		<tr>
			<td>Patron ID:</td>
			<td><input type="text" id="patron_id" name="patron_id" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" id="password" name="password" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Login">
</form>

</body>
</html>