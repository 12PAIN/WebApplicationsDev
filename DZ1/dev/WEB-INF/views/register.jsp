<%@ page import="java.io.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <% if(request.getAttribute("regError") != null){%>
            <br> Login Error!
        <% } %>
        <form method="post" action="UserCreate">
            <br><input name="new_login" placeholder="Login">
            <br><input type="password" name="new_password" placeholder="Password">
            <br><input type="submit" value="Register and Login">
        </form>

    </body>
</html>