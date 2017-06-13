<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

    <spring:url value="/resources/css/style.min.css" var="jqueryTreeStyle" />
    <link href="${jqueryTreeStyle}" rel="stylesheet" />

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

    <script src="/resources/jquery/jstree.min.js"></script>


<body>
<h1>Ontology</h1>
    <table  cellpadding="5" cellspacing="20" >
        <tr>
            <td valign="top" rowspan="6" width="370">
                <div id="ontology"/>
            </td>
            <td valign="top" colspan="3" height="30">
                <div id="label"></div>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="3" height="30">
                <div id="comment"></div>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="3" height="30">
                <div id="equivalentClass"></div>
            </td>
        </tr>
        <tr>
            <td valign="top" width="250">
                <table id="objProp" class="display cell-border row-border dt-middle" cellspacing="5" >
                    <thead>
                    <tr>
                        <th><div id = "objPropHead"></div></th>
                    </tr>
                    </thead>
                </table>
            </td>
            <td valign="top" width="250">
                <table id="dataProp" class="display cell-border row-border dt-middle" cellspacing="5" >
                    <thead>
                    <tr>
                        <th><div id = "dataPropHead"></div></th>
                    </tr>
                    </thead>
                </table>
            </td>
            <td></td>
        </tr>
        <tr>
            <td valign="top" colspan="3" height="30" >
                <div id="search"></div>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="3">
                <form:form id="indRes" action="/searchResult" method="POST" modelAttribute="searchRequest">
                    <table  id="searchTab" cellpadding="5" cellspacing="20">
                        <tr>
                            <td valign="top" width="250">
                                <table id="inds" class="display cell-border row-border dt-middle" cellspacing="20" style="overflow-x:auto">
                                    <thead>
                                        <tr>
                                            <th><div id = "indHead"></div></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                </table>
                            </td>
                            <td valign="top" rowspan="3">
                                <div id="info"/>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top" width="250">
                                <table id="resources" class="display cell-border row-border dt-middle" cellspacing="20" style="overflow-x:auto">
                                    <thead>
                                        <tr>
                                            <th><div id = "resHead"></div></th>
                                        </tr>
                                    </thead>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left">
                                <div id="submit"></div>
                            </td>
                        </tr>
                    </table>
                </form:form>
            </td>
        </tr>

    </table>
</body>

<script type="text/javascript">
    $(document).ready(function(){

        $("#label").html("Choose Ontology Class");
        var ontData = JSON.parse('${ontJson}');
        console.log(ontData);

        $("#ontology").on("changed.jstree", function (e, data) {
            var ontData = data.node.text;
            console.log(ontData);

            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "/OntTree/OntProperty",

                data : JSON.stringify(ontData),
                dataType : "json",

                success : function(data) {
                    console.log("SUCCESS: ", data);
                    datatab(data);
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                }
            })}).jstree({ 'core' : {
                'data' : ontData
        } });
    });

    function getInfo(name) {
        console.log(name);

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "/OntTree/InstInfo",

            data : JSON.stringify(name),
            dataType : "json",

            success : function(data) {
                console.log("SUCCESS: ", data);
                displayInfo(data);
            },
            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
    }

    function datatab(data) {
        var label = (data["label"])? data["label"] : "";
        var comment = (data["comment"])? data["comment"] : "";
        var equivalentClass = (data["equivalentClass"])? data["equivalentClass"] : "";
        $('#label').html('<h3>' + label + '</h3>');
        $('#comment').html('<h4>' + comment + '</h4>');
        $('#equivalentClass').html('Equivalent Class: ' + '<a href="' + equivalentClass + '">' + equivalentClass);

        //Destroy old dataTables
        if ($.fn.DataTable.isDataTable( '#objProp' ) ) {
            $('#objProp').dataTable().fnDestroy();
        }
        if ($.fn.DataTable.isDataTable( '#dataProp' ) ) {
            $('#dataProp').dataTable().fnDestroy();
        }
        if ($.fn.DataTable.isDataTable( '#inds' ) ) {
            $('#inds').dataTable().fnDestroy();
        }
        if ($.fn.DataTable.isDataTable( '#resources' ) ) {
            $('#resources').dataTable().fnDestroy();
        }
        $('#search').hide();
        $('#searchTab').hide();

        //Show dataTables for ObjProperties and dataProperties
        var objPropData = JSON.parse(data["objProperties"]);
        console.log("ObjProp: ", objPropData);

        $('#objPropHead').html("Object Properties");
        var objPropTable = $('#objProp').DataTable( {
            'aaData':           objPropData,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'field' }
            ]
        });

        var dataPropData = JSON.parse(data["dataProperties"]);
        console.log("DataProp: ", dataPropData);

        $('#dataPropHead').html("Data Properties");
        var dataPropTable = $('#dataProp').DataTable( {
            'aaData':           dataPropData,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'field' }
            ]
        });

        var individuals = JSON.parse(data["individuals"]);
        //If there are no instances, do nothing
        if (individuals.length == 0) return;

        //Show form for searching related instances in thirdparty resources
        $('#search').show();
        $('#searchTab').show();
        $('#search').html('<h4>Choose instance and resource to search for related objects</h4>');
        $('#submit').html('<button type="submit">Get data</button>');
        $('#info').empty();
        $('#indHead').html("Instances");
        $('#resHead').html("Resources");

        var indTable = $('#inds').DataTable( {
            'aaData':           individuals,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'field'},
                { 'mData': 'info'}
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': true,
                'orderable': true,
                'render': function (data){
                    return '<input type="radio" name="entity" value="' + $('<div/>').text(data).html() + '">' + data;
                }
            },{
                'targets': 1,
                'searchable': false,
                'orderable': false,
                'render': function (data){
                    return '<button type="button" onClick="getInfo(\'' + data + '\')" value="' + data + '">' + 'info' + '</button>';
                }
            }]
        });

        var resData = JSON.parse('${thirdPartyRes}');
        console.log(resData);
        var resTable = $('#resources').DataTable( {
            'aaData':           resData,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'resourceName' }
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': true,
                'orderable': true,
                'render': function (data){
                    return '<input type="radio" name="resource" value="' + $('<div/>').text(data).html() + '">' + data;
                }
            }]
        });
    }

    function displayInfo(data) {
        var info = JSON.parse(data["instInfo"]);
        console.log(info);
        var response = '<br><br><br>';
        for (var key in info) {
            //console.log(key);

            response += ('<strong>' + key + ':</strong><br>');
            if (Array.isArray(info[key])) {
                info[key].forEach(function (element) {
                    if (element.toString().startsWith("http"))
                            element = '<div style="line-height: 30%;"> <a href="' + element + '">' + element + '</div>';
                    //console.log(element);
                    //response += ('   ' + element + '<br>');
                    response += ('<div style="text-indent: 15px;">' + element + '</div>' + '<br>');
                });
            }
            else {
                var text = "";
                if (info[key].toString().startsWith("http")) {
                    text = '<a href="' + info[key] + '">' + info[key];
                } else text = info[key];
                response += ('<div style="text-indent: 15px;">' + text + '</div>' + '<br>');
            }
            response += '<br>';
        }
        $('#info').html(response);
    }

    //border="5" bgcolor="#006400"
</script>

</html>
