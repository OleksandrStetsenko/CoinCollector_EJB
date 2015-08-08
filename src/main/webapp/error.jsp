<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>User Page</title>
    <style>
        body {
         background: #EEE8AA;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>

<c:if test="${not empty msg}">
    <h1 style="color:red">${msg}</h1>
</c:if>

<c:if test="${not empty stack}">
    <h2>${stack}</h2>
</c:if>

<jsp:include page="footer.html"></jsp:include>

</body>
</html>