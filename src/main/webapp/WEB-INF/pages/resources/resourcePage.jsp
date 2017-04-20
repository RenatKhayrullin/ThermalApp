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
    <title>Resources</title>
</head>
<body>
    <h2>List of available resources</h2>
    <table style="border: 1px solid; width: 1000px; text-align:center">
        <thead style="background:#bde">
        <tr>
            <th>Resource Name</th>
            <th>Resource Url</th>
            <th>Accept Data</th>
            <th>Accept Charset</th>
            <th>Apikey</th>
            <th>Resource fields that will be shown</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${resources}" var="r">
                <c:url var="columnsUrl" value="/resources/columns?id=${r.id}" />
                <tr>
                    <td>"${r.resourceName}"</td>
                    <td>"${r.url}"</td>
                    <td>"${r.acceptData}"</td>
                    <td>"${r.acceptCharset}"</td>
                    <td>"${r.apikey}"</td>
                    <td><a href="${columnsUrl}">List of fields</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br/><a href="/resources/add">Add resource</a>
</body>
</html>
