<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h2>Oops! An error occurred</h2>
<c:if test="${not empty requestScope.errorMessage}">
    <h3>Error message: ${requestScope.errorMessage}</h3>
</c:if>
<c:if test="${not empty sessionScope.errorMessage}">
    <h3>Error message: ${sessionScope.errorMessage}</h3>
</c:if>
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
