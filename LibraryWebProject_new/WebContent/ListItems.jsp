<?xml version="1.0" encoding="utf-8"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>IBM Library</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<h1>IBM Library</h1>
	<h2>My Checked Out Items</h2>
	
	<% if (request.getAttribute("error") != null) {
		out.print("<p>" + request.getAttribute("error") + "</p>");
	} %>
<form method="post">
	<table width="100%" border="1" cellpadding="0" cellspacing="1">
		<tbody>
			<tr>
				<th>Renew</th>
				<th>Author</th>
				<th>Title</th>
				<th>Copy</th>
				<th>Due date</th>
			</tr>
			<c:forEach var="item" items="${listitems.loanedCopyList}" varStatus="rowCounter">
				<tr>
					<td><input type="checkbox" value="${rowCounter.count}" name="renew" /></td>
					<td>${item.author}</td>
					<td>${item.title}</td>
					<td>${item.copyNumber}</td>
					<td>${item.due}</td>
					<td>${item.renewMessage}</td>
				</tr>
			</c:forEach>
			<tr><td><input type="submit" value="Renew" /></td></tr>
		</tbody>
	</table>
	<a href="Default">Back</a>
</form>
</body>
</html>