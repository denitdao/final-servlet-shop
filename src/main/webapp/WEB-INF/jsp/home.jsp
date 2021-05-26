<%@ page import="ua.denitdao.servlet.shop.final_servlet_shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h2>Contents:</h2>
<h3>Hello any: ${sessionScope.getOrDefault("user", "(not logged in)")}</h3>
<a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">- Login (guest)</a><br>
<a href="${pageContext.request.contextPath}<%= Paths.SOME %>">Not working page</a><br>
<a href="${pageContext.request.contextPath}<%= Paths.LOGOUT %>">- Logout (user)</a><br><br>
<a href="${pageContext.request.contextPath}<%= Paths.VIEW_HOME %>">Home page (guest)</a><br>

<p>${sessionScope.role}</p>
<c:forEach var="user" items="${requestScope.users}">
    <p>User: --- ${user}</p>
</c:forEach>
</body>
</html>
