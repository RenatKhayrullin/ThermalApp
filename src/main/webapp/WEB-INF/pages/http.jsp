<%--
  Created by IntelliJ IDEA.
  User: Катерина
  Date: 27.03.2017
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>

    <c:url var="home" value="/" scope="request" />

    <spring:url value="/resources/css/common.css" var="coreCss" />
    <spring:url value="/resources/css/bootstrap.min.css"
                var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />

    <spring:url value="/resources/jquery/jquery-3.1.1.min.js"
                var="jqueryJs" />
    <script src="${jqueryJs}"></script>
</head>

<nav class="navbar navbar-inverse">
    <div class="container">
    </div>
</nav>

<div class="container" style="min-height: 500px">

    <h1>Search Form</h1>
    <br>

    <form action="/searchResult" method = "POST" class="form-horizontal" accept-charset="utf-8" id="search-form">
        <div class="form-group form-group-lg">
            <label class="col-sm-2 control-label">Entity or formula</label>
            <div class="col-sm-10">
                <input type=text class="form-control" name="entity">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" id="bth-search"
                        class="btn btn-primary btn-lg" value="Search"></input>
            </div>
        </div>
    </form>
</div>

</html>
