<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="update_product_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">

    <h3 class="h3 m-5"><fmt:message key="update_product_jsp.title"/></h3>
    <div class="form-product">
        <form action="<%= Paths.POST_UPDATE_PRODUCT %>" method="post">
            <div class="form-text mt-4 mb-2 text-center"><fmt:message key="add_product_jsp.label.general"/></div>
            <c:choose>
                <c:when test="${empty sessionScope.prev_params}">
                    <div class="input-group mb-3">
                        <span class="input-group-text">Назва товару</span>
                        <input type="text" name="title_uk" value="${requestScope.product.get('uk').title}"
                               maxlength="40" class="form-control" placeholder="Назва товару" aria-label="Назва товару">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Title</span>
                        <input type="text" name="title_en" value="${requestScope.product.get('en').title}"
                               maxlength="40" class="form-control" placeholder="Title" aria-label="Title">
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Опис товару</span>
                        <textarea class="form-control" name="description_uk" maxlength="300"
                                  aria-label="Опис товару">${requestScope.product.get('uk').description}</textarea>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Description</span>
                        <textarea class="form-control" name="description_en" maxlength="300"
                                  aria-label="Description">${requestScope.product.get('en').description}</textarea>
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Колір</span>
                        <input type="text" name="color_uk" value="${requestScope.product.get('uk').color}"
                               maxlength="20" class="form-control" placeholder="Колір" aria-label="Колір">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Color</span>
                        <input type="text" name="color_en" value="${requestScope.product.get('en').color}"
                               maxlength="20" class="form-control" placeholder="Color" aria-label="Color">
                    </div>

                    <hr>
                    <div class="form-text mt-4 mb-2 text-center">
                        <fmt:message key="add_product_jsp.label.specific"/>
                    </div>
                    <c:forEach var="local_product" items="${requestScope.product}">
                        <c:forEach var="property" items="${local_product.value.properties}">
                            <c:set var="prop_name" value="cp_${property.key.id}_${local_product.key}"/>
                            <div class="input-group mb-3">
                                <span class="input-group-text">${property.key.title}</span>
                                <input type="${property.key.dataType}" name="${prop_name}"
                                       value="${property.value}"
                                       maxlength="100" class="form-control" placeholder="${property.key.title}"
                                       aria-label="${property.key.title}">
                            </div>
                        </c:forEach>
                    </c:forEach>

                    <hr>
                    <div class="form-text mt-4 mb-2 text-center"><fmt:message key="add_product_jsp.label.common"/></div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="product.param.height"/></span>
                        <input type="number" name="height" value="${requestScope.product.get('en').height}"
                               class="form-control"
                               placeholder="<fmt:message key="product.param.height"/>"
                               maxlength="10" step='0.01' aria-label="<fmt:message key="product.param.height"/>">
                        <span class="input-group-text">.00</span>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">$</span>
                        <input type="number" name="price" value="${requestScope.product.get('en').price}"
                               class="form-control"
                               placeholder="<fmt:message key="product.param.price"/>"
                               maxlength="10" step='0.01' aria-label="<fmt:message key="product.param.price"/>">
                        <span class="input-group-text">.00</span>
                    </div>

                </c:when>
                <c:otherwise>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Назва товару</span>
                        <input type="text" name="title_uk" value="${sessionScope.prev_params.get('title_uk')}"
                               maxlength="40" class="form-control" placeholder="Назва товару" aria-label="Назва товару">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Title</span>
                        <input type="text" name="title_en" value="${sessionScope.prev_params.get('title_en')}"
                               maxlength="40" class="form-control" placeholder="Title" aria-label="Title">
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Опис товару</span>
                        <textarea class="form-control" name="description_uk" maxlength="300"
                                  aria-label="Опис товару">${sessionScope.prev_params.get('description_uk')}</textarea>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Description</span>
                        <textarea class="form-control" name="description_en" maxlength="300"
                                  aria-label="Description">${sessionScope.prev_params.get('description_en')}</textarea>
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">Колір</span>
                        <input type="text" name="color_uk" value="${sessionScope.prev_params.get('color_uk')}"
                               maxlength="20" class="form-control" placeholder="Колір" aria-label="Колір">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Color</span>
                        <input type="text" name="color_en" value="${sessionScope.prev_params.get('color_en')}"
                               maxlength="20" class="form-control" placeholder="Color" aria-label="Color">
                    </div>
                    <hr>
                    <div class="form-text mt-4 mb-2 text-center">
                        <fmt:message key="add_product_jsp.label.specific"/>
                    </div>
                    <c:forEach var="local_product" items="${requestScope.product}">
                        <c:forEach var="property" items="${local_product.value.properties}">
                            <c:set var="prop_name" value="cp_${property.key.id}_${local_product.key}"/>
                            <div class="input-group mb-3">
                                <span class="input-group-text">${property.key.title}</span>
                                <input type="${property.key.dataType}" name="${prop_name}"
                                       value="${sessionScope.prev_params.get(prop_name)}"
                                       maxlength="100" class="form-control" placeholder="${property.key.title}"
                                       aria-label="${property.key.title}">
                            </div>
                        </c:forEach>
                    </c:forEach>

                    <hr>
                    <div class="form-text mt-4 mb-2 text-center"><fmt:message key="add_product_jsp.label.common"/></div>
                    <div class="input-group mb-3">
                        <span class="input-group-text"><fmt:message key="product.param.height"/></span>
                        <input type="number" name="height" value="${sessionScope.prev_params.get('height')}"
                               class="form-control"
                               placeholder="<fmt:message key="product.param.height"/>"
                               maxlength="10" step='0.01' aria-label="<fmt:message key="product.param.height"/>">
                        <span class="input-group-text">.00</span>
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">$</span>
                        <input type="number" name="price" value="${sessionScope.prev_params.get('price')}"
                               class="form-control"
                               placeholder="<fmt:message key="product.param.price"/>"
                               maxlength="10" step='0.01' aria-label="<fmt:message key="product.param.price"/>">
                        <span class="input-group-text">.00</span>
                    </div>

                </c:otherwise>
            </c:choose>
            <input type="number" value="${requestScope.product.get('en').id}" name="id"
                   aria-label="id" hidden>
            <input type="submit" class="btn btn-primary mb-5" value='<fmt:message key="update_product_jsp.title"/>'>

            <c:if test="${not empty sessionScope.errorMessage}">
                <pre class="h5 mb-5">${sessionScope.errorMessage}</pre>
            </c:if>
        </form>
    </div>
</main>


<c:remove var="prev_params" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
