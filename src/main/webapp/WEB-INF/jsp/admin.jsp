<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    
    <head>
        <meta charset="utf-8">
        <title>Admin</title>
        <style>
            table,th,td {
                border:1px solid black;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${user != null}">
                <c:choose>
                    <c:when test="${user.role() == 'admin'}">
                        <h1>Welcome to admin page, ${user.first_name()} ${user.last_name()}</h1>
                        <h3>Current registered customers and customer representatives:</h3>
                        <table style="border:1px solid black;">
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Role</th>
                            </tr>
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td><c:out value="${item.id()}"/> </td>
                                    <td><c:out value="${item.username()}"/> </td>
                                    <td><c:out value="${item.password()}"/> </td>
                                    <td><c:out value="${item.first_name()}"/> </td>
                                    <td><c:out value="${item.last_name()}"/> </td>
                                    <td><c:out value="${item.role()}"/> </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <h3>Admin options</h3>
                        <ul>
                            <li><a href="/adminadd">Add a user</a></li>
                            <li><a href="/adminedit">Edit a user</a></li>
                            <li><a href="/admindelete">Delete a user</a></li>
                            <li><a href="/adminactiveflights">List of most active flights</a></li>
                        </ul>
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
