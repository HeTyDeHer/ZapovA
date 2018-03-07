<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 19.02.2018
  Time: 1:29
  1. Реализовать авторизация и аутентификацию [#2517].
  Start page.
  Show all users and add new.
  Only admin access.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Show All</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <script>
        function loadCity(select, place, selected) {
            $(place).html('');
            $.get('${pageContext.servletContext.contextPath}/auth/getcity?country=' + select, function(response) {
                $.each(response, function(number, item) {
                    if (item == selected) {
                        $("<option selected>").text(item).appendTo($(place));
                    } else {
                        $("<option>").text(item).appendTo($(place));
                    }
                });
            });
        }
    </script>
    <script>
        function validateLogin() {
            var login = document.getElementById("addlogin").value;
            $.get('${pageContext.servletContext.contextPath}/auth/add?login=' + login, function(response) {
                if(response == "invalid") {
                    document.getElementById('addlogin').setCustomValidity("login already exists");
                } else {
                    document.getElementById('addlogin').setCustomValidity('');
                }
            });
        }
    </script>
    <script>

        function validatePassword(password1, password2){
            var pass1 = document.getElementById(password1).value;
            var pass2 = document.getElementById(password2).value;
            if(pass1 != pass2) {
                document.getElementById(password2).setCustomValidity("Passwords Don't Match");}
            else {
                document.getElementById(password2).setCustomValidity(''); }
        }
    </script>
    <style>
        input:not(:placeholder-shown):invalid {
            background: orangered;
        }
        input:valid {
            background-color: lightgreen;
        }
        input[type = "submit"]:valid {
            background-color: ButtonFace;
        }
        table{
            border: 2px solid black;
            border-collapse: collapse;
            margin: auto;
            width: 80%;
        }
        td {
            padding-left: 5px;
            border: 1px solid black;
        }
        .auth {
            border: solid black 2px;
            border-radius: 10px;
            background: lightgrey;
            position: absolute;
            top: 5px;
            right: 5px;
            padding: 5px;
        }
    </style>

</head>
<body>
<h2 align="center">All users, Auth Test (admin)</h2>
<c:set var="loginReq" value="${param.login}"/>

<table>
    <c:forEach var="user" items="${users}">
        <c:if test="${loginReq != user.login}">
            <tr>
                <td width='11%'>
                    <c:out value="${user.login}"/>
                </td>
                <td width='11%'>
                    <c:out value="********"/>
                </td>
                <td width='11%'>
                    <c:out value="${user.name}"/>
                </td>
                <td width='11%'>
                    <c:out value="${user.email}"/>
                </td>
                <td width='11%'>
                    <c:out value="${user.country}"/>
                </td>
                <td width='11%'>
                    <c:out value="${user.city}"/>
                </td>
                <td width='11%'>
                    <c:out value="${user.role}"/>
                </td>
                <td width='11%'>
                    <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${user.created}" />
                </td>
                <form action='${pageContext.servletContext.contextPath}/auth' method='get'>
                    <td align='right' style="border-right:0 " width="6%">
                        <input style="width: 100%" type='submit'  name='submit' value='Edit'/>
                        <input type='hidden'  name='login' value='${user.login}'/>
                    </td>
                </form>
                <form action='${pageContext.servletContext.contextPath}/auth/delete' method="post">
                    <td style="border-left:0"  width="6%">
                        <input style="width: 100%" type='submit'  name='submit' value='Delete'/>
                        <input type='hidden'  name='login' value='${user.login}'/>
                    </td>
                </form>
            </tr>
        </c:if>
        <c:if test="${loginReq == user.login}">
            <tr>
                <form action='${pageContext.servletContext.contextPath}/auth/edit' method="post">
                    <td  width='11%'>
                        <c:out value="${loginReq}"/>
                        <input type='hidden'  name='login' value='${loginReq}'/>
                    </td>
                    <td  width='11%'>
                        <input type='password' id="password1" onchange="validatePassword('password1', 'password1rep')" name='password' value="${user.password}" required />
                        <input type='password' id="password1rep" oninput="validatePassword('password1', 'password1rep')" name='passwordrep' value="${user.password}" required />
                    </td>>
                    <td width='11%'>
                        <input type='text' class='colortext' name='name'  value='${user.name}' required />
                    </td>
                    <td width='11%'>
                        <input type='email' class='colortext' name='email'  value='${user.email}' required />
                    </td>
                    <td>
                        <select name='country' id="chooseCountryEd" onclick="loadCity(this.options[selectedIndex].value, '#chooseCityEd', '${user.city}')" required>
                            <c:forEach var="countr" items="${countries}">
                                <c:choose>
                                    <c:when test="${countr == user.country}">
                                        <option selected>${countr}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option>${countr}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name='city' id="chooseCityEd" required>
                            <option>${user.city}</option>
                        </select>
                    </td>
                    <td width='11%'>
                        <select name="role">
                            <option value="${user.role}"><c:out value="${user.role}"/></option>
                            <c:choose>
                                <c:when test="${user.role == 'user'}">
                                    <option value="admin"><c:out value="admin"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="user"><c:out value="user"/></option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </td>
                    <td width='11%'>
                        <c:out value="${user.created}"/>
                    </td>
                    <td colspan="2" align='center'>
                        <input style="width: 100%" type='submit'  name='submit' value='Confirm'/>
                    </td>
                </form>
            </tr>
        </c:if>
    </c:forEach>

    <tr>
        <form action='${pageContext.servletContext.contextPath}/auth/add' method="post">
            <td>
                <input type ='text' placeholder='login' name = 'login' id="addlogin" onblur="validateLogin()" required>
            </td>
            <td>
                <input type ='password' placeholder='password' id="password2" name = 'password' onchange="validatePassword('password2', 'password2rep')" required /><br/>
                <input type ='password' placeholder='repeat password' id="password2rep" name = 'passwordrep' oninput="validatePassword('password2', 'password2rep')" required/>
            </td>
            <td>
                <input type ='text' placeholder='name' name = 'name' required>
            </td>
            <td>
                <input type ='email' placeholder='email' name = 'email' required>
            </td>
            <td>
                <select name='country' id="chooseCountry" onchange="loadCity(this.options[selectedIndex].value, '#chooseCity')" required>
                    <option value="" disabled selected>Выберите страну</option>
                    <c:forEach var="countr" items="${countries}">
                        <option><c:out value="${countr}" /></option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select name='city' id="chooseCity">
                    <option>Сначала выберите страну...</option>
                </select>
            </td>
            <td>
                <select name="role">
                    <option value="user"><c:out value="user"/></option>
                    <option value="admin"><c:out value="admin"/></option>
                </select>
            </td>
            <td colspan="3" align='center'><input type ='submit' value='Add New' />
        </form>
    </tr>
</table>
<div class="auth">
    <c:out value="Hello, ${sessionScope.user.login}!  "/>
    <form action='${pageContext.servletContext.contextPath}/auth/logout' align="center" method="post" >
        <input type="submit" value="Log out"/>
    </form>
</div>
</body>
</html>
