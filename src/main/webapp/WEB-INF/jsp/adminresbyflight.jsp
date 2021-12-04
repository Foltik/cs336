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
                    <c:when test="${user.role() == 'ADMIN'}">
                        <h1>Get reservations by flight id</h1>
                        <form action="/adminresbyflight" method="POST">
                            Flight ID: <input type="text" name="customer_id"/>
                            <input type="hidden" name="_method" value="PUT"/>
                            <input type="submit" value="Get"/>
                        </form>
                        <table style="border:1px solid black;">
                            <tr>
                                <th>Customer ID</th>
                                <th>Fare paid</th>
                                <th>Purchased on</th>
                            </tr>
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td><c:out value="${item.customer_id()}"/> </td>
                                    <td><c:out value="${item.fare()}"/> </td>
                                    <td><c:out value="${item.purchased_on()}"/> </td>
                                </tr>
                            </c:forEach>
                        </table>
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
