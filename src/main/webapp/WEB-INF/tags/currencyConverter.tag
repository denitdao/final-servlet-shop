<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="value" required="true" type="java.lang.String" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>

<c:choose>
    <c:when test="${sessionScope.locale eq 'uk'}">
        ${value * 27}${' '}<fmt:message key="currency"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="currency"/>${value}
    </c:otherwise>
</c:choose>