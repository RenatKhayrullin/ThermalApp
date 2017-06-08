<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html lang="en">

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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

<body>
	<h1>${message}</h1>
    <div id="feedback"></div>
    <form:form id="metaData" action="/metadata/datainfo" method="POST" modelAttribute="dataMetaInfo" >
        <table class="table table-bordered dt-middle">
            <tr>
                <td>
                    <table id="chemSubstTab" class="display cell-border row-border dt-middle" cellspacing="0" width="100%" style="overflow-x:auto">
                        <thead>
                        <tr>
                            <th></th>
                            <th>substanceName</th>
                            <th>chemicalFormula</th>
                            <th>substanceType</th>
                        </tr>
                        </thead>
                    </table>
                </td>
                <td>
                    <table id="statesTab" class="display cell-border row-border dt-middle" cellspacing="0" width="100%" style="overflow-x:auto">
                        <thead>
                        <tr>
                            <th></th>
                            <th>phaseName</th>
                            <th>phaseType</th>
                        </tr>
                        </thead>
                    </table>
                </td>
                <td>
                    <table id="qtyTab" class="display cell-border row-border dt-middle" cellspacing="0" width="100%" style="overflow-x:auto">
                        <thead>
                        <tr>
                            <th></th>
                            <th>quantityName</th>
                            <th>quantityDesignation</th>
                        </tr>
                        </thead>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="text-left">
                    <input id="submitId" type="submit" value="Get Data">
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>

<script type="text/javascript">
    $(document).ready(function (){
        var chemdata =eval('${chemSubstList}');
        var chemtable = $('#chemSubstTab').DataTable( {
            'aaData':           chemdata,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'id'},
                { 'mData': 'substanceName' },
                { 'mData': 'substanceFormula' },
                { 'mData': 'substanceType' }
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'render': function (data){
                    return '<input type="radio" name="substanceid" value="' + $('<div/>').text(data).html() + '">';
                }
            }],
        });

        var statedata =eval('${statesList}');
        var statetable = $('#statesTab').DataTable( {
            'aaData':           statedata,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'id'},
                { 'mData': 'phaseName' },
                { 'mData': 'phaseType' }
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'render': function (data){
                    return '<input type="radio" name="stateid" value="' + $('<div/>').text(data).html() + '">';
                }
            }],
        });


        var qtydata =eval('${quantitiesList}');
        var qtytable = $('#qtyTab').DataTable( {
            'aaData':           qtydata,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'id'},
                { 'mData': 'quantityName' },
                { 'mData': 'quantityDesignation' }
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'render': function (data){
                    return '<input type="checkbox" name="propertyid" value="' + $('<div/>').text(data).html() + '">';
                }
            }],
        });
    });

</script>
