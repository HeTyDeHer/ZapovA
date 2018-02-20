<%@ page import="common.User" %>
<%@ page import="common.UserStoreJDBC" %><%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 19.02.2018
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <title>Show All</title>
</head>
<body>
<table border='1' align='center' width=60% style="border-spacing: 0">
        <%  for (User u : UserStoreJDBC.getInstance().getAll()) {%>
    <tr><td width='22%'>
        <%=u.getLogin()%>
    </td><td width='22%'>
        <%=u.getName()%>
    </td><td width='22%'>
        <%=u.getEmail()%>
    </td><td width='22%'>
        <%=u.getCreated()%>
    </td><td align='right' style="border-right:0 " width="6%">
        <form action='<%=request.getContextPath()%>/updatejsp' method=get>
            <input type='submit'  name='submit' value='Edit'/>
            <input type='hidden'  name='login' value='<%=u.getLogin()%>'/>
            <input type='hidden'  name='name'  value='<%=u.getName()%>'/>
            <input type='hidden'  name='email'  value='<%=u.getEmail()%>'/>
        </form>
    </td><td style="border-left:0">
        <form action='<%=request.getContextPath()%>/deletejsp' method=post>
            <input type='submit'  name='submit' value='Delete'/>
            <input type='hidden'  name='login' value='<%=u.getLogin()%>'/>
        </form>
    </td></tr>
        <%}%>
    <tr><form action='showjsp' method=post>
        <td><input type ='text' placeholder='login' name = 'login' required></td>
        <td><input type ='text' placeholder='name' name = 'name' required></td>
        <td><input type ='text' placeholder='email' name = 'email' required></td>
        <td></td>
        <td colspan="2" align='center'><input type ='submit' value='Add New'></td>
    </form></tr></table>
</body>
</html>
