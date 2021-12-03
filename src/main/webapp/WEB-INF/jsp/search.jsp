<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Search</title>
        <script>
            function type() {
                var ty = document.querySelector('#type');
                document.querySelector('#arrival').style.display = 'none';
                if (ty.value == 'ONE_WAY') {
                    document.querySelector('#arrival').style.display = 'none';
                    document.querySelector('#arrival_flexible').style.display = 'none';
                    document.querySelector('label[for=arrival]').style.display = 'none';
                    document.querySelector('label[for=arrival_flexible]').style.display = 'none';
                } else {
                    document.querySelector('#arrival').style.display = 'inline-block';
                    document.querySelector('#arrival_flexible').style.display = 'inline-block';
                    document.querySelector('label[for=arrival]').style.display = 'inline-block';
                    document.querySelector('label[for=arrival_flexible]').style.display = 'inline-block';
                }
            }
            window.addEventListener('DOMContentLoaded', () => type());
        </script>
    </head>
    <body>
        <form action="/search" method="POST">
            <label for="origin">Origin</label>
            <select id="origin" name="origin">
                <c:forEach items="${airports}" var="a">
                <option value="${a.id()}" ${a.id() == query.origin() ? "selected" : ""}>${a.name()}</option>
                </c:forEach>
            </select>

            <label for="destination">Destination</label>
            <select id="destination" name="destination">
                <c:forEach items="${airports}" var="a">
                <option value="${a.id()}" ${a.id() == query.destination() ? "selected" : ""}>${a.name()}</option>
                </c:forEach>
            </select>

            <label for="type">Trip Type</label>
            <select id="type" name="type" onchange="window.type()">
                <option value="ONE_WAY" ${query.type() == "ONE_WAY" ? "selected" : ""}>One Way</option>
                <option value="ROUND_TRIP" ${query.type() == "ROUND_TRIP" ? "selected" : ""}>Round Trip</option>
            </select>

            <label for="departure">Departure Date</label>
            <input type="date" id="departure" name="departure" value="${query.departure()}">

            <label for="departure_flexible">Flexible</label>
            <input type="checkbox" id="departure_flexible" name="departure_flexible" ${query.departure_flexible() ? "checked" : ""} />

            <label for="arrival">Return Date</label>
            <input type="date" id="arrival" name="arrival" value="${query.arrival()}">

            <label for="arrival_flexible">Flexible</label>
            <input type="checkbox" id="arrival_flexible" name="arrival_flexible" ${query.arrival_flexible() ? "checked" : ""} />


            <label for="sort">Sort By</label>
            <select id="sort" name="sort">
                <option value="PRICE" ${query.sort() == "PRICE" ? "selected" : ""}>Price</option>
                <option value="TAKEOFF" ${query.sort() == "TAKEOFF" ? "selected" : ""}>Takeoff Time</option>
                <option value="LANDING" ${query.sort() == "LANDING" ? "selected" : ""}>Landing Time</option>
                <option value="DURATION" ${query.sort() == "DURATION" ? "selected" : ""}>Duration</option>
            </select>

            <select name="direction">
                <option value="ASCENDING" ${query.direction() == "ASCENDING" ? "selected" : ""}>Ascending</option>
                <option value="DESCENDING" ${query.direction() == "DESCENDING" ? "selected" : ""}>Descending</option>
            </select>

            <input type="submit" value="Search" />
        </form>
        <ul>
            <c:forEach items="${trips}" var="t">
            <li>
                <ul>
                    <li>Price: ${t.price()}</li>
                    <li>Duration: ${t.duration()} minutes</li>
                    <li>Takeoff Time: ${t.takeoff_time()}</li>
                    <li>Landing Time: ${t.landing_time()}</li>
                </ul>
            </li>
            </c:forEach>
            <!-- <c:forEach items="${airlines}" var="a">
            <li>
                <ul>
                    <li>ID: ${a.id()}</li>
                    <li>Name:
                        <form action="/airlines" method="POST">
                            <input type="text" name="name" value="${a.name()}"/>
                            <input type="hidden" name="id" value="${a.id()}"/>
                            <input type="hidden" name="_method" value="PUT"/>
                            <input type="submit" value="Update"/>
                        </form>
                    </li>
                    <li>
                        <form action="/airlines" method="POST">
                            <input type="hidden" name="id" value="${a.id()}"/>
                            <input type="hidden" name="_method" value="DELETE"/>
                            <input type="submit" value="Delete"/>
                        </form>
                    </li>
                </ul>
            </li>
            </c:forEach> -->
        </ul>
    </body>
</html>
