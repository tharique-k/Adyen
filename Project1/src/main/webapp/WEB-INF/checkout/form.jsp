<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page isELIgnored="false" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring test</title>
</head>
<body>
<form:form  action="showValue" modelAttribute ="data">
	Value : <form:input path="value"/>
	<input type="submit">
</form:form>
</body>
</html>