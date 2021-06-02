<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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

<main class="container">
    <nav class="m-4" style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
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
        <div class="col mx-5 mt-4 mb-5">
            <h3 class="h3">${requestScope.category.title}</h3>
        </div>
        <c:if test="${sessionScope.role eq 'ADMIN'}">
            <div class="col-auto mx-5 mt-4 mb-5">
                <c:url value="<%= Paths.VIEW_ADD_PRODUCT %>" var="addProdUrl">
                    <c:param name="id" value="${requestScope.category.id}"/>
                </c:url>
                <a href="${addProdUrl}" class="btn btn-success"><fmt:message key="add_product_jsp.title"/></a>
            </div>
        </c:if>
    </div>

    <div class="row m-md-2">
        <c:forEach var="item" items="${requestScope.category.products}">
            <c:url value="<%= Paths.VIEW_PRODUCT %>" var="prodUrl">
                <c:param name="id" value="${item.id}"/>
            </c:url>
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="card shadow-sm">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/b/b3/Solid_gray.png"
                         class="card-img-top"
                         alt="image of the product">
                    <div class="card-body p-2">
                        <h5 class="card-title h5 pb-2">${item.title}</h5>
                        <p class="card-text">${fn:substring(item.description, 0, 100)}...</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item p-2"><fmt:message key="product.param.color"/>: ${item.color}</li>
                        <li class="list-group-item p-2"><fmt:message key="product.param.height"/>: ${item.height}</li>
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
    </div>

    <!--<nav>
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">«</span>
                </a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">»</span>
                </a>
            </li>
        </ul>
    </nav>-->
</main>
</body>
</html>
