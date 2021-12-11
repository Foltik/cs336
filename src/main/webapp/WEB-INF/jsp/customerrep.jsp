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
                    <c:when test="${user.role() == 'REPRESENTATIVE'}">
                        <h1>Welcome to customer representative page, ${user.first_name()} ${user.last_name()}</h1>
                        <h3>Current registered customers:</h3>
                        <table style="border:1px solid black;">
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>First Name</th>
                                <th>Last Name</th>                            
                            </tr>
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td><c:out value="${item.id()}"/> </td>
                                    <td><c:out value="${item.username()}"/> </td>
                                    <td><c:out value="${item.password()}"/> </td>
                                    <td><c:out value="${item.first_name()}"/> </td>
                                    <td><c:out value="${item.last_name()}"/> </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <h3>Representative options</h3>
                        <ul>
                            <li><a href="/representativeflightadd">Add a flight</a></li>
                            <li><a href="/representativeflightupdate">Edit a flight</a></li>
                            <li><a href="/representativeflightdelete">Delete a flight</a></li>
                            <li><a href="/representativeairportadd">Add an Airport</a></li>
                            <li><a href="/representativeairportupdate">Edit an Airport</a></li>
                            <li><a href="/representativeairportdelete">Delete an Airport</a></li>
                            <li><a href="/representativeaircraftadd">Add an Aircraft</a></li>
                            <li><a href="/representatitiveaircraftupdate">Edit an Aircraft</a></li>
                            <li><a href="/representativeaircraftdelete">Delete an Aircraft</a></li>
                            <li><a href="/representativepasswaitinglist">List of passengers on waiting list</a></li>
                            <li><a href="/representativefromflightlist">List of all flights from a given airport</a></li>
                            <li><a href="/representativetoflightlist">List of all flights to a given airport</a></li>
                            <li><a href="/representativereply">Reply to a user's question</a></li>
                            <li><a href="/representativereservation">Make flight reservations on behalf of users</a></li>
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
