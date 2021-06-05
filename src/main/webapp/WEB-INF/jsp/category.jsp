<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="category_jsp.title"/>: ${requestScope.category.title}</title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container-fluid">
    <div class="container">
        <nav class="mt-4" style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_HOME %>">
                        <fmt:message key="home_jsp.title"/>
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">${requestScope.category.title}</li>
            </ol>
        </nav>

        <div class="row justify-content-between">
            <div class="col mx-5 mt-2 mb-4">
                <h3 class="h3">${requestScope.category.title}</h3>
            </div>
            <c:if test="${sessionScope.role eq 'ADMIN'}">
                <div class="col-auto mx-5 mt-2 mb-4">
                    <c:url value="<%= Paths.VIEW_ADD_PRODUCT %>" var="addProdUrl">
                        <c:param name="id" value="${requestScope.category.id}"/>
                    </c:url>
                    <a href="${addProdUrl}" class="btn btn-success"><fmt:message key="add_product_jsp.title"/></a>
                </div>
            </c:if>
        </div>
    </div>

    <div class="row m-4">
        <form class="col-auto col-lg-3" method="get" action="<%= Paths.VIEW_CATEGORY %>">
            <div class="card mb-2">
                <div class="card-header">
                    <fmt:message key="category_jsp.form.ordering_direction"/>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="orderValue" items="${requestScope.sortingOrderValues}">
                        <c:set var="value" value="${orderValue.toString()}"/>
                        <li class="list-group-item">
                            <input class="form-check-input" type="radio" name="sorting_order" value="${value}"
                                   id="order_${value}" ${requestScope.sortingOrder eq orderValue ? 'checked' : ''}>
                            <label class="form-check-label" for="order_${value}">
                                <fmt:message key="sorting_order.${value}"/>
                            </label>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="card mb-2">
                <div class="card-header">
                    <fmt:message key="category_jsp.form.order_by"/>
                </div>
                <ul class="list-group list-group-flush">
                    <c:forEach var="paramValue" items="${requestScope.sortingParamValues}">
                        <c:set var="value" value="${paramValue.toString()}"/>
                        <li class="list-group-item">
                            <input class="form-check-input" type="radio" name="sorting_param" value="${value}"
                                   id="param_${value}" ${requestScope.sortingParam eq paramValue ? 'checked' : ''}>
                            <label class="form-check-label" for="param_${value}">
                                    <fmt:message key="sorting_param.${value}"/>
                            </label>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="card mb-2">
                <div class="card-header">
                    <fmt:message key="category_jsp.form.price_range"/>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <div class="form-group input-group-sm">
                            <label for="price_min"><fmt:message key="category_jsp.form.min_price"/>:</label>
                            <input type="number" min="0" max="10000" required name="price_min" id="price_min"
                                   class="form-control" value="${requestScope.priceMin}">
                            <label for="price_max"><fmt:message key="category_jsp.form.max_price"/>:</label>
                            <input type="number" min="1" max="10000" required name="price_max" id="price_max"
                                   class="form-control" value="${requestScope.priceMax}">
                        </div>
                    </li>
                </ul>
            </div>
            <div class="card mb-2">
                <input type="submit" class="btn btn-outline-secondary" value='<fmt:message key="category_jsp.form.apply"/>'>
            </div>
            <input type="number" value="${requestScope.category.id}" name="id" aria-label="id" hidden>
            <input type="number" value="${requestScope.currentPage}" name="page" aria-label="page" hidden>
        </form>
        <div class="col">
            <div class="row row-cols-sm-2 row-cols-md-3 row-cols-xl-4">
                <c:forEach var="item" items="${requestScope.category.products.content}">
                    <c:url value="<%= Paths.VIEW_PRODUCT %>" var="prodUrl">
                        <c:param name="id" value="${item.id}"/>
                    </c:url>
                    <div class="mb-4">
                        <div class="card shadow-sm">
                            <img src="<%= Paths.IMAGES %>/${not empty item.imageUrl ? item.imageUrl : 'default.jpg'}"
                                 class="card-img-top img-fluid"
                                 alt="image of the product">
                            <div class="card-body p-2">
                                <h5 class="card-title h5 pb-2">${item.title}</h5>
                                <p class="card-text">${fn:substring(item.description, 0, 100)}...</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item p-2"><fmt:message
                                        key="product.param.color"/>: ${item.color}</li>
                                <li class="list-group-item p-2"><fmt:message
                                        key="product.param.weight"/>: ${item.weight}</li>
                            </ul>
                            <div class="card-body p-2 d-flex justify-content-between align-items-baseline">
                                <div>
                                    <a href="${prodUrl}" class="btn btn-primary">
                                        <fmt:message key="category_jsp.item.button"/>
                                    </a>
                                </div>
                                <p class="fw-bold mx-auto">$ ${item.price}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty requestScope.category.products.content}">
                    <p><fmt:message key="category_jsp.message.no_found"/></p>
                </c:if>
            </div>
        </div>
    </div>

    <nav class="my-5">
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${requestScope.category.products.totalPages}" varStatus="loop">
                <li class="page-item ${loop.index eq requestScope.currentPage ? 'active' : ''}">
                    <a href="<my:replaceParam name='page' value='${loop.index}'/>" class="page-link">
                            ${loop.index}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</main>
</body>
</html>
