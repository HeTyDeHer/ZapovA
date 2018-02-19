<%@ page import="common.User" %>
<%@ page import="common.UserStoreJDBC" %><%--
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
    </style>
</head>
<body>
<% String loginRequired = request.getParameter("login");
    String name = request.getParameter("name");
    String email = request.getParameter("email");%>
<table border='1' align='center' width=60%>

    <%  for (User u : UserStoreJDBC.getInstance().getAll()) {
        if (!loginRequired.equals(u.getLogin())) {%>
    <tr><td width='22%'>
        <%=u.getLogin()%>
    </td><td width='22%'>
        <%=u.getName()%>
    </td><td width='22%'>
        <%=u.getEmail()%>
    </td><td width='22%'>
        <%=u.getCreated()%>
    </td><td align='center'>
        <form action='<%=request.getContextPath()%>/updatejsp' method=get>
            <input type='submit'  name='submit' value='Edit'/>
            <input type='hidden'  name='login' value='<%=u.getLogin()%>'/>
            <input type='hidden'  name='name'  value='<%=u.getName()%>'/>
            <input type='hidden'  name='email'  value='<%=u.getEmail()%>'/>
        </form>
        <form action='<%=request.getContextPath()%>/deletejsp' method=post>
            <input type='submit'  name='submit' value='Delete'/>
            <input type='hidden'  name='login' value='<%=u.getLogin()%>'/>
        </form>
    </td></tr>
    <%} else {%>
    <tr><form action='<%=request.getContextPath()%>/updatejsp' method=post><td width='22%'>
        <%=loginRequired%>
        <input type='hidden'  name='login' value='<%=u.getLogin()%>'/>
    </td><td width='22%'>
        <input type='text' class='colortext' name='name'  value='<%=u.getName()%>'/>
    </td><td width='22%'>
        <input type='text' class='colortext' name='email'  value='<%=u.getEmail()%>'/>
    </td><td width='22%'>
        <%=u.getCreated()%>
    </td><td align='center'>
        <input type='submit'  name='submit' value='Confirm'/>
    </td>
    </form>
    </tr>
    <%}
    }%>
    <tr><form action="<%=request.getContextPath()%>/showjsp" method=post>
        <td><input type ='text' placeholder='login' name = 'login' required></td>
        <td><input type ='text' placeholder='name' name = 'name' required></td>
        <td><input type ='text' placeholder='email' name = 'email' required></td>
        <td></td>
        <td align='center'><input type ='submit' value='Add New'></td>
    </form></tr></table>
</body>
</html>
