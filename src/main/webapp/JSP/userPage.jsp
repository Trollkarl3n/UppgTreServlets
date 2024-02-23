<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@ include file="fragments/head.jsp" %>
<body>
<%@ include file="fragments/navbar.jsp" %>
<li><form action="/logout" method="POST"><input type="submit" name="logout" value="LOGOUT"></form></li>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>

<div class="container">
    <%-- Visa användarspecifik information --%>
    <h2>Välkommen, ${userBean.userType}</h2>

    <%-- Visa kurser för studenter --%>
    <c:if test="${userBean.userType == 'student'}">
        <h3>Dina kurser:</h3>
        <table class="data">
            <thead>
                <tr>
                    <th>Kursnamn</th>
                    <th>YHP</th>
                    <th>Beskrivning</th>
                    <th>Kurs ID</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr>
                        <td>${course[0]}</td>
                        <td>${course[1]}</td>
                        <td>${course[2]}</td>
                        <td>${course[3]}</td>
                        <td>${course[4]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <table class="data">
            <thead>
                <tr>
                    <th>KursName  </th>
                    <th>FirstName  </th>
                    <th>LastName  </th>
                    <th>TName  </th>
                    <th>TLName  </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${students}" var="student">
                    <tr>
                        <td>${student[0]}</td>
                        <td>${student[1]}</td>
                        <td>${student[2]}</td>
                        <td>${student[3]}</td>
                        <td>${student[4]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <%-- Visa alla kurser och elever för lärare --%>
    <c:if test="${userBean.userType == 'teacher'}">
        <h3>Alla kurser:</h3>
        <table class="data">
            <thead>
                <tr>
                    <th>Kursnamn</th>
                    <th>YHP</th>
                    <th>Beskrivning</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr>
                        <td>${course[1]}</td>
                        <td>${course[2]}</td>
                        <td>${course[3]}</td>
                        <td>${course[4]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <h3>Alla elever:</h3>
        <table class="data">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Förnamn</th>
                    <th>Efternamn</th>
                    <th>Stad</th>
                    <th>Email</th>
                    <th>Mobil</th>
                    <th>Användarnamn</th>
                    <th>Lösenord</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${students}" var="students">
                    <tr>
                        <td>${students[0]}</td>
                        <td>${students[1]}</td>
                        <td>${students[2]}</td>
                        <td>${students[3]}</td>
                        <td>${students[4]}</td>
                        <td>${students[5]}</td>
                        <td>${students[6]}</td>
                        <td>${students[7]}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<%@ include file="fragments/footer.jsp" %>

</body>
</html>