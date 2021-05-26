<%@ page import="ua.denitdao.servlet.shop.final_servlet_shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login_jsp.title"/></title>
</head>
<body>
<h1>Login here</h1>
<br>
<form action="<%= Paths.LOGIN %>" method="post">
    <fmt:message key="login_jsp.label.login"/>: <input type="text" name="login" value="${sessionScope.getOrDefault("wrong_login", "")}"><br>
    <fmt:message key="login_jsp.label.password"/>: <input type="text" name="password"><br>
    <input type="submit" value='<fmt:message key="login_jsp.button.login"/>'>
</form>
<c:if test="${not empty sessionScope.login_status}">
    <h3>Error message: ${sessionScope.login_status}</h3>
</c:if>
<c:remove var="login_status" scope="session"/>
<c:remove var="wrong_login" scope="session"/>
</body>
</html>