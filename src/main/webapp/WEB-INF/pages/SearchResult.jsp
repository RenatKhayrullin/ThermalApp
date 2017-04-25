<%--
  Created by IntelliJ IDEA.
  User: Катерина
  Date: 28.03.2017
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
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

<body>
    <h2>List of available data</h2>

    <form:form id="bind" action="/bind?entity=${entity}&resource=${resource}" method="POST" modelAttribute="link">
        <table id="form">
            <tr>
                <td>
                    <table id="dataTab" class="display cell-border row-border dt-middle" cellspacing="0" width="100%" style="overflow-x:auto">
                        <thead>
                            <tr>
                                <c:forEach items="${columns}" var="c">
                                  <th>${c.columnComment}</th>
                                </c:forEach>
                             </tr>
                        </thead>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="text-left">
                    <input id="submitEntity" type="submit" value="Bind">
                </td>
            </tr>
        </table>
    </form:form>


<script type="text/javascript">

    $(document).ready(function (){
        console.log('${infos}');
        var data = JSON.parse('${infos}');
        if ('${tpresource.additional}'.length > 0) {
                console.log('${tpresource.additional}');
                data = data['${tpresource.additional}'];
        }
        console.log(data);
        var columns = eval('${jsoncolumns}');
        var array = [];
        //console.log(columns[0]);
        for (var i = 0; i < columns.length; ++i){
            array.push({mData: columns[i].columnName});
        }
        console.log(array);
        var table = $('#dataTab').DataTable( {
            'aaData':           data,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns':        array,
            'columnDefs': [
                {
                    'targets': 0,
                    'searchable': true,
                    'orderable': true,
                    'render': function (data) {
                        if (data == null) return "";
                        if (data.toString().startsWith("http"))
                            return '<input type="radio" name="link" value="' + data + '">' +
                                    '<a href="' + data + '" title="' + data + '">' + 'Link' + '</a>';
                        return '<input type="radio" name="link" value="' + data + '">' + data;
                    }
                },
                {
                    'targets': "_all",
                    'searchable': true,
                    'orderable': true,
                    'className': 'dt-body-center',
                    'render': function (data) {
                        if (data == null) return "";
                        if (data.toString().indexOf("Image") > 0) return '<img src="' + data + '"/>';
                        if (data.toString().startsWith("http")) return '<a href="' + data +
                                '" title="' + data + '">' + 'Link' + '</a>';
                        return data;
                    }
                }]
        });
    });

/*
    $(document).ready(function (){
        console.log('${infos}');
        var spiderdata = JSON.parse('${infos}');
        //var spiderdata = '${infos}';
        console.log(spiderdata);
        var spidertable = $('#chemSpiderTab').DataTable( {
            'aaData':           spiderdata.collection,
            'paging':           false,
            'scrollCollapse':   true,
            'scrollY':          '50vh',
            'info':             false,
            'aoColumns': [
                { 'mData': "links.ontology" },
                { 'mData': "definition" },
                { 'mData': "prefLabel" },
                { 'mData': "@type" },
            ],
            'columnDefs': [
                {
                    'targets': 0,
                    'searchable': false,
                    'orderable': false,
                    'className': 'dt-body-center',
                    'render': function (data){
                        return '<a href="' + data + '">' + 'Ontology' + '</a>';
                    }
                },

                {
                    'targets': 3,
                    'searchable': false,
                    'orderable': false,
                    'className': 'dt-body-center',
                    'render': function (data){
                        if (data.includes('Class')) {
                            return '<a href="' + data + '">' + 'Owl#Class' + '</a>';
                        } else return '<a href="' + data + '">' + data + '</a>';
                    }
                }],

        });
    });
*/
    function proccess(data) {
        var array = [];
        //console.log(data[0].getColumnName());
        for (var i = 0; i < data.size; ++i){
            array[i].mData = data[i].getColumnName();
        }
        console.log(array);
        return array;
    }
</script>
</body>
</html>
