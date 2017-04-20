<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Катерина
  Date: 19.04.2017
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Columns</title>
</head>
<body>
<h2>Add new columns for resource</h2>
<h3>${resource.resourceName}</h3>
<form method="POST" action="/resources/addedColumns?id=${resource.id}"  accept-charset="UTF-8">
    <table>

        <tr>
            <td>Field Name<br/>Name of field in resource</td>
            <td><input type = "text" name="columnName" value = ""/></td>
        </tr>

        <tr>
            <td>Table Title<br/>Text will be shown in result table head</td>
            <td><input type = "text" name="comment" value = ""/></td>
        </tr>

    </table>

    <input type="submit" value="Save" />
</form>
</body>
</html>
