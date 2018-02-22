<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 19.02.2018
  Time: 0:45
  Add Error (login already exists)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body align="center">
<h2><c:out value="${empty param.login ? 'Enter login!' : 'Login already exists'}" /></h2>
<input type='button' onclick='history.back()' value='Back'/>
</body>
</html>

