<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>

<html>
    <head>
        <meta charset="utf-8">
        <title>Questions and Answers</title>
    </head>
    <body>
        <c:if test="${user != null}"> <!--We can see that the user is not null-->
            <h2>Logged in as: ${user.first_name()} ${user.last_name()}</h2>
        </c:if> 
        
        <c:if test="${user.role == 'represenative'}"> <!--User is a represenative-->
            
        </c:if> 

        <c:if test="${user.role == 'customer'}"> <!--User is a customer-->
            
        </c:if> 

    </body>
</html>
