<%@ page import="ua.denitdao.servlet.shop.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Landing page</title>
</head>
<body>
    <p>moving you to the home page of the site</p>
    <c:redirect url="<%= Paths.VIEW_HOME %>"/>
</body>
</html>
