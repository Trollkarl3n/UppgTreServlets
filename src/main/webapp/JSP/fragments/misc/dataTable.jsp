<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>

<table id="data">
    <c:forEach items="${data}" var="dataPunkt">
        <tr>
            <c:forEach items="${dataPunkt}" var="dataPunktKolumn">
                <td>${dataPunktKolumn}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

</body>
</html>