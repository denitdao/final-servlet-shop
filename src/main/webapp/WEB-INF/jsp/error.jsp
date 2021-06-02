<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="error_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <div class="col mx-5 mt-4 mb-5">
        <h3 class="h3"><fmt:message key="error_jsp.h3"/></h3>
    </div>
    <c:if test="${not empty requestScope.errorMessage}">
        <pre class="h5 mt-3 mb-5">${requestScope.errorMessage}</pre>
    </c:if>
    <c:if test="${not empty sessionScope.errorMessage}">
        <pre class="h5 mt-3 mb-5">${sessionScope.errorMessage}</pre>
    </c:if>
</main>

<c:remove var="errorMessage" scope="session"/>
</body>
</html>
