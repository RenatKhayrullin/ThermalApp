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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Data</title>
</head>
<body>
    <h2>AVAILABLE DATA</h2>
    <h3>FOR SUBSTANCE ${substance} IN STATE ${state}</h3>
    <table cellpadding="5" cellspacing="30" >
        <tr>
            <td><div id="dataSourceAbout"/></td>
        </tr>
        <tr>
            <td width="700" >
                <table id="datasources" class="display cell-border row-border dt-middle" cellspacing="5">
                    <thead>
                    <tr>
                        <th>DataSource Identifier</th>
                        <th>Bibliographic Reference</th>
                    </tr>
                    </thead>
                </table>
            </td>
        </tr>
        <tr>
            <td><div id="uncertaintiesAbout"/></td>
        </tr>
        <tr>
            <td width="400" >
                <table id="uncertainties" class="display cell-border row-border dt-middle" cellspacing="5">
                    <thead>
                    <tr>
                        <th>UncertaintyType Identifier</th>
                        <th>UncertaintyType Name</th>
                        <th>Uncertainty Value</th>
                    </tr>
                    </thead>
                </table>
            </td>
        </tr>
        <tr>
            <td><div id="numericDataAbout"/></td>
        </tr>
        <tr>
            <td width="200" >
                <table id="NData" class="display cell-border row-border dt-middle" cellspacing="5">
                    <thead>
                    <tr>

                    </tr>
                    </thead>
                </table>
            </td>
        </tr>
    </table>
<script type="text/javascript">

    $(document).ready(function (){
        var data = JSON.parse('${data}');
        //Test
        /*
        var data = JSON.parse('[' +
                '{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"Bar","quantityValue":168.0,"uncertaintyType":2,"uncertaintyValue":10.0,"reference":1},' +
                '{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"K","quantityValue":98.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1}, ' +
                '{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"S","dimension":"K","quantityValue":98.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1},' +
                '{"dataset":1,"rowNum":1,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"Bar","quantityValue":160.0,"uncertaintyType":2,"uncertaintyValue":10.0,"reference":2},' +
                '{"dataset":1,"rowNum":1,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"K","quantityValue":90.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":2}, ' +
                '{"dataset":1,"rowNum":1,"substance":"Neon","state":"liquid-solid","quantity":"S","dimension":"K","quantityValue":98.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":2}]');
        */
        console.log(data);

        $('#dataSourceAbout').html('<h4>DataSources Information</h4>');
        $('#uncertaintiesAbout').html('<h4>UncertaintyTypes Information</h4>');
        $('#numericDataAbout').html('<h4>Data</h4>');

        var sourceTable = $('#datasources').DataTable({
            'aaData': data["dataSources"],
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': [
                {mData: "id"},
                {mData: "bibliographicReference"}
            ]
        })

        var uncertaintyTable = $('#uncertainties').DataTable({
            'aaData': data["uncertainties"],
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': [
                {mData: "id"},
                {mData: "uncertaintyName"},
                {mData: "uncertaintyValue"}
            ]
        })

        var table = $('#NData').DataTable({
            'aaData': data["numericData"],
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': data["headers"]
        })
    })
    
</script>
</body>
</html>
