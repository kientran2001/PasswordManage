<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <h1>Please Login</h1>
    <form:form method="POST" action="/login" modelAttribute="loginForm">
        <form:input type="text" path="username" placeholder="Enter username:" /><br>
        <form:errors path="username" cssClass="error"/><br>

        <form:input type="password" path="password" placeholder="Enter password:" /><br>
        <form:errors path="password" cssClass="error"/><br>

        <input type="submit" value="Login" />
    </form:form>
    <br>
    <br>
    <a href="../register">
        Register
    </a>
</body>
</html>
