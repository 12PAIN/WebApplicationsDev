<%@ page import="java.io.*" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <% if(request.getAttribute("loginError") != null){%>
            <br> Login Error!
        <% } %>

        <form method="post" action="table">
            <br><input name="login" placeholder="Your Login">
            <br><input type="password" name="password" placeholder="Your Password">
            <br><input type="submit" value="Login">
        </form>
        <form method="post" action="register"><br><input type="submit" value="Register"></form>

    </body>
</html>