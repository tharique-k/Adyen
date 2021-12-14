<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkout Page</title>
</head>
<body>
<!-- 
<script src="https://checkoutshopper-test.adyen.com/checkoutshopper/sdk/{VERSION}/adyen.js"
integrity="sha384-SGA+BK9i1sG5N4BTCgRH6EGbopUK8WG/azn/TeIHYeBEXmEaB+NT+410Z9b1ii7Z"
crossorigin="anonymous"></script>



<link rel="stylesheet" href="https://checkoutshopper-test.adyen.com/checkoutshopper/sdk/{VERSION}/adyen.css"
integrity="sha384-oT6lIQpTr+nOu+yFBPn8mSMkNQID9wuEoTw8lmg2bcrFoDu/Ae8DhJVj+T5cUmsM"
crossorigin="anonymous">
 -->
<script  type="text/javascript" src="test.js"></script>
<script>
fetch("https://jsonplaceholder.typicode.com/users").then(res => res.json()).then(res => makePayment(res)).then(res => showResults(res));
</script>

<div id="dropin-container"></div>

</body>
</html>