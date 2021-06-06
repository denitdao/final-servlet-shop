<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

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
            <tr>
                <th scope="col"><fmt:message key="order_jsp.table.item"/></th>
                <th scope="col"><fmt:message key="order_jsp.table.price"/></th>
                <th scope="col"><fmt:message key="order_jsp.table.amount"/></th>
                <th scope="col"><fmt:message key="order_jsp.table.total"/></th>
                <th scope="col"></th>
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
                    <td><my:currencyConverter value='${item.product.price}'/></td>
                    <td>${item.amount}</td>
                    <td><my:currencyConverter value='${item.amount * item.product.price}'/></td>
                    <td>
                        <form action="<%= Paths.POST_ADD_TO_CART %>" method="post" class="row">
                            <input type="number" name="amount" value="0" aria-label="amount"
                                   hidden>
                            <input type="number" name="product_id" aria-label="product_id"
                                   value="${item.product.id}" hidden>
                            <input class="btn btn-outline-danger btn-sm" type="submit"
                                   value='<fmt:message key="order_jsp.table.button"/>'>
                        </form>
                    </td>
                    <c:set var="totalPrice" value="${totalPrice + item.amount * item.product.price}" scope="page"/>
                </tr>
            </c:forEach>

            <tr>
                <th scope="row" colspan="3"><fmt:message key="order_jsp.table.total_all"/></th>
                <td><my:currencyConverter value='${totalPrice}' /></td>
            </tr>
            </tbody>
        </table>
        <a href="<%= Paths.POST_ADD_ORDER %>" class="btn btn-primary"><fmt:message key="cart_jsp.button"/></a>

    </c:if>

    <c:if test="${empty requestScope.orderProducts}">
        <p><fmt:message key="cart_jsp.message.empty"/></p>
    </c:if>

    <%@ include file="/WEB-INF/parts/error_message.jspf" %>
</main>
</body>
</html>
