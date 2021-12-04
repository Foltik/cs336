<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Bookings</title>
    </head>
    <body>
        <c:if test="${message != null}">
            <h2>Message: ${message}</h2>
        </c:if>
        <h2>My Bookings</h2>
        <ul>
            <c:forEach items="${bookings}" var="b">
            <li>
                <ul>
                    <li>
                        <form target="_blank" action="/bookings" method="POST">
                            <b>${b.status() == 'RESERVED' ? 'Reserved' : 'Waiting'}</b>
                            <input type="hidden" name="id" value="${b.id()}" />
                            <input type="hidden" name="_method" value="PUT" />
                            <c:if test="${available.get(b.id())}">
                            <input type="submit" value="${departingFull.get(loop.index) ? 'Join Waitlist' : 'Book'}" />
                            </c:if>
                        </form>
                    </li>
                    <c:if test="${b.type() != 'ECONOMY'}">
                    <li>
                        <form action="/bookings" method="POST">
                            <input type="hidden" name="id" value="${b.id()}" />
                            <input type="hidden" name="_method" value="DELETE"/>
                            <input type="submit" value="Cancel" />
                        </form>
                    </li>
                    </c:if>
                    <li>From: ${airports.get(trips.get(b.id()).flights().get(0).from_airport_id()).name()}</li>
                    <li>To: ${airports.get(trips.get(b.id()).flights().get(trips.get(b.id()).flights().size() - 1).to_airport_id()).name()}</li>
                    <li>Class: ${b.type()}</li>
                    <li>Price: ${b.fare()}</li>
                    <li>Duration: ${trips.get(b.id()).duration()} minutes</li>
                    <li>Stops: ${trips.get(b.id()).stops()}</li>
                    <li>Takeoff Time: ${trips.get(b.id()).takeoff_time()}</li>
                    <li>Landing Time: ${trips.get(b.id()).landing_time()}</li>
                    <li>
                        <ul>
                            <c:forEach items="${trips.get(b.id()).flights()}" var="f">
                            <li>
                                <ul>
                                    <li>From: ${airports.get(f.from_airport_id()).name()}</li>
                                    <li>To: ${airports.get(f.to_airport_id()).name()}</li>
                                    <li>Takeoff Time: ${f.takeoff_time()}</li>
                                    <li>Landing Time: ${f.landing_time()}</li>
                                    <li>Domain: ${f.domain()}</li>
                                    <li>Fare: ${f.fare()}</li>
                                    <li>Remaining Seats: ${seats.get(f.id())}</li>
                                </ul>
                            </li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </li>
            </c:forEach>
        </ul>
    </body>
</html>
