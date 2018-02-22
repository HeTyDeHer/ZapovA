<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 19.02.2018
  Time: 1:29
  Start page.
  Show all users and add new.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Show All</title>
    <style>
        .colortext {
            background-color: #ADFF2F;
        }
        table{
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
<h2 align="center">All users, JSTL test</h2>
<c:set var="loginReq" value="${param.login}"/>
<c:set var="name" value="${param.name}"/>
<c:set var="email" value="${param.email}"/>

<table>
    <c:forEach var="user" items="${users}">
        <c:if test="${loginReq != user.login}">
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
                <td width='22%'>
                    <c:out value="${user.created}"/>
                </td>
                <form action='${pageContext.servletContext.contextPath}' method=get>
                    <td align='right' style="border-right:0 ">
                        <input style="width: 100%" type='submit'  name='submit' value='Edit'/>
                        <input type='hidden'  name='login' value='${user.login}'/>
                        <input type='hidden'  name='name'  value='${user.name}'/>
                        <input type='hidden'  name='email'  value='${user.email}'/>
                    </td>
                </form>

                <form action='${pageContext.servletContext.contextPath}/deletejstl' method=post>
                    <td style="border-left:0">
                        <input style="width: 100%" type='submit'  name='submit' value='Delete'/>
                        <input type='hidden'  name='login' value='${user.login}'/>
                    </td>
                </form>
            </tr>
        </c:if>
        <c:if test="${loginReq == user.login}">
            <tr>
                <form action='${pageContext.servletContext.contextPath}/updatejstl' method=post>
                    <td width='22%'>
                        <c:out value="${loginReq}"/>
                        <input type='hidden'  name='login' value='${loginReq}'/>
                    </td>
                    <td width='22%'>
                        <input type='text' class='colortext' name='name'  value='${name}'/>
                    </td>
                    <td width='22%'>
                        <input type='text' class='colortext' name='email'  value='${email}'/>
                    </td>
                    <td width='22%'>
                        <c:out value="${user.created}"/>
                    </td>
                    <td colspan="2" align='center'>
                        <input style="width: 100%" type='submit'  name='submit' value='Confirm'/>
                    </td>
                </form>
            </tr>
        </c:if>
    </c:forEach>

    <tr><form action='${pageContext.servletContext.contextPath}/' method=post>
        <td><input type ='text' placeholder='login' name = 'login' required></td>
        <td><input type ='text' placeholder='name' name = 'name' required></td>
        <td><input type ='text' placeholder='email' name = 'email' required></td>
        <td></td>
        <td colspan="2" align='center'><input type ='submit' value='Add New'></td>
    </form></tr></table>
</body>
</html>
