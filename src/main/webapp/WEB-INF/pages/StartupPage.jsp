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

<body>
	<h1>${message}</h1>
    <div id="feedback"></div>
    <form:form id="metaData" action="/metadata/datainfo" method="POST" modelAttribute="dataMetaInfo">
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
                { 'mData': 'chemicalFormula' },
                { 'mData': 'substanceType' }
            ],
            'columnDefs': [{
                'targets': 0,
                'searchable': false,
                'orderable': false,
                'className': 'dt-body-center',
                'render': function (data, type, full, meta){
                    return '<input type="checkbox" name="id[]" value="' + $('<div/>').text(data).html() + '">';
                }
            }],
        });

        // Handle form submission event
        $('#metaData').on('submit', function(e){
            var frm = $('#metaData');
            var form = this;
            var data = {};

            // Iterate over all checkboxes in the table
            chemtable.$('input[type="checkbox"]').each(function(){
                // If checkbox exists in DOM
                if($.contains(document, this)){
                    // If checkbox is checked
                    if(this.checked){
                        data["substanceid"] = parseInt(this.value),
                        data["stateid"] = parseInt(this.value),
                        data["propertyid"] = parseInt(this.value)
                    }
                }
            });


            console.log(data);
            console.log("URL", frm.attr('action'));
            console.log("METHOD", frm.attr('method'));

            $.ajax({
                url: frm.attr('action'),
                type: frm.attr('method'),
                contentType : "application/json",
                dataType: "json",
                data: JSON.stringify(data),
                success :function(data) {
                    console.log("SUCCESS: ", data);
                }
            });
        });
    });

</script>
