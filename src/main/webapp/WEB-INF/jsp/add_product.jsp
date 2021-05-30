<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>Add Product</title>
</head>
<body>

<h3>Add item to ${requestScope.category.title}</h3>
<c:if test="${empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>">Login</a><br>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <a href="${pageContext.request.contextPath}<%= Paths.POST_LOGOUT %>">Logout</a><br>
</c:if>

<form action="<%= Paths.POST_ADD_PRODUCT %>" method="post">
    Назва (uk): <input type="text" name="title_uk" value="${sessionScope.prev_params.get('title_uk')}"><br>
    Title (en): <input type="text" name="title_en" value="${sessionScope.prev_params.get('title_en')}"><br>
    Опис (uk): <input type="text" name="description_uk" value="${sessionScope.prev_params.get('description_uk')}"><br>
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
    Price: <input type="number" name="price" value="${sessionScope.prev_params.get('price')}"><br>
<%--    Weight: <input type="number" name="weight" value="${sessionScope.prev_params.get('weight')}"><br>--%>
    Height: <input type="number" name="height" value="${sessionScope.prev_params.get('height')}"><br>
    <input type="number" value="${requestScope.category.id}" name="category_id" hidden>
    <input type="submit" value='Add'>
</form>
<c:if test="${not empty sessionScope.errorMessage}">
    <h3>Error message: ${sessionScope.errorMessage}</h3>
</c:if>
<c:remove var="prev_params" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
