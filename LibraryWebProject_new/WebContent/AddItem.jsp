<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>AddItem</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<h1>Add a New Item</h1>

<% if (request.getAttribute("error") != null) {
	out.print("<p>" + request.getAttribute("error") + "</p>");
} %>

<form method="post" action="AddItem">
	<table>
		<tr>
			<td>Title:</td>
			<td><input type="text" id="title" name="title" /></td>
		</tr>
		<tr>
			<td>Author:</td>
			<td><input type="text" id="author" name="author" /></td>
		</tr>
		<tr>
			<td>Date of Publication:</td>
			<td><input type="text" id="date" name="date" /></td>
		</tr>
		<tr>
			<td>Type of Item:</td>
			<td><input type="text" id="type" name="type" /></td>
		</tr>
		<tr>
			<td>Oversize:</td>
			<td><input type="checkbox" id="oversize" name="oversize" /></td>
		</tr>
		<tr>
			<td>Number:</td>
			<td><input type="text" id="number" name="number" /></td>
		</tr>
		<tr>
			<td>ISBN Number:</td>
			<td><input type="text" id="isbn" name="isbn" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Add Item">
</form>

</body>
</html>