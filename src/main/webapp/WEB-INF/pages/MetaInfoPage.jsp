<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>

<spring:url value="/resources/jquery/jquery-3.1.1.min.js"               var="jqueryJs" />
<script src="${jqueryJs}"></script>

<spring:url value="/resources/DataTables/js/jquery.dataTables.js"       var="datatable" />
<script src="${datatable}"></script>

<spring:url value="/resources/DataTables/js/dataTables.checkboxes.min.js"  var="checkboxesDataTables" />
<script src="${checkboxesDataTables}"></script>

<spring:url value="/resources/DataTables/css/jquery.dataTables.css"     var="jqueryDataTables" />
<link href="${jqueryDataTables}" rel="stylesheet" />

<spring:url value="/resources/DataTables/css/jquery.dataTables.min.css" var="jqueryDataTablesMin" />
<link href="${jqueryDataTablesMin}" rel="stylesheet" />

<spring:url value="/resources/DataTables/css/dataTables.checkboxes.css" var="checkboxesDataTablesCss" />
<link href="${checkboxesDataTablesCss}" rel="stylesheet" />

<spring:url value="/resources/css/common.css"                           var="common" />
<link href="${common}" rel="stylesheet" />
<head>
    <title>Meta Data</title>
</head>
<body>
<form:form id="metaForm" action="metainfo" method="post" modelAttribute="metaData">
    <table class="display cell-border row-border dt-middle" cellspacing="0" width="50%" style="overflow-x:auto">
        <tr>
            <table id="metaDataTab" class="display cell-border row-border dt-middle" cellspacing="0" width="50%" style="overflow-x:auto">
                <thead>
                <tr>

                </tr>
                </thead>
            </table>
        </tr>
        <tr>
        <td>
            <button type="submit">Get data</button>
        </td>
        </tr>
    </table>
</form:form>
<script type="text/javascript">

    $(document).ready(function (){
        var data = JSON.parse('${dataMetaInfo}');
        //data = process(data);
        data = data["data"][0];
        console.log(data);
        var columns = process(data);
        console.log(columns);
        var table = $('#metaDataTab').DataTable({
            'aaData': [data],
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': columns,

            'columnDefs': [
                {
                    'targets': 0,
                    'render': function (data) {return data;}
                },
                {
                    'targets': 1,
                    'render': function (data) {return data;}
                },
                {
                    'targets': "_all",
                    'render': function(data) {
                        console.log(data["quantity"]);
                        var string = data["quantity"] + '<br><br><select form="metaForm" name="metaData">';
                        data["dimensions"].forEach(function(element) {
                            string += '<option value="' + data["quantity"] + ' ' + element + '">' + element + '</option>';
                            //string += '<option value="1">' + element + '</option>';
                        })
                        string += '</select>';
                        return string;
                    }
                }
            ]

        });
    })

    function process(data) {
        var array = [], object = {}, count = 0;
        array.push({mData: "substance"});
        array.push({mData: "state"});
        data["quantities"].forEach(function (element) {
            object = {mData: "quantities." + count.toString()};
            ++count;
            array.push(object);
        })
        return array;
    }
</script>
</body>
</html>

