<%-- 
    Document   : login
    Created on : Jul 1, 2021, 11:24:24 PM
    Author     : Tuan
--%>

<%@page import="model.Category"%>
<%@page import="dao.CategoryDao"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="dao.UserDao"%>
<%@page import="model.Product"%>
<%@page import="model.OrderDetail"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h1> Login </h1>
        <form action="${pageContext.request.contextPath}/Login" method="POST">
        <label> Username: </label>
        <input type="text" name="username"> <br>
        <label> Password: </label>
        <input type="password" name="password"> <br>
        <input type="submit" value="Login">
    </body>
</html>
