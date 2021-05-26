<%@ page import="com.example.final_project_demo.util.Paths" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks</title>
</head>
<body>
<h2>PAGE FOR ADMIN ONLY:</h2>
<h3>Hello admin: ${sessionScope.getOrDefault("user", "(not logged in)")}</h3>
<a href="${pageContext.request.contextPath}<%= Paths.VIEW_HOME %>">Home page (all)</a><br>
</body>
</html>
