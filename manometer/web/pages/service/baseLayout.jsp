<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%--<logic:notPresent name="level" scope="session">--%>
<%--sdfsdfsdf--%>
<%--<jsp:forward  page="pages/users/userLogin.jsp"></jsp:forward>--%>
<%--</logic:notPresent>--%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/Manometr/css/main.css" rel="stylesheet" type="text/css"/>
    <title>
        <tiles:getAsString name="title" ignore="true"/>
    </title>

    <style type="text/css">
       
    </style>
</head>
<body>

<div id="mainDiv">


<table border="1" class="rootTable" align="center">
    <tr class="header">
        <td colspan="2">
            <tiles:insert attribute="header" ignore="true"/>
        </td>
    </tr>
    <tr class="body">
        <td id="menu" valign="top">
            <tiles:insert attribute="menu"/>
        </td>
        <td valign="top" id="contentTD" >


            <logic:present name="logonUser" scope="session">
                <tiles:insert attribute="body"/>
            </logic:present>
            <logic:notPresent name="logonUser" scope="session">
                <jsp:include page="/pages/users/userLogin.jsp"/>
            </logic:notPresent>

        </td>
    </tr>
    <tr class="footer">
        <td colspan="2">
            <tiles:insert attribute="footer"/>
        </td>
    </tr>
</table>
</div>

</body>
</html>
