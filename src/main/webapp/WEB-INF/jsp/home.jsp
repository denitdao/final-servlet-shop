<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="home_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <div class="col mx-5 mt-4 mb-5">
        <h3 class="h3"><fmt:message key="home_jsp.h3"/></h3>
    </div>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 m-md-2">
        <c:forEach var="category" items="${requestScope.categories}">
            <c:url value="<%= Paths.VIEW_CATEGORY %>" var="catUrl">
                <c:param name="id" value="${category.id}"/>
            </c:url>
            <div class="col mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="h5 card-title">${category.title}</h5>
                        <p class="card-text">${category.description}</p>
                        <a href="${catUrl}" class="btn btn-primary"><fmt:message key="home_jsp.item.button"/></a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>
</body>
</html>
