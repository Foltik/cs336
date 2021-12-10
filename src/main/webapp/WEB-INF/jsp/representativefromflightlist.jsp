<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Flights from airports</title>
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
                    <c:when test="${user.role() == 'REPRESENTATIVE'}">
                        <h3>List of all the flights from a particular airport</h3>
                        <p>These flights are leaving from their destination.</p>
                        <table style="border:1px solid black;">
                            <tr>
                                <th>Airport ID</th>
                                <th>Flight</th>
                            </tr>
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td><c:out value="${item.from_airport_id()}"/> </td>
                                    <td><c:out value="${item.count()}"/> </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <a href="/customerrep">Return to representative page</a>
                        
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
