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
                        <div id="additionalHeader"/>
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
        var data = JSON.parse('${numericData}');
        //var data = JSON.parse('[{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"Bar","quantityValue":168.0,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1},{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"T","dimension":"K","quantityValue":98.38,"uncertaintyType":2,"uncertaintyValue":10.0,"reference":1}]');
        console.log(data);
        data = process(data);
        console.log(data);
        var headers = getHeaders(data[0]);
        console.log(headers);
        //console.log(data.length);
        var dataSources = JSON.parse('${dataSources}');
        var uncertainties = JSON.parse('${uncertainties}');

        $('#dataSourceAbout').html('<h4>DataSources Information</h4>');
        $('#uncertaintiesAbout').html('<h4>UncertaintyTypes Information</h4>');
        $('#numericDataAbout').html('<h4>Data</h4>');

        var table = $('#datasources').DataTable({
            'aaData': dataSources,
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': [
                {mData: "id"},
                {mData: "bibliographicReference"}
            ]
        })

        var table = $('#uncertainties').DataTable({
            'aaData': uncertainties,
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': [
                {mData: "id"},
                {mData: "uncertaintyName"}
            ]
        })

        var table = $('#NData').DataTable({
            'aaData': data,
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': headers
        })
    })

    function process(data) {
        var dataset = 0, a=[], count = 0, headers = [];
        while(count < data.length) {
            ++dataset;
            var row_num = 0, singledataset = [];
            data.forEach(function(element) {
                if (element.dataset == dataset) {
                    //console.log(element);
                    singledataset.push(element);
                }
            })
            //console.log(singledataset);
            var newcount = 0;
            while (newcount < singledataset.length) {
                ++row_num;
                var o = {};
                singledataset.forEach(function(element) {
                    if (element.rowNum == row_num) {
                        ++newcount;
                        var quantity = element.quantity + ' (' + element.dimension + ')';
                        var value = element.quantityValue, datasource = element.reference;
                        var uncertaintyType = element.quantity + '-Unc-' + element.uncertaintyType;
                        o[quantity] = value;
                        o["datasource"] = datasource;
                        o[uncertaintyType] = element.uncertaintyValue;
                    }
                })
                if (o.hasOwnProperty("datasource")) a.push(o);
            }
            count += newcount;
        }
        return a;
    }

    function getHeaders(data) {
        var array = [];
        for (var key in data) {
            array.push({title: key, mData: key});

        }
        array.sort(function (a, b) {
            if (a.title == "datasource") return 1;
            if (b.title == "datasource") return -1;
            if (a.title > b.title) {
                return 1;
            }
            if (a.title < b.title) {
                return -1;
            }
            return 0;
        });
        return array;
    }
</script>
</body>
</html>
