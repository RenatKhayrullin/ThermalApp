<head>
    <!-- Include the required JavaScript libraries: -->

    <script src="${pageContext.request.contextPath}/resources/jquery.dynatree-1.2.8-all/src/jquery.dynatree.js" type="text/javascript"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/jquery.js" />
    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-ui.custom.js" type="text/javascript"/>

    <!-- Add code to initialize the tree when the document is loaded: -->
    <script type="text/javascript">
        $(function(){
            $("#tree").dynatree({
                onActivate: function(node) {
                    alert("You activated " + node);
                }
            });
        });
    </script>
</head>
<body>
<!-- Add a <div> element where the tree should appear: -->
<div id="tree">
    <ul>
        <li id="key1" title="Look, a tool tip!">item1 with key and tooltip
        <li id="key2" class="selected">item2: selected on init
        <li id="key3" class="folder">Folder with some children
            <ul>
                <li id="key3.1">Sub-item 3.1
                <li id="key3.2">Sub-item 3.2
            </ul>

        <li id="key4" class="expanded">Document with some children (expanded on init)
            <ul>
                <li id="key4.1">Sub-item 4.1
                <li id="key4.2">Sub-item 4.2
            </ul>

        <li id="key5" class="lazy folder">Lazy folder
    </ul>
</div>
</body>