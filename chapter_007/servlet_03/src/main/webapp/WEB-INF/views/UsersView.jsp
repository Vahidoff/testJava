<%@ page import="ru.job4j.crudservlet.UserCS" %>
<%@ page import="ru.job4j.userservlets.UsersDeposit" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--если убрать эту строчку то все ок--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset=\"UTF-8\">
    <title>JSP</title>
</head>
<body>
<h1>Here all users</h1>
<table border='3' style="background-color:#4dff78" width='100%'>
    <tr>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Date</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>

    <%
        List<String> allLogin = null;
        try {
            allLogin = UsersDeposit.getInstance().getAllLogin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (allLogin != null) {
            for (String login : allLogin) {
                UserCS user = null;
                try {
                    user = UsersDeposit.getInstance().getUserCS(login);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (user != null) {
    %>

    <tr>
        <td align="center"><%=user.getName()%>
        </td>
        <td align="center"><%=user.getLogin()%>
        </td>
        <td align="center"><%=user.getEmail()%>
        </td>
        <td align="center"><%=user.getCreateDate()%>
        </td>
        <td align="center">
            <form><input type='button' value='Rewrite user'
                         onclick="window.location.href='update?login=<%=user.getLogin()%>'"/>
            </form>
        </td>
        <td align="center">
            <form><input type='button' value='Delete'
                         onclick="window.location.href='delete?login=<%=user.getLogin()%>'"/>
            </form>
        </td>

    </tr>
    <% } %>
    <% } %>
    <% } %>
</table>
<h1>Add new user</h1>
<form action='<%=request.getContextPath()%>/' method='post'>
    <table>
        <tr>
            <td>Name:</td>
            <td><input type='text' name='name' required/></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type='login' name='login' required/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type='email' name='email' required/></td>
        </tr>
        <tr>
            <td>Date:</td>
            <td><input type='Timestamp' name='date' required/></td>
        </tr>
        <tr>
            <td><input type='submit' value='Save data'/></td>
        </tr>
    </table>
</form>
<h1>Rewrite user</h1>
<form action='<%=request.getContextPath()%>/update' method='post'>
    <table>
        <tr>
            <td>Name:</td>
            <td><input type='text' name='name' required/></td>
        </tr>
        <tr>
            <td>Existing login</td>
            <td><input type='login' name='login' required/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type='email' name='email' required/></td>
        </tr>
        <tr>
            <td>Date:</td>
            <td><input type='Timestamp' name='date' required/></td>
        </tr>
        <tr>
            <td><input type='submit' value='Save data'/></td>
        </tr>
    </table>
</form>
</body>
</html>