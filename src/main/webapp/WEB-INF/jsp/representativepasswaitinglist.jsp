<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Passenger waiting list</title>
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
                        <h3>List of all the passengers on a waiting list for a particular flight</h3>
                        <p>Passengers who are waiting for a seat in a flight are listed below.</p>
                        <table style="border:1px solid black;">
                            <tr>
                                <th>Flight ID</th>
                                <th>Passenger</th>
                            </tr>
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td><c:out value="${item.id()}"/> </td>
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
