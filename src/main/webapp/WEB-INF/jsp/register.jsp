<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Register here</h1>
<br>
<form action="<%= Paths.POST_LOGIN %>" method="post">
    Name: <input type="text" name="name"><br>
    Login: <input type="text" name="login"
                  value="${sessionScope.getOrDefault("wrong_login", "")}"><br>
    Password: <input type="text" name="password"><br>
    <input type="submit" value='<fmt:message key="login_jsp.button.login"/>'>
</form>
<a href="${pageContext.request.contextPath}<%= Paths.LOGIN_JSP %>">Logout</a><br>
<c:if test="${not empty sessionScope.login_status}">
    <h3>Error message: ${sessionScope.login_status}</h3>
</c:if>
<c:remove var="login_status" scope="session"/>
<c:remove var="wrong_login" scope="session"/>
</body>
</html>
