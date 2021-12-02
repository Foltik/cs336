<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Admin</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${user != null}">
                <c:choose>
                    <c:when test="${user.role() == 'admin'}">
                        <h1>Edit a user</h1>
                        <p>Enter ID to delete - This action cannot be reversed.</p>
                        <p>${message}</p>
                        <form action="/admindelete" method="POST">
                            ID: <input type="text" name="id"/>
                            <input type="hidden" name="_method" value="PUT"/>
                            <input type="submit" value="Delete"/>
                        </form>
                        <a href="/admin">Return to admin page</a>
                        
                    </c:when>
                    <c:otherwise>
                        <h1>Unauthorized Access</h1>
                        <p>You do not have access to this page.</p>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <h1>Please login to access this page</h1>
                <a href="/login">Return to login</a>
            </c:otherwise>
        </c:choose>
        
    </body>
</html>
