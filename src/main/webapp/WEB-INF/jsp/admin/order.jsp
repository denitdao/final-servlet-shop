<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="order_jsp.title"/> #${requestScope.order.id}</title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <div class="col mx-5 mt-4 mb-5">
        <h3 class="h3"><fmt:message key="order_jsp.title"/> #${requestScope.order.id}</h3>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="order_jsp.table.item"/></th>
            <th scope="col"><fmt:message key="order_jsp.table.price"/></th>
            <th scope="col"><fmt:message key="order_jsp.table.amount"/></th>
            <th scope="col"><fmt:message key="order_jsp.table.total"/></th>
        </tr>
        </thead>
        <tbody>
        <c:set var="totalPrice" value="0" scope="page"/>
        <c:forEach var="item" items="${requestScope.order.products}">
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
            <th scope="row" colspan="3"><fmt:message key="order_jsp.table.total_all"/></th>
            <td>$ ${totalPrice}</td>
        </tr>
        </tbody>
    </table>

    <h5 class="h5"><fmt:message key="order_jsp.h5"/>: <fmt:message
            key="status.${requestScope.order.status.toString()}"/></h5>

    <form action="<%= Paths.POST_UPDATE_ORDER %>" method="post" class="row justify-content-between m-4">
        <div class="col-6">
            <div class="form-floating">
                <select class="form-select pb-1" name="status" id="statusSelect">
                    <c:forEach var="status" items="${requestScope.statuses}">
                        <option value="${status.toString()}"
                            ${status.toString() eq requestScope.order.status ? 'selected' : ''}>
                            <fmt:message key="status.${status.toString()}"/>
                        </option>
                    </c:forEach>
                </select>
                <label for="statusSelect"><fmt:message key="order_jsp.form.label"/></label>
            </div>
        </div>
        <input type="number" name="id" aria-label="id" value="${requestScope.order.id}" hidden>
        <div class="col-auto">
            <input type="submit" class="btn btn-primary" value='<fmt:message key="order_jsp.form.button"/>'>
        </div>
        <c:if test="${not empty sessionScope.errorMessage}">
            <pre class="h5 mt-3 mb-5">${sessionScope.errorMessage}</pre>
        </c:if>
    </form>
</main>

<c:remove var="errorMessage" scope="session"/>
</body>
</html>
