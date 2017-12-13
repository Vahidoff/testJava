<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h1>Rewrite user</h1>
<form action='${pageContext.servletContext.contextPath}/update' method='post'>

    <table>
        <tr>
            <td></td>
            <td><input type='hidden' name='login'
                       value='<c:out value="${user.login}"></c:out>'/></td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type='text' name='name'
                       value='<c:out value="${user.name}"></c:out>' required/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type='email' name='email'
                       value='<c:out value="${user.email}"></c:out>' required/></td>
        </tr>
        <tr>
            <td>Date:</td>
            <td><input type='Timestamp' name='date'
                       value='<c:out value="${user.createDate}"></c:out>' required/></td>
        </tr>
        <tr>
            <td><input type='submit' value='Save data'/></td>
        </tr>
    </table>
</form>
<br/>
<form><input type='button' value='Here all users'
             onclick="window.location.href='${pageContext.servletContext.contextPath}/' "/>
</form>
</body>
</html>