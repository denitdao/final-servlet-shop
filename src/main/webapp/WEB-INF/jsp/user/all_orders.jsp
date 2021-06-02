<%@ page import="ua.denitdao.servlet.shop.util.Paths, java.sql.Timestamp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="all_orders_jsp.title"/></title>
    <%@ include file="/WEB-INF/parts/head_tags.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jspf" %>

<main class="container">
    <div class="col mx-5 mt-4 mb-5">
        <h3 class="h3"><fmt:message key="all_orders_jsp.title"/></h3>
    </div>

    <div class="row m-md-2">
        <c:forEach var="order" items="${requestScope.orders}">
            <c:url value="<%= Paths.VIEW_ORDER %>" var="orderUrl">
                <c:param name="id" value="${order.id}"/>
            </c:url>
            <div class="col-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-header fw-bold">
                        <fmt:message key="all_orders_jsp.item.header"/> #${order.id}
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><fmt:message key="all_orders_jsp.item.status"/>: <fmt:message
                                key="status.${order.status}"/>
                        </li>
                        <li class="list-group-item"><fmt:message key="all_orders_jsp.item.update"/>: <fmt:formatDate
                                value="${Timestamp.valueOf(order.updatedAt)}"
                                type="date"
                                pattern="dd.MM.yyyy - HH:mm"/>
                        </li>
                        <li class="list-group-item"><fmt:message key="all_orders_jsp.item.create"/>: <fmt:formatDate
                                value="${Timestamp.valueOf(order.createdAt)}"
                                type="date"
                                pattern="dd.MM.yyyy - HH:mm"/>
                        </li>
                    </ul>
                    <c:if test="${sessionScope.role eq 'ADMIN'}">
                        <div class="card-footer w-100">
                            <a href="${orderUrl}" class="btn btn-primary w-100">
                                <fmt:message key="all_orders_jsp.item.button"/>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty requestScope.orders}">
        <p><fmt:message key="all_orders_jsp.message.empty"/></p>
    </c:if>
</main>
</body>
</html>
