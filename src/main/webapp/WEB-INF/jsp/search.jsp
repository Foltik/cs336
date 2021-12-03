<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Search</title>
        <script>
            function direction() {
                var dir = document.querySelector('#direction');
                document.querySelector('#arrival').style.display = 'none';
                if (dir.value == 'ONE_WAY') {
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
                <c:forEach items="${airports.values()}" var="a">
                <option value="${a.id()}" ${a.id() == query.origin() ? "selected" : ""}>${a.name()}</option>
                </c:forEach>
            </select>

            <label for="destination">Destination</label>
            <select id="destination" name="destination">
                <c:forEach items="${airports.values()}" var="a">
                <option value="${a.id()}" ${a.id() == query.destination() ? "selected" : ""}>${a.name()}</option>
                </c:forEach>
            </select>

            <label for="direction">Trip Type</label>
            <select id="direction" name="direction" onchange="window.direction()">
                <option value="ONE_WAY" ${query.direction() == "ONE_WAY" ? "selected" : ""}>One Way</option>
                <option value="ROUND_TRIP" ${query.direction() == "ROUND_TRIP" ? "selected" : ""}>Round Trip</option>
            </select>

            <label for="type">Class</label>
            <select id="type" name="type">
                <option value="FIRST_CLASS" ${query.type() == "FIRST_CLASS" ? "selected" : ""}>First Class</option>
                <option value="BUSINESS" ${query.type() == "BUSINESS" ? "selected" : ""}>Business</option>
                <option value="ECONOMY" ${query.type() == "ECONOMY" ? "selected" : ""}>Economy</option>
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
                <option value="DURATION" ${query.sort() == "DURATION" ? "selected" : ""}>Duration</option>
                <option value="STOPS" ${query.sort() == "STOPS" ? "selected" : ""}>Stops</option>
                <option value="TAKEOFF" ${query.sort() == "TAKEOFF" ? "selected" : ""}>Takeoff Time</option>
                <option value="LANDING" ${query.sort() == "LANDING" ? "selected" : ""}>Landing Time</option>
            </select>

            <select name="order">
                <option value="ASCENDING" ${query.order() == "ASCENDING" ? "selected" : ""}>Ascending</option>
                <option value="DESCENDING" ${query.order() == "DESCENDING" ? "selected" : ""}>Descending</option>
            </select>

            <input type="submit" value="Search" />

            <br><br>

            <label for="price">Price</label>
            <select id="price" name="price_filter">
                <option value="NONE" ${query.price_filter() == "NONE" ? "selected" : ""}>N/A</option>
                <option value="GREATER" ${query.price_filter() == "GREATER" ? "selected" : ""}>Greater Than</option>
                <option value="LESS" ${query.price_filter() == "LESS" ? "selected" : ""}>Less Than</option>
            </select>
            <input type="number" min="0" name="price" value="${query.price()}" />

            <label for="duration">Duration (min)</label>
            <select id="duration" name="duration_filter">
                <option value="NONE" ${query.duration_filter() == "NONE" ? "selected" : ""}>N/A</option>
                <option value="GREATER" ${query.duration_filter() == "GREATER" ? "selected" : ""}>Longer Than</option>
                <option value="LESS" ${query.duration_filter() == "LESS" ? "selected" : ""}>Shorter Than</option>
            </select>
            <input type="number" min="0" step="1" name="duration" value="${query.duration()}" />

            <label for="stops">Stops</label>
            <select id="stops" name="stops_filter">
                <option value="NONE" ${query.stops_filter() == "NONE" ? "selected" : ""}>N/A</option>
                <option value="GREATER" ${query.stops_filter() == "GREATER" ? "selected" : ""}>More Than</option>
                <option value="LESS" ${query.stops_filter() == "LESS" ? "selected" : ""}>Fewer Than</option>
            </select>
            <input type="number" min="0" step="1" name="stops" value="${query.stops()}" />

            <label for="takeoff">Takeoff Time</label>
            <select id="takeoff" name="takeoff_filter">
                <option value="NONE" ${query.takeoff_filter() == "NONE" ? "selected" : ""}>N/A</option>
                <option value="GREATER" ${query.takeoff_filter() == "GREATER" ? "selected" : ""}>After</option>
                <option value="LESS" ${query.takeoff_filter() == "LESS" ? "selected" : ""}>Before</option>
            </select>
            <input type="time" name="takeoff" value="${query.takeoff()}" />

            <label for="landing">Landing Time</label>
            <select id="landing" name="landing_filter">
                <option value="NONE" ${query.landing_filter() == "NONE" ? "selected" : ""}>N/A</option>
                <option value="GREATER" ${query.landing_filter() == "GREATER" ? "selected" : ""}>After</option>
                <option value="LESS" ${query.landing_filter() == "LESS" ? "selected" : ""}>Before</option>
            </select>
            <input type="time" name="landing" value="${query.landing()}" />

        </form>
        <h2>Departing Trips</h2>
        <ul>
            <c:forEach items="${departing}" var="t">
            <li>
                <ul>
                    <li>Price: ${t.price()}</li>
                    <li>Duration: ${t.duration()} minutes</li>
                    <li>Stops: ${t.stops()}</li>
                    <li>Takeoff Time: ${t.takeoff_time()}</li>
                    <li>Landing Time: ${t.landing_time()}</li>
                    <li>
                        <ul>
                            <c:forEach items="${t.flights()}" var="f">
                            <li>
                                <ul>
                                    <li>From: ${airports.get(f.from_airport_id()).name()}</li>
                                    <li>To: ${airports.get(f.to_airport_id()).name()}</li>
                                    <li>Takeoff Time: ${f.takeoff_time()}</li>
                                    <li>Landing Time: ${f.landing_time()}</li>
                                    <li>Domain: ${f.domain()}</li>
                                    <li>Fare: ${f.fare()}</li>
                                </ul>
                            </li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </li>
            </c:forEach>
        </ul>
        <c:if test="${query.direction() == 'ROUND_TRIP'}">
        <h2>Arriving Trips</h2>
        <ul>
            <c:forEach items="${arriving}" var="t">
            <li>
                <ul>
                    <li>Price: ${t.price()}</li>
                    <li>Duration: ${t.duration()} minutes</li>
                    <li>Takeoff Time: ${t.takeoff_time()}</li>
                    <li>Landing Time: ${t.landing_time()}</li>
                    <li>
                        <ul>
                            <li>
                            <c:forEach items="${t.flights()}" var="f">
                                <ul>
                                <li>From: ${airports.get(f.from_airport_id()).name()}</li>
                                <li>To: ${airports.get(f.to_airport_id()).name()}</li>
                                <li>Takeoff Time: ${f.takeoff_time()}</li>
                                <li>Landing Time: ${f.landing_time()}</li>
                                <li>Domain: ${f.domain()}</li>
                                <li>Fare: ${f.fare()}</li>
                                </ul>
                            </c:forEach>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            </c:forEach>
        </ul>
        </c:if>
    </body>
</html>
