<%@ page import="ru.job4j.crudservlet.UserCS" %>
<%@ page import="ru.job4j.userservlets.UsersDeposit" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--если убрать эту строчку то все ок--%>
<html>
<head>
    <title>Update user</title>
</head>
<body>
<form action='<%=request.getContextPath()%>/update' method='post'>
    <%String login = request.getParameter("login");%>
    <%
        UserCS user = null;
        try {
            user = UsersDeposit.getInstance().getUserCS(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>
    <table>
        <%if (user != null) {%>
        <tr>
            <td></td>
            <td><input type='hidden' name='login' value='<%=user.getLogin()%>'/></td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type='text' name='name' value='<%=user.getName()%>' required/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type='email' name='email' value='<%=user.getEmail()%>' required/></td>
        </tr>
        <tr>
            <td>Date:</td>
            <td><input type='Timestamp' name='date' value='<%=user.getCreateDate()%>' required/></td>
        </tr>
        <tr>
            <td><input type='submit' value='Save data'/></td>
        </tr>
        <%}%>
    </table>
</form>
<form><input type='button' value='Here all users' onclick="window.location.href='/usercs'">
</form>
<%--<a href="${pageContext.servletContext.contextPath}/">Here all users</a>--%>
</body>
</html>
