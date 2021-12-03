<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Airlines</title>
    </head>
    <body>
        Name:
        <form action="/airlines" method="POST">
            <input type="text" name="name"/>
            <input type="submit" value="Create"/>
        </form>
        <ul>
            <c:forEach items="${airlines}" var="a">
            <li>
                <ul>
                    <li>ID: ${a.id()}</li>
                    <li>Name:
                        <form action="/airlines" method="POST">
                            <input type="text" name="name" value="${a.name()}"/>
                            <input type="number" name="name" value="${a.name()}"/>
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
            </c:forEach>
        </ul>
    </body>
</html>
