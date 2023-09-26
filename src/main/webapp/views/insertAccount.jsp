<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <h2>Insert new Account</h2>
    <form:form method="POST"
        modelAttribute="account"
        action="/account/insertAccount/${userId}">

        <form:input type="text"
            placeholder="Enter account's username"
            value="${account.getAcc()}"
            path="acc"
        /><br>
        <form:errors path="acc" cssClass="error"/> <br>

        <form:input type="text"
            placeholder="Enter account's password"
            value="${account.getPass()}"
            path="pass"
        /><br/>
        <form:errors path="pass" cssClass="error" /> <br>

        <form:input type="text"
            placeholder="Enter website"
            value="${account.getWeb()}"
            path="web"
        /><br/>
        <form:errors path="web" cssClass="error"/> <br>
        
        <p class="error">${error}</p>
        <input type="submit" value="Insert" />
    </form:form>

</body>
</html>