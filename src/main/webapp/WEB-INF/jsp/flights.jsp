<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Flights</title>
    </head>
    <body>
        <h2>Scheduled Flights</h2>
        <ul>
            <c:forEach items="${flights}" var="f">
            <li>
                <ul>
                    <li>From: ${airports.get(f.from_airport_id()).name()}</li>
                    <li>To: ${airports.get(f.to_airport_id()).name()}</li>
                    <li>Takeoff Time: ${f.takeoff_time()}</li>
                    <li>Landing Time: ${f.landing_time()}</li>
                    <li>Days: ${f.days()}</li>
                    <li>Domain: ${f.domain()}</li>
                    <li>Fare: ${f.fare()}</li>
                    <li>Remaining Seats: ${seats.get(f.id())}</li>
                </ul>
            </li>
            </c:forEach>
        </ul>
    </body>
</html>
