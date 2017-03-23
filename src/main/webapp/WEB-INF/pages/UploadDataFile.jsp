<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
</head>
<body>
    <form:form action="${pageContext.request.contextPath}/uploadSuccess" commandName="uploadFile" method="post" enctype="multipart/form-data">
        <form:label for="uploadFile" path="uploadFile">Select data file</form:label><br/><br/>
        <form:input path = "uploadFile" type = "file"/>
        <input type="submit">
    </form:form>
</body>
</html>
