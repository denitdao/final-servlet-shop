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
    <p>${property.key} - ${property.value}</p>
</c:forEach>

<c:url value="<%= Paths.VIEW_CATEGORY %>" var="toCategoryUrl">
    <c:param name="id" value="${requestScope.product.category.id}"/>
</c:url>
<p><a href="${toCategoryUrl}">Back to ${requestScope.product.category.title}</a></p>

<c:url value="<%= Paths.VIEW_UPDATE_PRODUCT %>" var="editProdUrl">
    <c:param name="id" value="${requestScope.product.id}"/>
</c:url>
<p><a href="${editProdUrl}">Edit</a></p>

<c:url value="<%= Paths.POST_DELETE_PRODUCT %>" var="deleteProdUrl">
    <c:param name="product_id" value="${requestScope.product.id}"/>
    <c:param name="category_id" value="${requestScope.product.category.id}"/>
</c:url>
<p><a href="${deleteProdUrl}">Delete</a></p>

<form action="<%= Paths.POST_ADD_TO_CART %>" method="post">
    Amount to add: <input type="number" name="amount"
                          value="${sessionScope.cart.products.get(requestScope.product.id)}"><br>
    <input type="number" name="product_id" value="${requestScope.product.id}" hidden>
    <input type="submit" value='Add to cart'>
</form>
<c:if test="${not empty sessionScope.errorMessage}">
    <h3>Error message: ${sessionScope.errorMessage}</h3>
</c:if>
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
