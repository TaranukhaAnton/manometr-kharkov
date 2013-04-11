<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/mainPage.css" rel="stylesheet" type="text/css">
    <link href="../css/ui-lightness/jquery-ui-1.10.0.custom.css" rel="stylesheet" type="text/css">
    <link href="../css/jqueryslidemenu.css" rel="stylesheet" type="text/css">

    <%--<script src="../js/handlebars-1.0.rc.1.min.js"></script>--%>
    <script src="../js/jquery-1.9.0.js"></script>
    <script src="../js/jquery-ui-1.10.0.custom.min.js"></script>
    <script src="../js/jquery.timer.js"></script>
    <script src="../js/jquery.cookies.2.2.0.js"></script>
    <script src="../js/jqueryslidemenu.js"></script>


    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <script src="../js/local/gmaps.js"></script>



    <link href="../css/jquery.treeview.css" rel="stylesheet" type="text/css">
    <script src="../js/jquery.treeview.js"></script>

    <link href="../css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
    <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>

    <link href="../css/jquery.contextMenu.css" rel="stylesheet" type="text/css">
    <script src="../js/jquery.contextMenu.js"></script>


    <script src="../js/local/journey.js"></script>
    <script src="../js/local/leftMenu.js"></script>
    <title></title>
</head>
<body>
<jsp:include page="../work/header.jsp"/>
<table>
    <tr>
        <td>
            <jsp:include page="../work/leftMenu.jsp"/>
        </td>
        <td>
            <div id="journeyReport"/>
        </td>
    </tr>
</table>



</body>
</html>