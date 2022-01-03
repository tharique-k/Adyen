<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body>
	<c:forEach items="${cart.getProducts()}" var="item">
		<a href="../product/showProduct?pid=${item.getPid()}"><c:out value="${item.getName()}"></c:out></a>
		<a href="../product/deleteFromCart?pid=${item.getPid()}">Delete</a>
	<br>
	</c:forEach>
	<br>
	<p> total is :${cart.getTotal()}</p>
	<a href="../checkout/preview?amount=${cart.getTotal()}">Check Out</a>
</body>
</html>