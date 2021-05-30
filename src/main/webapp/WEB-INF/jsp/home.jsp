<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>Home</title>
</head>
<body>
<h3>${sessionScope.getOrDefault("user", "(Guest)")}</h3>
<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>
<h4>Item in the cart: ${sessionScope.cart.products.size()}</h4>
<c:forEach var="category" items="${requestScope.categories}">
    <c:url value="<%= Paths.VIEW_CATEGORY %>" var="catUrl">
        <c:param name="id" value="${category.id}"/>
    </c:url>

    <p><a href="${catUrl}">${category.title}</a>: ${category}</p>
</c:forEach>
</body>
</html>
