<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Login</title>
    </head>
    <body>
        <c:if test="${user != null}">
            <h2>Logged in as: ${user.first_name()} ${user.last_name()}</h2>
        </c:if>

        <c:if test="${message != null}">
            <h2>Message: ${message}</h2>
        </c:if>

        <form action="/login" method="POST">
            Username: <input type="text" name="username"/>
            Password: <input type="text" name="password"/>
            <input type="submit" value="Login"/>
        </form>

        <form action="/login" method="POST">
            <input type="hidden" name="_method" value="DELETE"/>
            <input type="submit" value="Logout"/>
        </form>

        <form action="/login" method="POST">
            Username: <input type="text" name="username"/>
            Password: <input type="text" name="password"/>
            First Name: <input type="text" name="first_name"/>
            Last Name: <input type="text" name="last_name"/>
            <input type="hidden" name="_method" value="PUT"/>
            <input type="submit" value="Register"/>
        </form>
        <br>
        <a href="/">Return to home page</a>
    </body>
</html>
