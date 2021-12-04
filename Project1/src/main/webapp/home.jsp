<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="java.util.Date, javax.swing.Timer, javax.swing.JOptionPane"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<style>
body {
	margin: 0px;
}

.active {
	margin-left: 70%;
}

.navbar-nav {
	width: 100%;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Admin</title>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a
		class="navbar-brand">Legit Website</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="./home.jsp"> Home
					<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item "><a class="nav-link" href="./products">Products</a>
			</li>
			<li class="nav-item active"><a class="nav-link" href="./logOut">Logout</a>
			</li>
		</ul>
	</div>
	</nav><br><br>
	<p>
		Welcome, 
		<c:out value="${name}"></c:out>
	</p>
	<p>
		Session max Inactive date:
		<c:out value="${pageContext.session.maxInactiveInterval}"></c:out>
	</p>
</body>
</html>