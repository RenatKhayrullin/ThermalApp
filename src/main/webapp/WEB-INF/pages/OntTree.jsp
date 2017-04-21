<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    <table cellpadding="5", cellspacing="20">
        <tr>
            <td valign="top" rowspan="2">
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
            <td valign="top" colspan="3">
                <div id="label"></div>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <table id="objProp" class="display cell-border row-border dt-middle" cellspacing="20" width="100%" style="overflow-x:auto">
                    <thead>
                    <tr>
                        <th>Object Property</th>
                    </tr>
                    </thead>
                </table>
            </td>
            <td valign="top">
                <table id="dataProp" class="display cell-border row-border dt-middle" cellspacing="5" width="100%" style="overflow-x:auto">
                    <thead>
                    <tr>
                        <th>Data Property</th>
                    </tr>
                    </thead>
                </table>
            </td>
            <td valign="top">
                <table id="inds" class="display cell-border row-border dt-middle" cellspacing="5" width="100%" style="overflow-x:auto">
                    <thead>
                    <tr>
                        <th>Instances</th>
                    </tr>
                    </thead>
                </table>
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
                    //display(data);
                    datatab(data);
                    /*
                    var ontdata =eval(data);
                    var onttable = $('#ontDataTab').DataTable( {
                        'aaData':           ontdata,
                        'paging':           false,
                        'scrollCollapse':   true,
                        'scrollY':          '50vh',
                        'info':             false,
                        'aoColumns': [
                            { 'mData': 'individuals' }
                        ],
                        'columnDefs': [{
                            'targets': 0,
                            'searchable': false,
                            'orderable': false,
                            'className': 'dt-body-center',
                            'render': function (data){
                                return '<input type="checkbox" name="indName" value="' + $('<div/>').text(data).html() + '">';
                            }
                        }],
                    });*/
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                    display(e);
                }
            });
        });
    });

    function datatab(data) {
        $('#label').html(data["label"]);
        var objPropData = process(data, "objProperties");
        console.log(objPropData[0].field);
        if ($.fn.DataTable.isDataTable( '#objProp' ) ) {
            $('#objProp').dataTable().fnDestroy();
        }
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
        if ($.fn.DataTable.isDataTable( '#dataProp' ) ) {
            $('#dataProp').dataTable().fnDestroy();
        }
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
        if ($.fn.DataTable.isDataTable( '#inds' ) ) {
            $('#inds').dataTable().fnDestroy();
        }
        var indTable = $('#inds').DataTable( {
            'aaData':           inds,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': 'field' }
            ]
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
        data[field].forEach(function (element) {
            var object = {"field": element};
            array.push(object);
        });
        console.log(array);
        return array;
    }


</script>

</html>
