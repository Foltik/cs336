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
        
        <c:choose>
            <c:when test="${user.role() == 'REPRESENTATIVE'}"> <!--User is a represenative, will be able to respond to questions-->
                <h1>Questions and Answers</h1>
                <c:forEach var="item" items="${qlist}"> <!--For every item in the answer list-->
                    <h3>Question: </h3>
                    ID: ${item.id()} <br>
                    Title: ${item.title()} <br>
                    Content: <br>
                    ${item.body()}
                    <br>
                    <br>
                    <c:forEach var="item2" items="${alist}"> <!--For every item in the answer list-->
                        <c:if test="${item2.qid() == item.id()}">
                            <h3>Answer: </h3>
                            Answer ID: ${item2.id()} <br>
                            ID of Question Answered: ${item2.qid()} <br>
                            Answer: <br>
                            ${item2.body()}
                            <br>
                            <br>
                        </c:if>
                    </c:forEach>
                </c:forEach>

                <!--Form for allowing represenatives to answer questions-->
                <form action="/qna" method="POST">

                    QuestionID: <input type="number" name="qid"/>
                    Answer: <input type="text" name="abody"/>
                    <input type="submit" value="Submit"/>
                    <br>
                    <br>

                </form>

                
                <form action="/qna" method="POST">
                    Look Up Question: <input type="text" name="qkeyword"/>
                    Look Up Answer: <input type="text" name="akeyword"/>
                    <input type="submit" value="Search"/>
                    <br>
                    <br>
                </form>
                
                <h3>Search Results: </h3><br>
   
                <c:if test="${qresults.size() > 0}">
                    <c:forEach var="item" items="${qresults}"> <!--Question LookUp Results-->
                        <h3>Question: </h3>
                        ID: ${item.id()} <br>
                        Title: ${item.title()} <br>
                        Content: <br>
                        ${item.body()} <br><br>
                    </c:forEach>
                </c:if>

                <c:if test="${qresults.size() == 0}">
                    <h3>NO QUESTION MATCHING KEYWORDS</h3>
                </c:if>

                <c:if test="${aresults.size() > 0}">
                    <c:forEach var="item" items="${aresults}"> <!--Question LookUp Results-->
                        <h3>Answer: </h3>
                        Answer ID: ${item.id()} <br>
                        ID of Question Answered: ${item.qid()} <br>
                        Answer: <br>
                        ${item.body()} <br><br>
                    </c:forEach>
                </c:if>

                <c:if test="${aresults.size() == 0}">
                    <h3>NO ANSWERS MATCHING KEYWORDS</h3>
                </c:if>
                
            </c:when> 

            <c:when test="${user.role() == 'CUSTOMER'}"> <!--User is a customer-->
                <h1>Questions and Answers</h1>
                <c:forEach var="item" items="${qlist}"> <!--For every item in the answer list-->
                    <h3>Question: </h3>
                    Question ID: ${item.id()} <br>
                    Title: ${item.title()} <br>
                    Content: <br>
                    ${item.body()}
                    <br>
                    <br>
                    <c:forEach var="item2" items="${alist}"> <!--For every item in the answer list-->
                        <c:if test="${item2.qid() == item.id()}">
                            <h3>Answer: </h3>
                            Answer ID: ${item2.id()} <br>
                            ID of Question Answered: ${item2.qid()} <br>
                            Answer: <br>
                            ${item2.body()}
                            <br>
                            <br>
                        </c:if>
                    </c:forEach>
                </c:forEach>


                <!--Form for allowing customers to ask questions-->
                <form action="/qna" method="POST">
                    Question Title: <input type="text" name="qtitle"/>
                    Question: <input type="text" name="qbody"/>
                    <input type="submit" value="Submit"/>
                    <br>
                    <br>
                </form>

                <form action="/qna" method="GET">
                    Look Up Question: <input type="text" name="qkeyword"/>
                    Look Up Answer: <input type="text" name="akeyword"/>
                    <input type="submit" value="Search"/>
                    <br>
                    <br>
                </form>
                <h3>Search Results: </h3><br><br>

                
            </c:when> 
        </c:choose>
    </body>
</html>
