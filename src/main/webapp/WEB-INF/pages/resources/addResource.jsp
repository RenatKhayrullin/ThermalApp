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
<h2>Add new resource</h2>
<form method="POST" action="/resources/added"  accept-charset="UTF-8">
    <table>

        <tr>
            <td>Resource Name</td>
            <td><input type = "text" name="resourceName" value = ""/></td>
        </tr>

        <tr>
            <td>Resource URL<br/>Put here patterns for query and apikey (if required).<br/>Example:
                http://data.bioontology.org/search?apikey={apikey}&q={entity}</td>
            <td><input type = "text" name="url" value = ""/></td>
        </tr>

        <tr>
            <td>Response format you expect<br/>Example: text/xml</td>
            <td><input type = "text" name="acceptData" value = ""/></td>
        </tr>

        <tr>
            <td>Response charset you expect<br/>UTF-8 is prefered</td>
            <td><input type = "text" name="acceptCharset" value = ""/></td>
        </tr>

        <tr>
            <td>Apikey (if required)</td>
            <td><input type = "text" name="apikey" value = ""/></td>
        </tr>

        <tr>
            <td>Additional Information</td>
            <td><input type = "text" name="additional" value = ""/></td>
        </tr>
    </table>

    <input type="submit" value="Save" />
</form>
<br/><a href="/resources">Back to list of resources</a>
</body>
</html>
