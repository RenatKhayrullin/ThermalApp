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
        var data = JSON.parse('${numericData}');
        //Test
        /*
        var data = JSON.parse('[' +
                '{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"Bar","quantityValue":168.0,"uncertaintyType":2,"uncertaintyValue":10.0,"reference":1},' +
                '{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"T","dimension":"K","quantityValue":98.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1}, ' +
                '{"dataset":1,"rowNum":3,"substance":"Neon","state":"liquid-solid","quantity":"S","dimension":"K","quantityValue":98.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1},' +
                '{"dataset":1,"rowNum":1,"substance":"Neon","state":"liquid-solid","quantity":"Pmelt","dimension":"Bar","quantityValue":160.0,"uncertaintyType":2,"uncertaintyValue":10.0,"reference":1},' +
                '{"dataset":1,"rowNum":1,"substance":"Neon","state":"liquid-solid","quantity":"T","dimension":"K","quantityValue":90.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1}, ' +
                '{"dataset":1,"rowNum":1,"substance":"Neon","state":"liquid-solid","quantity":"S","dimension":"K","quantityValue":98.38,"uncertaintyType":1,"uncertaintyValue":10.0,"reference":1}]');
        */
        console.log(data);

        var dataSources = JSON.parse('${dataSources}');
        var uncertainties = JSON.parse('${uncertainties}');
        var uncertaintyValues = JSON.parse('${uncertaintyValues}');

        data = process(data);
        var numericData = data["data"];
        console.log(numericData);
        var type = getType(numericData);
        var headers = getHeaders(numericData[0], type, dataSources, uncertainties, uncertaintyValues,
                data["requiredSources"], data["requiredUncertainties"]);

        $('#dataSourceAbout').html('<h4>DataSources Information</h4>');
        $('#uncertaintiesAbout').html('<h4>UncertaintyTypes Information</h4>');
        $('#numericDataAbout').html('<h4>Data</h4>');

        var sourceTable = $('#datasources').DataTable({
            'aaData': headers["actualSources"],
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
            'aaData': headers["actualUncertainties"],
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
            'aaData': numericData,
            'paging': false,
            'scrollCollapse': true,
            'scrollY': '50vh',
            'info': false,
            'aoColumns': headers["mainHeaders"]
        })
    })

    function process(data) {
        var dataset = 0, a=[], count = 0, headers = [];
        var requiredSources = [], requiredUncertainties = [];

        while(count < data.length) {
            ++dataset;
            var row_num = 0, singledataset = [];
            data.forEach(function(element) {
                if (element.dataset == dataset) {
                    singledataset.push(element);
                }
            })

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
                        requiredSources.push(datasource);
                        requiredUncertainties.push(element.uncertaintyType);
                    }
                })
                if (o.hasOwnProperty("datasource")) a.push(o);
            }
            count += newcount;
        }
        return {"requiredSources": requiredSources, "requiredUncertainties": requiredUncertainties, "data": a};
    }

    function getType(data) {
        var type = 1, uncertaintyValues = {};
        var datasource = data[0]["datasource"];

        //If there are different uncertaintyValues, type is not less than 2
        data.forEach(function (object) {
            for (var key in object) {
                if (key.includes('-Unc-')) {
                    var uncertaintyType = key.match("[0-9]+")[0];
                    if (uncertaintyValues.hasOwnProperty(uncertaintyType) &&
                            uncertaintyValues[uncertaintyType] != object[key]) {
                        type = 2;
                        break;
                    }
                    uncertaintyValues[uncertaintyType] = object[key];
                }
            }
        })
        //WARNING! This is bad assumption!
        //If there are more than 2 uncertainties, set type 2.
        if (Object.keys(uncertaintyValues).length > 1) type = 2;

        //If there are different dataSources, type is 3
        for (var object in data) {
            if(data[object]["datasource"] != datasource) {
                type = 3;
                break;
            }
        }
        console.log(type);
        return type;
    }

    function getHeaders(data, type, datasources, uncertainties, uncertaintyValues, requiredSources, requiredUncertainties) {

        var mainHeaders = [];
        //Get headers for NumericData Table
        for (var key in data) {
            if (key.includes("-Unc-") && type == 1) continue;
            if (key.includes("datasource") && type != 3) continue;
            mainHeaders.push({title: key, mData: key});
        }
        //Sort headers in alphabet. Datasource is the last column
        mainHeaders.sort(function (a, b) {
            if (a.title == "datasource") return 1;
            if (b.title == "datasource") return -1;
            if (a.title > b.title) return 1;
            if (a.title < b.title) return -1;
            return 0;
        });

        var actualSources = [], actualUncertainties = [];
        //Skip all unnecessary datasources
        datasources.forEach(function (element) {
            if (requiredSources.indexOf(element["id"]) >= 0)
                actualSources.push(element);

        })

        //Skip all unnecessary uncertainties. Add value if needed
        uncertainties.forEach(function (element) {
            if (requiredUncertainties.indexOf(element["id"]) >= 0) {
                var value = "";
                if (type == 1) {
                    if (uncertaintyValues.hasOwnProperty(element["id"])) value = uncertaintyValues[element["id"]];
                    else value = "no information in database"
                }
                actualUncertainties.push({
                    "id": element["id"],
                    "uncertaintyName": element["uncertaintyName"],
                    "uncertaintyValue": value
                })
            }
        })

        return {"mainHeaders": mainHeaders, "actualSources": actualSources, "actualUncertainties": actualUncertainties};
    }

</script>
</body>
</html>
