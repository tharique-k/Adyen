<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<title>Product Details</title>
<style>
body {
	/*background-color: #F3EBF6;*/
	margin: 10px 10px 0px 10px;
}
.active {
	margin-left: 70%;
}
.navbar-nav {
	width: 100%;
}
.header {
	width: 100%;
	height: 15%;
	background-color: white;
	border: none;
	border-radius: 20px;
	border: 2px solid purple;
}

.center {
	position: relative;
	vertical-align:top;
	text-align: center;
	font-family: Ubuntu;
	font-size: 72;
}

.image {
    vertical-align: top;
	display: inline-block;
	margin-left: 1%;
	margin: 1% 0%;
	width: 38%;
	height: 90%;
	border-right: purple solid 2px;
}
.imgg{
	border: none;
	border-radius: 20px;
	border: 5px solid rgba(0, 0, 0, 0.02);
}

.main {
	margin-top: 2%;
	width: 100%;
	height: 70%;
	border: none;
	border-radius: 20px;
	border: 2px solid white;
}

.prop {
	display: inline-block;
	position: relative;
	margin-top: 4%;
	left: 10%;
	width: 50%;
	height: 90%;
}
.quantity {
	background-color: white;
	position: relative;
	width: 30%;
	height: 10%;
	left: 35%;
	top: 5%;
	border: none;
	border-radius: 20px;
	border: 2px solid purple;
}
.price {
	background-color: white;
	position: relative;
	width: 40%;
	height: 20%;
	left: 30%;
	margin-top: 10%;
	border: none;
	border-radius: 20px;
	border: 2px solid purple;
}

.desc {
	background-color: white;
	position: relative;
	width: 80%;
	height: 30%;
	left: 10%;
	margin-top: 10%;
	border: none;
	border-radius: 20px;
	border: 2px solid purple;
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
			<li class="nav-item active"><a class="nav-link" href="../home/home"> Home
					<span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item "><a class="nav-link" href="../home/products">Products</a>
			</li>
			<li class="nav-item "><a class="nav-link" href="../home/cart">My Cart</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="../home/logout">Logout</a>
			</li>
		</ul>
	</div>
	</nav><br><br>
	
	<div class="header">
		<h1 class="center"><c:out value="${product.get('name')}"></c:out></h1>
	</div>
	<div class="main">
		<div class="image"><img class="imgg" style="width:500px; height: 500px;" alt="image" src="${product.get('url')}"></div>
		<div class="prop">
			<div class="price">
				<h2 class="center"><c:out value="${product.get('price')}"></c:out> AUD
				</h2>
			</div>
			<div class="desc">
				<h2 class="center"><c:out value="${product.get('description')}"></c:out></h2>
			</div>
			<!-- form action="ControllerAddToCart?pid=${product.get('pid')}" method="get">
				<input type="submit" class="submit" value="Add to Cart"
					style="background-color: purple; color: white; border-radius: 
					20px; border: 2px solid white; width: 150px; height: 50px; 
					position: relative; left: 75%; margin-top: 10%;">
			</form -->
			<button onclick="location.href='./addToCart?pid=${product.get('pid')}'" type="button"
			style="background-color: purple; color: white; border-radius: 
					20px; border: 2px solid white; width: 150px; height: 50px; 
					position: relative; left: 75%; margin-top: 10%;">
         	Add to Cart</button>
		</div>
	</div>
</body>
</html>