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
    <link href="../css/jqueryslidemenu.css" rel="stylesheet" type="text/css">

    <script src="../js/handlebars-1.0.rc.1.min.js"></script>
    <script src="../js/jquery-1.9.0.js"></script>
    <script src="../js/jquery-ui-1.10.0.custom.min.js"></script>
    <script src="../js/jquery.treeview.js"></script>
    <script src="../js/jquery.timer.js"></script>
    <script src="../js/jquery.cookies.2.2.0.js"></script>
    <script src="../js/jqueryslidemenu.js"></script>

    <%--<script src="../js/facescroll.js"></script>--%>
    <%--<script src="../js/jquery.ui.touch-punch.min.js"></script>--%>


    <script src="../js/local/main.js"></script>

    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <script src="../js/local/gmaps.js"></script>


    <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>





    <title></title>
</head>
<body>


<div clas="head" onclick="">
    <img style=" float: left;" src="../images/live.whitelabelsaas-new.com/login_header_logo.png" alt="">

    <div id="myslidemenu" class="jqueryslidemenu">
        <ul>
            <li><a href="http://www.dynamicdrive.com">Mapping</a></li>
            <li><a href="#">Proximity</a></li>
            <li><a href="#">Geo-Zones</a></li>
            <li><a href="#">Reports</a>
                <ul>
                    <li><a href="/reports/journey">Journey</a></li>
                    <li><a href="#">Sub Item 1.2</a></li>
                    <li><a href="#">Sub Item 1.3</a></li>
                    <li><a href="#">Sub Item 1.4</a></li>
                    <li><a href="#">Sub Item 1.5</a></li>
                    <li><a href="#">Sub Item 1.6</a></li>
                </ul>
            </li>
            <li><a href="#">Preferences</a></li>
            <li><a href="#">Admin</a>
                <ul>
                    <li><a href="#">Sub Item 2.1</a></li>
                    <li><a href="#">Folder 2.1</a>
                        <ul>
                            <li><a href="#">Sub Item 2.1.1</a></li>
                            <li><a href="#">Sub Item 2.1.2</a></li>
                            <li><a href="#">Folder 3.1.1</a>
                                <ul>
                                    <li><a href="#">Sub Item 3.1.1.1</a></li>
                                    <li><a href="#">Sub Item 3.1.1.2</a></li>
                                    <li><a href="#">Sub Item 3.1.1.3</a></li>
                                    <li><a href="#">Sub Item 3.1.1.4</a></li>
                                    <li><a href="#">Sub Item 3.1.1.5</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Sub Item 2.1.4</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li><a href="http://www.dynamicdrive.com/style/">Logout</a></li>
        </ul>
        <br style="clear: left" />
    </div>
</div>


<table>
    <tr>
        <td>
            <div class="menu" id="menu" >
                <div>
                    <a href="javascript:void($('#groupSelectDialog').dialog('open'))">Select Group</a>
                    <a href="javascript:void(expand())">Expand</a>
                    <a href="javascript:void( timer.toggle())">stop</a>
                    <%--<a href="javascript:void(timer.start())">start</a>--%>
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