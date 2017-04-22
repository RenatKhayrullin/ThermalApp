<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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


<body>
<h1>Ontology</h1>
    <table  cellpadding="5", cellspacing="20">
        <tr>
            <td valign="top" rowspan="4">
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
            <td valign="top" colspan="2", height="30">
                <div id="label"></div>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <table id="objProp" class="display cell-border row-border dt-middle" cellspacing="5" width="100%" style="overflow-x:auto">
                    <thead>
                    <tr>
                        <th><div id = "objPropHead"></div></th>
                    </tr>
                    </thead>
                </table>
            </td>
            <td valign="top">
                <table id="dataProp" class="display cell-border row-border dt-middle" cellspacing="5" width="100%" style="overflow-x:auto">
                    <thead>
                    <tr>
                        <th><div id = "dataPropHead"></div></th>
                    </tr>
                    </thead>
                </table>
            </td>
        </tr>
        <tr>
            <td valign="top" colspan="2", height="30">
                <div id="search"></div>
            </td>
        </tr>
        <tr>
            <td valign="top", colspan="2">
                <form:form id="indRes" action="/searchResult" method="POST" modelAttribute="searchRequest">
                    <table id="searchTab" cellpadding="5", cellspacing="20">
                        <tr>
                            <td valign="top">
                                <table id="inds" class="display cell-border row-border dt-middle" cellspacing="20" width="100%" style="overflow-x:auto">
                                    <thead>
                                        <tr>
                                            <th><div id = "indHead"></div></th>
                                        </tr>
                                    </thead>
                                </table>
                            </td>
                            <td valign="top">
                                <table id="resources" class="display cell-border row-border dt-middle" cellspacing="20" width="100%" style="overflow-x:auto">
                                    <thead>
                                        <tr>
                                            <th><div id = "resHead"></div></th>
                                        </tr>
                                    </thead>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" class="text-left">
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
                    //display(data);
                    datatab(data);
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                    display(e);
                }
            });
        });
    });

    function datatab(data) {
        var label = (data["label"])? data["label"] : "";
        $('#label').html("Label: "+ label);

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
        $('#search').empty();
        $('#inds').empty();
        $('#resources').empty();
        $('#submit').empty();

        //Show dataTables for ObjProperties and dataProperties
        var objPropData = process(data, "objProperties");

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

        var dataPropData = process(data, "dataProperties");

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

        var inds = process(data, "individuals");

        //If there are no instances, do nothing
        if (inds.length == 0) return;

        //Show form for searching related instances in thirdparty resources
        $('#search').html("Choose instance and resource to search for related objects");
        $('#submit').html('<input id="submitId" type="submit" value="Get Data">');

        $('#indHead').html("Instances");
        var indTable = $('#inds').DataTable( {
            'aaData':           inds,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'field' }
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': false,
                'orderable': false,
                //'className': 'dt-body-center',
                'render': function (data){
                    return '<input type="radio" name="entity" value="' + $('<div/>').text(data).html() + '">' + data;
                }
            }]
        });

        $('#resHead').html("Resources");
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
                'searchable': false,
                'orderable': false,
                //'className': 'dt-body-center',
                'render': function (data){
                    return '<p><input type="radio" name="resource" value="' + $('<div/>').text(data).html() + '">' + data + '</p>';
                }
            }]
        });
    }

    function display(data) {
        if (data["code"] != 200) {
            $('#feedback').html("No data");
            return;
        }
        var response = "";
        response += add(data, "label");
        response += add(data, "objProperties");
        response += add(data, "dataProperties");
        response += add(data, "individuals");

        console.log(response);
        $('#feedback').html(response);
        /*
        var json = "<h4>Ajax Response</h4><pre>"
            + JSON.stringify(data, null, 4) + "</pre>";
        $('#feedback').html(json);
        */
    }

    function add(data, field) {
        var response = "<h3>"+ field + "</h3>";
        data[field].forEach(function (element) {
            response = response + "<br/>" + element;
        });
        response += "<br/>"
        return response;
    }

    function process(data, field) {
        var array = [];
        if (data[field] == null) return array;
        data[field].forEach(function (element) {
            var object = {"field": element};
            array.push(object);
        });
        console.log(array);
        return array;
    }


</script>

</html>
