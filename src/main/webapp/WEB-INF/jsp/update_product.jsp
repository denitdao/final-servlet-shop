<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>Update Product</title>
</head>
<body>

<h3>Update item</h3>
<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>
<c:catch var="exceptionThrown">

    <form action="<%= Paths.POST_UPDATE_PRODUCT %>" method="post">
        <c:choose>
            <c:when test="${empty sessionScope.prev_params}">
                Назва (uk): <input type="text" name="title_uk" value="${requestScope.product.get('uk').title}"><br>
                Title (en): <input type="text" name="title_en" value="${requestScope.product.get('en').title}"><br>
                Опис (uk): <input type="text" name="description_uk"
                                  value="${requestScope.product.get('uk').description}"><br>
                Description (en): <input type="text" name="description_en"
                                         value="${requestScope.product.get('en').description}"><br>
                Колір (uk): <input type="text" name="color_uk"
                                   value="${requestScope.product.get('uk').color}"><br>
                Color (en): <input type="text" name="color_en"
                                   value="${requestScope.product.get('en').color}"><br>
                <c:forEach var="local_product" items="${requestScope.product}">
                    <c:forEach var="property" items="${local_product.value.properties}">
                        <c:set var="prop_name" value="cp_${property.key.id}_${local_product.key}"/>
                        ${property.key.title} (${local_product.key}): <input type="${property.key.dataType}"
                                                                             name="${prop_name}"
                                                                             value="${property.value}"><br>
                    </c:forEach>
                </c:forEach>
                Price: <input type="number" name="price" value="${requestScope.product.get('en').price}"><br>
                Height: <input type="number" name="height" value="${requestScope.product.get('en').height}"><br>
                <input type="number" value="${requestScope.product.get('en').id}" name="id" hidden>
            </c:when>
            <c:otherwise>
                Назва (uk): <input type="text" name="title_uk" value="${sessionScope.prev_params.get('title_uk')}"><br>
                Title (en): <input type="text" name="title_en" value="${sessionScope.prev_params.get('title_en')}"><br>
                Опис (uk): <input type="text" name="description_uk"
                                  value="${sessionScope.prev_params.get('description_uk')}"><br>
                Description (en): <input type="text" name="description_en"
                                         value="${sessionScope.prev_params.get('description_en')}"><br>
                Колір (uk): <input type="text" name="color_uk" value="${sessionScope.prev_params.get('color_uk')}"><br>
                Color (en): <input type="text" name="color_en" value="${sessionScope.prev_params.get('color_en')}"><br>
                <c:forEach var="property" items="${requestScope.category.categoryProperties}">
                    <c:set var="prop_name" value="cp_${property.id}_${property.locale}"/>

                    <%--  add cutom tag to display int or string parameters  --%>
                    ${property.title} (${property.locale}): <input type="${property.dataType}"
                                                                   name="${prop_name}"
                                                                   value="${sessionScope.prev_params.get(prop_name)}"><br>
                </c:forEach>
                <c:forEach var="local_product" items="${requestScope.product}">
                    <c:forEach var="property" items="${local_product.value.properties}">
                        <c:set var="prop_name" value="cp_${property.key.id}_${local_product.key}"/>
                        ${property.key.title} (${local_product.key}): <input type="${property.key.dataType}"
                                                                             name="${prop_name}"
                                                                             value="${sessionScope.prev_params.get(prop_name)}"><br>
                    </c:forEach>
                </c:forEach>
                Price: <input type="number" name="price" value="${sessionScope.prev_params.get('price')}"><br>
                Height: <input type="number" name="height" value="${sessionScope.prev_params.get('height')}"><br>
                <input type="number" value="${requestScope.product.get('en').id}" name="id" hidden>
            </c:otherwise>
        </c:choose>
        <input type="submit" value='Update'>
    </form>

</c:catch>

<c:if test="${exceptionThrown != null}">
    <p>The exception is : ${exceptionThrown} <br/>
        There is an exception: ${exceptionThrown.message}
    </p>
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
    <h3>Error message: ${sessionScope.errorMessage}</h3>
</c:if>
<c:remove var="prev_params" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
