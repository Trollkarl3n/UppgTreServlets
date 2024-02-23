<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Title</title>
    <link rel="stylesheet" type="text/css" href="src/main/webapp/CSS/style.css">
    <%@ include file="fragments/head.jsp" %>
</head>
<body>
    <%@ include file="fragments/navbar.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">

    <main>
        <%@ include file="fragments/loginForm.jsp" %>
        <%@ include file="fragments/misc/errorMessage.jsp" %>
    </main>

    <%@ include file="fragments/footer.jsp" %>
</body>
</html>