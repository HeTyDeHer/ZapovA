<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 24.02.2018
  Time: 2:15
  1. Реализовать авторизация и аутентификацию [#2517]
  Edit user.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit own data</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        window.onload = function () {
            document.getElementById("password1").onchange = validatePassword;
            document.getElementById("password2").onchange = validatePassword;
        };

        function validatePassword(){
            var pass1 = document.getElementById("password1").value;
            var pass2 = document.getElementById("password2").value;
            if(pass1 != pass2)
                document.getElementById("password2").setCustomValidity("Passwords Don't Match");
            else
                document.getElementById("password2").setCustomValidity('');
        }
    </script>
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
    <style>
        input {
            width: 100%;
        }
        input:invalid {
            background: orangered;
        }
        input:valid {
            background-color: lightgreen;
        }
        input[type = "submit"]:valid {
            background-color: ButtonFace;
        }
        table{
            alignment: center;
            border: 2px solid black;
            border-spacing: 10px;
            margin: auto;
        }
        td {
            text-align: center;
        }
         select {
            width: 100%;
         }

    </style>
</head>
<body>
<h2 align="center"> Edit data </h2>
<form action='${pageContext.servletContext.contextPath}/auth/edit' method="post">
    <table>
        <tr>
            <td>
                Login
            </td>
            <td>
            <input type="hidden" name="login" value="${user.login}"/>
            <c:out value="${user.login}"/>
            </td>
        </tr>

        <tr>
            <td>
                Password
            </td>
            <td>
            <input type="password" id="password1" name="password" value="${user.password}" required/>
            </td>
        </tr>
        <tr>
            <td>
                Repeat password
            </td>
            <td>
            <input type="password" id="password2" name="repeatpassword" value="${user.password}" required/>
            </td>
        </tr>
        <tr>
            <td>
                Name
            </td>
            <td>
            <input type="text" name="name" value="${user.name}" required/>
            </td>
        </tr>
        <tr>
            <td>
                Email
            </td>
            <td>
            <input type="email" name="email" value="${user.email}" required/>
            </td>
        </tr>
        <tr>
            <td>
                Country
            </td>
            <td>
                <select name="country" id="chooseCountryEd" onclick="loadCity(this.options[selectedIndex].value, '#chooseCity', '${user.city}')" required>
                    <c:forEach var="country" items="${countries}">
                        <c:choose>
                            <c:when test="${country == user.country}">
                                <option selected>${country}</option>
                            </c:when>
                            <c:otherwise>
                                <option>${country}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                City
            </td>
            <td>
                <select name="city" id='chooseCity'>
                    <option>${user.city}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                Role
            </td>
            <td>
            <c:out value="${user.role}"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
            <input type="submit" value="Confirm"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
