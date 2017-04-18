<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>OntTree</title>
    <spring:url value="/resources/jquery/jquery-3.1.1.min.js"               var="jqueryJs" />
    <script src="${jqueryJs}"></script>
</head>

<body>
    <table>
        <tr>
            <td>
                <table>
                    <c:forEach items="${ontList}" var="ontElem">
                        <tr>
                            <td>${ontElem.id}</td>
                            <td><button class="post_btn">${ontElem.name}</button></td>
                            <td>${ontElem.parentId}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <div id="feedback"></div>
            </td>
        </tr>
    </table>
</body>

<script type="text/javascript">
    $(document).ready(function(){

        $(".post_btn").click(function() {

            name = $(this).text();
            console.log(name);

            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "/OntTree/OntProperty",

                data : JSON.stringify(name),
                dataType : "json",

                success : function(data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                    display(e);
                }
            });
        });
    });

    function display(data) {
        var json = "<h4>Ajax Response</h4><pre>"
            + JSON.stringify(data, null, 4) + "</pre>";
        $('#feedback').html(json);
    }

</script>

</html>
