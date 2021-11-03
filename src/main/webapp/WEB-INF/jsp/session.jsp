<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Session</title>
    </head>
    <body>
        Value:
        <form action="/session" method="POST">
            <input type="text" name="value" value="${value}"/>
            <input type="submit" value="Update"/>
        </form>
    </body>
</html>
