<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>All orders</title>
</head>
<body>

<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>

<c:forEach var="order" items="${requestScope.orders}">
    <c:url value="<%= Paths.VIEW_ORDER %>" var="orderUrl">
        <c:param name="id" value="${order.id}"/>
    </c:url>
    <p>Status: ${requestScope.order.status}</p>
    <p><a href="${orderUrl}">View order ${order.id}</a>: ${order}</p>
</c:forEach>

</body>
</html>
