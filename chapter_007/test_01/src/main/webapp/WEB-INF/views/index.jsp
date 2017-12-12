<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border='1' width='100%'>
    <tr>
        <th>Login</th>
        <th>Email</th>
    </tr>
    <c:forEach items="${users}" var="user" varStatus="status">
        <tr>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.email}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

