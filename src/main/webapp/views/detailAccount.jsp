<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h1>Detail Account</h1>
    <table>
      <tr>
        <th>Account's ID</th>
        <th>UserID</th>
        <th>Account</th>
        <th>Password</th>
        <th>Website</th>
      </tr>

      <tr>
        <td>${account.getId()}</td>
        <td>${account.getUserId()}</td>
        <td>${account.getAcc()}</td>
        <td>${account.getPass()}</td>
        <td>${account.getWeb()}</td>
      </tr>
    </table>
  </body>
</html>
