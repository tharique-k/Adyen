<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Product</title>
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
.error-box{
	width: 100%;
	color: red;
	font-weight: 700;
	font-size: 11px;
	letter-spacing: 1px;
	background: rgba(0, 0, 0, 0);
	margin-top: 30px;
	margin-left: 10px;
	text-align: center;
	font-family: 'Ubuntu', sans-serif;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a
		class="navbar-brand">Add Product Details</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="./homeAdmin.jsp">Admin-Home
					<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="./addProduct">Add-Product</a>
			</li>
			<li class="nav-item "><a class="nav-link" href="./products">Products</a>
			</li>
			<li class="nav-item active"><a class="nav-link" href="./logout">Logout</a>
			</li>
		</ul>
	</div>
	</nav>
	<br><br>
	<form action="./addProduct" method="post">
		Name: <input type="text" name="name"><br> <br>
		Price: <input type="text" name="price"><br> <br>
		Description: <input type="text" style="height: 50px"name="description"><br><br>
		Image Url: <input type="text" name="url"> <br>
		<br><input type="submit" value="Add">
		<p class="error-box"><%=request.getAttribute("error")%></p>
	</form>
</body>
</html>