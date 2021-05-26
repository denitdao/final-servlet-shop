<%@ page import="com.example.final_project_demo.util.Paths" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>About</title>
</head>
<body>
<h2>THIS PAGE IS FOR USER AND ADMIN</h2>
<h3>Hello user (or admin): ${sessionScope.getOrDefault("user", "(not logged in)")}</h3>
<a href="${pageContext.request.contextPath}<%= Paths.VIEW_HOME %>">Home page (all)</a><br></body>
</html>
