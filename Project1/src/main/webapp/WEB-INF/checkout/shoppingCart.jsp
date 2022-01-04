

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.ArrayList"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<style>
<style>
body {
	margin: 0px;
	/* background-color: #F3EBF6;*/ 
}
.active {
	margin-left: 70%;
}
.navbar-nav {
	width: 100%;
}
.topBar {
	position: relative;
	top: 0px;
	width: 99.8%;
	height: 15%;
	background-color: white;
	text-align: center;
	border-radius: 10px;
	border: 2px solid purple;
}

.home {
	color: purple;
	font-family: Georgia, 'Times New Roman', Times, serif;
	font-size: 40px;
	text-decoration: none;
}

.product {
	border: 2px solid purple;
	display: inline-block;
}
#margin{
	margin: 2% 8% 2% 4%;
}
#margin3{
		margin: 2% 0% 2% 2%;
}
#margin7{
		margin: 2% 0% 2% 2%;
}

img {
	width: 310px;
	height: 310px;
}

a {
	color: purple;
	font-family: 'Times New Roman', Times, serif;
	font-size: 17px;
}
p{
	text-align: center;
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a
		class="navbar-brand">Ecomm Website</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNav" aria-controls="navbarNav"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNav">
		<ul class="navbar-nav">
			<li class="nav-item "><a class="nav-link" href="./home"> Home</a></li>
			<li class="nav-item "><a class="nav-link" href="./products">Products</a>
			</li>
			<li class="nav-item active"><a class="nav-link" href="./cart">My Cart
			<span class="sr-only">(current)</span></a>
			</li>
			<li class="nav-item"><a class="nav-link" href="./logout">Logout</a>
			</li>
		</ul>
	</div>
	</nav><br><br>
	
	

	<c:forEach items="${cart.getProducts()}" var="item">
				
				<div class="product" id="margin3">
					<img src="${item.getUrl()}">
					<p>
						<a href="../product/showProduct?pid=${item.getPid()}">
							${item.getName()}--${item.getPrice()} AUD</a>
						<br>
						<a href="../product/deleteFromCart?pid=${item.getPid()}">Delete</a>
					</p>
					
				</div>

	</c:forEach>
	<div class="product" id="margin7">
		<p> total is :${cart.getTotal()} <br>
		<a href="../checkout/preview?amount=${cart.getTotal()}">Check Out</a>
		</p>
	</div>

</body>
</html>