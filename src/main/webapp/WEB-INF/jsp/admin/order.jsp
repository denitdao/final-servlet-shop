<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>Order # ${requestScope.order.id}</title>
</head>
<body>

<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>
<p>Status: ${requestScope.order.status}</p>
<c:forEach var="item" items="${requestScope.order.products}">
    <c:url value="<%= Paths.VIEW_PRODUCT %>" var="prodUrl">
        <c:param name="id" value="${item.product.id}"/>
    </c:url>

    <p><a href="${prodUrl}">${item.product.title}</a>: ${item.amount}</p>
</c:forEach>

<form action="<%=Paths.POST_UPDATE_ORDER%>" method="post">

    <select name="status">
        <c:forEach var="status" items="${requestScope.statuses}">
            <option value="${status.toString()}"
                    <c:if test="${status.toString() eq requestScope.order.status}">
                        selected
                    </c:if>
            >${status.toString()}</option>
        </c:forEach>
    </select>
    <input type="number" name="id" value="${requestScope.order.id}" hidden>
    <input type="submit" value='Add to cart'>
</form>

</body>
</html>
