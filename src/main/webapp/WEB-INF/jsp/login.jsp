<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="login_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>
<main class="text-center container">
    <div class="form-login">
        <form action="<%= Paths.POST_LOGIN %>" method="post">
            <h1 class="h1 mb-3 fw-normal"><fmt:message key="login_jsp.h1"/></h1>
            <div class="mb-3">
                <label for="login" class="form-label">
                    <fmt:message key="login_jsp.label.login"/>
                </label>
                <input type="text" id="login" name="login" value="${sessionScope.prev_params.get('login')}"
                       class="form-control">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">
                    <fmt:message key="login_jsp.label.password"/>
                </label>
                <input type="password" id="password" name="password" class="form-control">
            </div>
            <div class="mb-3">
                <input type="submit" class="btn btn-primary"
                       value='<fmt:message key="login_jsp.button.login"/>'>
            </div>
            <a href="${pageContext.request.contextPath}<%= Paths.VIEW_REGISTER %>" class="link-primary">
                <fmt:message key="register_jsp.title"/>
            </a>

        </form>
    </div>
    <c:if test="${not empty sessionScope.errorMessage}">
        <pre class="h5 mb-5">${sessionScope.errorMessage}</pre>
    </c:if>
</main>

<c:remove var="errorMessage" scope="session"/>
<c:remove var="prev_params" scope="session"/>
</body>
</html>