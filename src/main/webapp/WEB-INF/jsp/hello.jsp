<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Flight Service</title>
    </head>
    <body>
        <h1>Welcome to the Group10 Flight Reservations!</h1>

        <c:choose>
            <c:when test="${user == null}">
                <p>You are not logged in. Please log in <a href="/login">here</a></p>
            </c:when>
            <c:when test="${user != null}">

                <div style="display: flex; align-items: center; gap: 10px">
                    <p>You are logged in as ${user.first_name()} ${user.last_name()}</p>
                    <form action="/login" method="POST" style="margin: 0">
                        <input type="hidden" name="_method" value="DELETE"/>
                        <input type="submit" value="Logout"/>
                    </form>
                </div>

                <h3>Menu</h3>
                <ul>
                    <c:choose>
                        <c:when test="${user.role() == 'CUSTOMER'}">
                            <li><a href="/search">Search for flights</a></li>
                            <li><a href="/bookings">My bookings</a></li>
                            <li><a href="/qna">Ask a question</a></li>
                        </c:when>
                        <c:when test="${user.role() == 'REPRESENTATIVE'}">
                            <li><a href="/customerrep">Customer Representative homepage</a></li>
                            <li><a href="/qna">View and answer questions</a></li>
                        </c:when>
                        <c:when test="${user.role() == 'ADMIN'}">
                            <li><a href="/admin">Admin homepage</a></li>
                        </c:when>
                    </c:choose>
                </ul>
            </c:when>
        </c:choose>
    </body>
</html>
