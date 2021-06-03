<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="all_users_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <div class="col mx-5 mt-4 mb-5">
        <h3 class="h3"><fmt:message key="all_users_jsp.title"/></h3>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="all_users_jsp.table.id"/></th>
            <th scope="col"><fmt:message key="all_users_jsp.table.first_name"/></th>
            <th scope="col"><fmt:message key="all_users_jsp.table.second_name"/></th>
            <th scope="col"><fmt:message key="all_users_jsp.table.login"/></th>
            <th scope="col"><fmt:message key="all_users_jsp.table.action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <c:url value="<%= Paths.VIEW_ALL_ORDERS %>" var="ordersUrl">
                <c:param name="user_id" value="${user.id}"/>
            </c:url>
            <tr>
                <th scope="row"><a href="${ordersUrl}">${user.id}</a></th>
                <td>${user.firstName}</td>
                <td>${user.secondName}</td>
                <td>${user.login}</td>
                <td>
                    <form action="<%= Paths.POST_EDIT_USER %>" method="post">
                        <input type="number" name="id" aria-label="id" value="${user.id}" hidden>
                        <c:if test="${user.blocked}">
                            <input type="number" name="block" aria-label="block" value="0" hidden>
                            <input type="submit" class="btn btn-outline-danger btn-sm" value='<fmt:message key="all_users_jsp.form.unblock"/>'>
                        </c:if>
                        <c:if test="${not user.blocked}">
                            <input type="number" name="block" aria-label="block" value="1" hidden>
                            <input type="submit" class="btn btn-outline-success btn-sm" value='<fmt:message key="all_users_jsp.form.block"/>'>
                        </c:if>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

</body>
</html>
