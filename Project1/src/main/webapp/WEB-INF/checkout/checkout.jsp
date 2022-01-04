<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adyen Checkout Page</title>
<link rel="stylesheet"
     href="https://checkoutshopper-live.adyen.com/checkoutshopper/sdk/4.9.0/adyen.css"
     integrity="sha384-0IvbHDeulbhdg1tMDeFeGlmjiYoVT6YsbfAMKFU2lFd6YKUVk0Hgivcmva3j6mkK"
     crossorigin="anonymous">

<style>
.dropin-box{
	margin:auto;
    width: 50%;
    margin-top: 50px;
    /*
     top: 50%;
	left: 50%;
    margin-top: -50px;
    margin-left: -50px;
    height: 100px;*/
}
h1{
	margin:auto;
	width: 50%;
    margin-top: 50px;
}
</style>
</head>
<body>
<h1> Adyen Checkout Page</h1>
<div id="dropin-container" class="dropin-box"></div>

<script src="https://checkoutshopper-live.adyen.com/checkoutshopper/sdk/4.9.0/adyen.js"
     integrity="sha384-aEL1fltFqDd33ItS8N+aAdd44ida67AQctv9h57pBGjNJ8E2xxbX/CVALJqO8/aM"
     crossorigin="anonymous"></script>
	<!-- Adyen provides the SRI hash that you include as the integrity attribute.-->
	<!-- Refer to our release notes to get the SRI hash for the specific version: https://docs.adyen.com/online-payments/release-notes -->
	

<script  type="text/javascript" src="../../resources/dropin-configuration.js"></script>

</body>
</html>