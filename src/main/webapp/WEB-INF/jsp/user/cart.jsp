<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="cart_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <div class="col mx-5 mt-4 mb-5">
        <h3 class="h3"><fmt:message key="cart_jsp.title"/></h3>
    </div>
    <c:if test="${not empty requestScope.orderProducts}">
        <table class="table table-hover">
            <thead>
            <%-- todo: localize--%>
            <tr>
                <th scope="col">Item</th>
                <th scope="col">Price</th>
                <th scope="col">Amount</th>
                <th scope="col">Total</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="totalPrice" value="0" scope="page"/>
            <c:forEach var="item" items="${requestScope.orderProducts}">
                <c:url value="<%= Paths.VIEW_PRODUCT %>" var="prodUrl">
                    <c:param name="id" value="${item.product.id}"/>
                </c:url>
                <tr>
                    <th scope="row"><a href="${prodUrl}">${item.product.title}</a></th>
                    <td>$ ${item.product.price}</td>
                    <td>${item.amount}</td>
                    <td>$ ${item.amount * item.product.price}</td>
                    <c:set var="totalPrice" value="${totalPrice + item.amount * item.product.price}" scope="page"/>
                </tr>
            </c:forEach>

            <tr>
                <th scope="row" colspan="3">Total</th>
                <td>$ ${totalPrice}</td>
            </tr>
            </tbody>
        </table>
        <p><a href="<%= Paths.POST_ADD_ORDER %>" class="btn btn-primary">Make order</a></p>


    </c:if>

    <c:if test="${empty requestScope.orderProducts}">
        <p>Cart is empty</p>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <h3 class="h3">Error message: ${sessionScope.errorMessage}</h3>
    </c:if>
</main>

<c:remove var="errorMessage" scope="session"/>
</body>
</html>
