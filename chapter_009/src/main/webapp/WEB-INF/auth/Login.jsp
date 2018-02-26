<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 24.02.2018
  Time: 0:15
  1. Реализовать авторизация и аутентификацию [#2517].
  Login Page.
  If already logged in, show username and logout button.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorisation</title>
</head>
<body align="center">
<c:if test="${not empty sessionScope.user.login}"><h2><c:out value="You were logged in as ${sessionScope.user.login}"/></h2>
    <h3 style="color: red"><c:out value="${error}"/></h3>
    <form action='${pageContext.servletContext.contextPath}/auth/logout' method="post">
        <input type="submit" value="Log out" align="center"/>
    </form>
</c:if>
<c:if test="${empty sessionScope.user.login}"><h2> Enter login and password </h2>
<h3 style="color: red"><c:out value="${error}"/></h3>
<form action='${pageContext.servletContext.contextPath}/auth/login' method="post">
    <input type="text" name="login" placeholder="login"/>
    <input type="password" name="password" placeholder="password"/><br/>
    <input type="checkbox" name="remember" value="true">Remember?
    <br/>
    <br/>
    <input type="submit" value="Log In"/>
</form>
</c:if>
</body>
</html>
