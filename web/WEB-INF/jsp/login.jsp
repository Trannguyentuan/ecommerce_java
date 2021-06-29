<%-- 
    Document   : login
    Created on : Jun 25, 2021, 11:40:05 PM
    Author     : TUAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
   <body>
	<h1> Login </h1>
    <form action="${pageContext.request.contextPath}/Login" method="POST">
        <label> Username: </label>
        <input type="text" name="username"> <br>
        <label> Password: </label>
        <input type="password" name="password"> <br>
        <label>&nbsp; </label>
        <input type="submit" value="Login">
        <a href="/Home">ring</a>
    </form>
</body>
</html>
