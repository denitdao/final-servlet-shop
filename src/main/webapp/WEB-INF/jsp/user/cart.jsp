<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>My Cart</title>
</head>
<body>

<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>

<c:forEach var="item" items="${requestScope.orderProducts}">
    <c:url value="<%= Paths.VIEW_PRODUCT %>" var="prodUrl">
        <c:param name="id" value="${item.product.id}"/>
    </c:url>

    <p><a href="${prodUrl}">${item.product.title}</a>: ${item.amount}</p>
</c:forEach>
<c:url value="<%= Paths.POST_ADD_ORDER %>" var="orderUrl">
    <c:param name="id" value="${sessionScope.user.id}"/>
</c:url>
<p><a href="${orderUrl}">Make order</a></p>
</body>
</html>
