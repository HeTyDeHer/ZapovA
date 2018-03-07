<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 19.02.2018
  Time: 1:29
  1. Реализовать авторизация и аутентификацию [#2517].
  Start page.
  Show all users, guest and user view.
  User has access to modification his own data (except creating date & role).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Show All</title>
    <style>
        .auth {
            border: solid black 2px;
            border-radius: 10px;
            background: lightgrey;
            position: absolute;
            top: 5px;
            right: 5px;
            padding: 5px;
        }
        table {
            border: 2px solid black;
            border-collapse: collapse;
            margin: auto;
            width: 60%;
        }
        td {
            padding-left: 5px;
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h2 align="center">All users, Auth test</h2>

<table>
    <c:forEach var="user" items="${users}">
        <tr>
            <td width='22%'>
                <c:out value="${user.login}"/>
            </td>
            <td width='22%'>
                <c:out value="${user.name}"/>
            </td>
            <td width='22%'>
                <c:out value="${user.email}"/>
            </td>
            <td width='25%'>
                <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${user.created}" />
            </td>
            <td width='9%'>
                <c:out value="${user.role}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${empty sessionScope.user}">
    <div class="auth">
        <form action='${pageContext.servletContext.contextPath}/auth/login' align="center" method="post">
            <input type="text" name="login" placeholder="login" align="center"/><br/>
            <input type="password" name="password" placeholder="password" align="center"/><br/>
            <input type="checkbox" name="remember" value="true">Remember?<br/>
            <input type="submit" value="Log in" />
        </form>
    </div>
</c:if>
<c:if test="${not empty sessionScope.user}">
    <div class="auth">
        <c:out value="Hello, ${sessionScope.user.login}!  "/> <br/>
        <a href='${pageContext.servletContext.contextPath}/auth/edit' align="center">Edit profile</a>
        <form action='${pageContext.servletContext.contextPath}/auth/logout' align="center" method="post" >
            <input type="submit" value="Log out"/>
        </form>
    </div>
</c:if>

</body>
</html>
