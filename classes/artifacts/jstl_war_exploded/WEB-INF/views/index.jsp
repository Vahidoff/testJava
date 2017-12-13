<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>JSTL</title>
</head>
<body>
<h1>Here all users</h1>
<table border='3' style="background-color:#61d8ff" width='100%'>
    <tr>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Date</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${users}" var="user" varStatus="status">
        <tr>
            <td align="center"><c:out value="${user.name}"/></td>
            <td align="center"><c:out value="${user.login}"/></td>
            <td align="center"><c:out value="${user.email}"/></td>
            <td align="center"><c:out value="${user.createDate}"/></td>
            <td align="center">
                <form><input type='button' value='Rewrite user'
                             onclick="window.location.href=
                                     'update?login=<c:out value="${user.login}"></c:out>'"/>
                </form>
            </td>
            <td align="center">
                <form><input type='button' value='Delete'
                             onclick="window.location.href=
                                     'delete?login=<c:out value="${user.login}"></c:out>'"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<h1>Add new user</h1>
<form action='${pageContext.servletContext.contextPath}/create' method='post'>
    <table>
        <tr>
            <td>Name:</td>
            <td><input type='name' name='name' required/></td>
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
</body>
</html>

