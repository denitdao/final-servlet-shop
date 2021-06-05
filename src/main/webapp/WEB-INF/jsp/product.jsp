<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="product_jsp.title"/>: ${requestScope.product.title}</title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <nav class="mt-4" style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <a href="${pageContext.request.contextPath}<%= Paths.VIEW_HOME %>"><fmt:message
                        key="home_jsp.title"/></a>
            </li>
            <li class="breadcrumb-item" aria-current="page">
                <c:url value="<%= Paths.VIEW_CATEGORY %>" var="toCategoryUrl">
                    <c:param name="id" value="${requestScope.product.category.id}"/>
                </c:url>
                <a href="${toCategoryUrl}">${requestScope.product.category.title}</a>
            </li>
            <li class="breadcrumb-item active" aria-current="page">${requestScope.product.title}</li>
        </ol>
    </nav>

    <div class="row justify-content-between">
        <div class="col mx-5 mt-2 mb-5">
            <h3 class="h3">${requestScope.product.title}</h3>
        </div>
        <c:if test="${sessionScope.role eq 'ADMIN'}">
            <div class="col-auto mx-5 mt-2 mb-5">
                <c:url value="<%= Paths.VIEW_UPDATE_PRODUCT %>" var="editProdUrl">
                    <c:param name="id" value="${requestScope.product.id}"/>
                </c:url>
                <a href="${editProdUrl}" class="btn btn-warning"><fmt:message key="action.edit"/></a>
                <c:url value="<%= Paths.POST_DELETE_PRODUCT %>" var="deleteProdUrl">
                    <c:param name="product_id" value="${requestScope.product.id}"/>
                    <c:param name="category_id" value="${requestScope.product.category.id}"/>
                </c:url>
                <a href="${deleteProdUrl}" class="btn btn-danger"><fmt:message key="action.delete"/></a>
            </div>
        </c:if>
    </div>

    <p>${requestScope.product.description}</p>
    <p>${requestScope.product.color}</p>
    <p>${requestScope.product.weight}</p>
    <p>${requestScope.product.price}</p>
    <c:forEach var="property" items="${requestScope.product.properties}">
        <p>${property.key.title} - ${property.value}</p>
    </c:forEach>

    <form action="<%= Paths.POST_ADD_TO_CART %>" method="post" class="row">
        <div class="mb-3 col-2">
            <div class="input-group">
                <span class="input-group-text">Amount</span>
                <input type="number" min="0" name="amount" value="${sessionScope.cart.products.get(requestScope.product.id)}"
                       class="form-control" placeholder="0" maxlength="3" aria-label="amount">
            </div>
        </div>

        <input type="number" name="product_id" aria-label="product_id" value="${requestScope.product.id}" hidden>
        <div class="mb-3 col-auto">
            <input class="btn btn-primary" type="submit" value='<fmt:message key="product_jsp.form.button"/>'>
        </div>

        <%@ include file="/WEB-INF/parts/error_message.jspf" %>
    </form>
</main>
</body>
</html>
