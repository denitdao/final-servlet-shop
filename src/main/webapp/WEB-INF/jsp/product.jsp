<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>Product</title>
</head>
<body>

<h3>${requestScope.product.title}</h3>
<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>
<p>${requestScope.product.description}</p>
<p>${requestScope.product.color}</p>
<p>${requestScope.product.height}</p>
<p>${requestScope.product.price}</p>
<c:forEach var="property" items="${requestScope.product.properties}">
    <p>${property.key}  -  ${property.value}</p>
</c:forEach>
<a href="#">${requestScope.product.category}</a>
</body>
</html>
