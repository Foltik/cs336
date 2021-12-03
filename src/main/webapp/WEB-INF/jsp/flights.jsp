<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Flights</title>
    </head>
    <body>
        Name:
        <form action="/flights" method="POST">
            <input type="text" name="name"/>
            <input type="submit" value="Create"/>
        </form>
        <ul>
            <c:forEach items="${flights}" var="f">
            <li>
                <ul>
                    <li>ID: ${a.id()}</li>
                    <li>
                        <form action="/flights" method="POST">
                            <input type="hidden" name="id" value="${f.id()}"/>
                            <input type="hidden" name="_method" value="DELETE"/>
                            <input type="submit" value="Delete"/>
                        </form>
                    </li>
                </ul>
            </li>
            </c:forEach>
        </ul>
    </body>
</html>
