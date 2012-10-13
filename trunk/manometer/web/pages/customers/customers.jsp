<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>JSP Page</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="css/smartTable.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<a href="CustomerProcess.do?method=setUpForInsertOrUpdate">Добавить
			предприятие</a>
		<br>
		<br>





		<display:table keepStatus="true" name="customers" id="row"
			requestURI="CustomerProcess.do?method=getCustomers"
			excludedParams="method" requestURIcontext="false" pagesize="24"
			sort="list" class="simple" defaultsort="3" >

            
			<%--<display:column property="registrationNumber" title="ID"--%>
				<%--sortable="true" />--%>
			<display:column title="Ст" sortable="true" class="bool2">
				<c:if test="${row.status}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
            <display:column title="Нов" sortable="true" class="bool2" >
				<c:if test="${row.new}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>

			<display:column property="shortName" title="кор название"
				url="/CustomerProcess.do?method=setUpForInsertOrUpdate" paramId="id"
				paramProperty="id" sortable="true" />
			<display:column property="branch" title="отр" />
			<display:column property="person" title="спец. OCO" />
			<display:column property="nomList" title="№ списка" />
			<display:column property="prospect" title="Потенциал" />
		</display:table>

	</body>
</html>