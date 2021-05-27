<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html>
<head>
    <title>Category</title>
</head>
<body>

<h3>${requestScope.category.title}</h3>
<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>

<c:forEach var="product" items="${requestScope.category.products}">
    <c:url value="<%= Paths.VIEW_PRODUCT %>" var="prodUrl">
        <c:param name="id" value="${product.id}"/>
    </c:url>

    <p><a href="${prodUrl}">${product.title}</a>: ${product}</p>
</c:forEach>
</body>
</html>
