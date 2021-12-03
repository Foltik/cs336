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
                    <c:when test="${user.role() == 'ADMIN'}">
                        <h1>Add a user</h1>
                        <p>Note that you cannot add another admin.</p>
                        <p>${message}</p>
                        <form action="/adminadd" method="POST">
                            Username: <input type="text" name="username"/>
                            Password: <input type="text" name="password"/>
                            First Name: <input type="text" name="first_name"/>
                            Last Name: <input type="text" name="last_name"/>
                            Role: 
                            <select name="role">
                                <option value="CUSTOMER">Customer</option>
                                <option value="REPRESENTATIVE">Representative</option>
                            </select>
                            <input type="hidden" name="_method" value="PUT"/>
                            <input type="submit" value="Add"/>
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
