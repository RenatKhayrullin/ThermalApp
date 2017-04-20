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
<h2>Resource fields that will be shown</h2>
<h3><c:out value="${resource.resourceName}"/></h3>
<table style="border: 1px solid; width: 1000px; text-align:center">
    <thead style="background:#bde">
    <tr>
        <th>Field Name</th>
        <th>Table title</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${columns}" var="c">
        <tr>
            <td>"${c.columnName}"</td>
            <td>"${c.columnComment}"</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/><a href="/resources/addColumn?id=${resource.id}">Add column</a>
<br/><a href="/resources">Back to list of resources</a>
</body>
</html>
