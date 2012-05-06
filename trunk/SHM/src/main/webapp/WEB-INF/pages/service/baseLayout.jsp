<%@ page import="java.util.Enumeration" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
    <title><tiles:getAsString name="title" ignore="true"/></title>

    <tiles:useAttribute id="list" name="cssItems" classname="java.util.List"/>
    <tiles:useAttribute id="ppp" name="ppp" classname="java.util.List"/>

    <c:forEach var="item" items="${list}">
        <c:if test="${fn:startsWith(item, '/css')}">
            <link rel="stylesheet" type="text/css" href="<c:url value="${item}"/>"/>
        </c:if>
        <c:if test="${fn:startsWith(item, '/js')}">
            <script type="text/javascript" src="<c:url value="${item}"/>" ></script>
        </c:if>
    </c:forEach>


    <c:forEach var="item" items="${ppp}">
        <c:if test="${fn:startsWith(item, '/css')}">
            <link rel="stylesheet" type="text/css" href="<c:url value="${item}"/>"/>
        </c:if>
        <c:if test="${fn:startsWith(item, '/js')}">
            <script type="text/javascript" src="<c:url value="${item}"/>" ></script>
        </c:if>
    </c:forEach>



</head>
<body  >
<%--onresize="bodyResize();"--%>
<div id="mainDiv">
    <table border="1" class="rootTable" align="center">
        <tr class="header">
            <td colspan="2">
                <tiles:insertAttribute name="header" ignore="true"/>
            </td>
        </tr>
        <tr class="body">
            <td id="menuTD" valign="top">
                <tiles:insertAttribute name="menu"/>
            </td>
            <td valign="top" id="contentTD">
                <tiles:insertAttribute name="body"/>
            </td>
        </tr>
        <tr class="footer">
            <td colspan="2">
                <tiles:insertAttribute name="footer"/>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
