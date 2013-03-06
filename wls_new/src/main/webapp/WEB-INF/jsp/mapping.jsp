<%@ page import="org.krams.tutorial.view.BaseTreeNode" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 13.02.13
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/java2s.tld" prefix="java2s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

    <link href="../css/jquery.treeview.css" rel="stylesheet" type="text/css">
    <link href="../css/mainPage.css" rel="stylesheet" type="text/css">
    <link href="../css/ui-lightness/jquery-ui-1.10.0.custom.css" rel="stylesheet" type="text/css">
    <link href="../css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">

    <script src="../js/handlebars-1.0.rc.1.min.js"></script>
    <script src="../js/jquery-1.9.0.js"></script>
    <script src="../js/jquery-ui-1.10.0.custom.min.js"></script>
    <script src="../js/jquery.treeview.js"></script>
    <script src="../js/jquery.timer.js"></script>
    <script src="../js/jquery.cookies.2.2.0.js"></script>

    <%--<script src="../js/facescroll.js"></script>--%>
    <%--<script src="../js/jquery.ui.touch-punch.min.js"></script>--%>


    <script src="../js/local/main.js"></script>

    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <script src="../js/local/gmaps.js"></script>


    <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>


    <%--<jsp:include page="initMap.jsp"/>--%>

    <%--    <script type="text/javascript">




            //        var source   = "<p>{{lastName}}, {{firstName}}</p>";
            //        alert(source);
            //        var template = Handlebars.compile(source);
            //        var res = template({firstName: "Alan", lastName: "Johnson"});
            //        alert(res);
            //        alert(Handlebars.VERSION);
        </script>--%>


    <title></title>
</head>
<body>


<div clas="head" onclick="">
    <img src="../images/live.whitelabelsaas-new.com/login_header_logo.png" alt="">

    <div class="bgmenu"></div>
</div>


<table>
    <tr>
        <td>
            <div class="menu" id="menu">
                <div>
                    <a href="javascript:void($('#groupSelectDialog').dialog('open'))">Select Group</a>
                    <a href="javascript:void(expand())">Expand</a>
                </div>
                <div id="leftMenuSP">
                    <div id="leftMenu"/>
                </div>

            </div>

        </td>
        <td>
            <div id="map"/>
            <%--<div id="maparea" />--%>

        </td>

    </tr>
</table>

<%--<div id="map" >--%>
<%--<jsp:include page="IFrameMap.jsp"/>--%>
<%--<div id="flex_maparea" ></div>--%>
<%--</div>--%>


<div id="groupSelectDialog" title="Select Group">
    <% BaseTreeNode baseTreeNode = (BaseTreeNode) request.getAttribute("baseTreeNode");%>
    <java2s:Hello rootTreeNode="${baseTreeNode}"></java2s:Hello>
</div>

</body>
</html>