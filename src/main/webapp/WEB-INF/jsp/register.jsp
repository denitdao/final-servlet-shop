<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>Register</title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>
<main class="text-center container">
    <div class="form-register">
        <form action="<%= Paths.POST_REGISTER %>" method="post">
            <h1 class="h1 mb-3 fw-normal"><fmt:message key="register_jsp.h1"/></h1>
            <div class="mb-3">
                <label for="firstName" class="form-label">
                    <fmt:message key="register_jsp.label.first_name"/>
                </label>
                <input type="text" id="firstName" name="firstName" value="${sessionScope.prev_params.get('firstName')}"
                       class="form-control" minlength="3">
            </div>
            <div class="mb-3">
                <label for="secondName" class="form-label">
                    <fmt:message key="register_jsp.label.second_name"/>
                </label>
                <input type="text" id="secondName" name="secondName"
                       value="${sessionScope.prev_params.get('secondName')}"
                       class="form-control" minlength="3">
            </div>
            <div class="mb-3">
                <label for="login" class="form-label">
                    <fmt:message key="register_jsp.label.login"/>
                </label>
                <input type="text" id="login" name="login" value="${sessionScope.prev_params.get('login')}"
                       class="form-control" minlength="3">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">
                    <fmt:message key="register_jsp.label.password"/>
                </label>
                <input type="password" id="password" name="password" class="form-control" minlength="3">
            </div>
            <div class="mb-3">
                <input type="submit" class="btn btn-primary"
                       value='<fmt:message key="register_jsp.button.register"/>'>
            </div>
            <a href="${pageContext.request.contextPath}<%= Paths.VIEW_LOGIN %>" class="link-primary">
                <fmt:message key="login_jsp.title"/>
            </a>
        </form>
    </div>
    <%@ include file="/WEB-INF/parts/error_message.jspf" %>
</main>

<c:remove var="prev_params" scope="session"/>
</body>
</html>
