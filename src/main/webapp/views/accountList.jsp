<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Account list</title>
    <style>
      table,
      th,
      td {
        border: 1px solid black;
      }
    </style>
  </head>
  <body>
    <h1>Account list by UserID</h1>
    <table>
      <tr>
        <th>Account's ID</th>
        <th>Account</th>
        <th>Password</th>
        <th>Website</th>
        <th>Details</th>
      </tr>
      <c:forEach var="account" items="${accounts}">
        <tr>
          <td>${account.getId()}</td>
          <td>${account.getAcc()}</td>
          <td>${account.getPass()}</td>
          <td>${account.getWeb()}</td>
          <td>
            <a href="../details/${account.getId()}"> Details </a>
            <br>
            <form:form
              method="POST"
              action="../../account/deleteAccount/${account.getId()}"
              onsubmit="return confirm('Are you sure you want to delete this Account?') ? true: false"
              >
              <input type="submit" value="Delete"/>
            </form:form>
          </td>
        </tr>
      </c:forEach>
    </table>

    <a href="../../account/insertAccount/${userId}"> Insert New Account </a>
  </body>
</html>
