<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
 

<html>
	<head>
        
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>JSP Page</title>

		<link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="css/smartTable.css" rel="stylesheet" type="text/css"/>

	</head>
	<body>



        <%

		Boolean isEmpt = ((List) request.getAttribute("contacts")).isEmpty();
		pageContext.setAttribute("isEmpt", isEmpt);
		pageContext.setAttribute("url", "ContactSaveOrUpdate.do?method=setUpForInsertOrUpdate");

%>



<c:if test="${!empty customer}">
<h2 align="center">Контактные лица предприятия&nbsp;&nbsp;&nbsp;
<c:out value='${customer.name}' />	<br />	<br />
</h2>




</c:if>



<a href="${url}" >Добавить	контакт</a>




		
			
			
		<br />
		<br />

		<c:if test="${! isEmpt}">
			<table class="simple">
				<tr>
					<td>

					</td>
					<td style="width: 123px; text-align: center;">
						Группа
					</td>
					<td style="width: 35px;">

					</td>
					<td style="width: 194px; text-align: center;">
						Роль в закупках
					</td>
				</tr>

			</table>
		</c:if>


		<display:table name="contacts" id="row"
			requestURI="ContactShow.do?method=getContacts"
			excludedParams="method" requestURIcontext="false" pagesize="20"
			sort="list" class="simple">


			<display:column property="lastName" title="Фамилия" sortable="true"
				maxLength="10"
				url="/ContactSaveOrUpdate.do?method=setUpForInsertOrUpdate"
				paramId="id" paramProperty="id" />
			<display:column property="customer" title="Предприятие" maxLength="10"/>
			<display:column property="profession" title="Должность" />
			<display:column property="subdivision" title="Подраздел"  maxLength="10"/>
			<display:column title="тех" class="bool2">
				<c:if test="${row.group1}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column title="сн" class="bool2">
				<c:if test="${row.group2}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>

			<display:column title="рук" class="bool2">
				<c:if test="${row.group3}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>


			<display:column sortable="true" title="акт" class="bool2">
				<c:if test="${row.status}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>

			<display:column sortable="true" title="1" class="bool">
				<c:if test="${row.purchaseRole1}">
					<img src="images/bullet_done.png" border="0" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="2" class="bool">
				<c:if test="${row.purchaseRole2}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="3" class="bool">
				<c:if test="${row.purchaseRole3}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="4" class="bool">
				<c:if test="${row.purchaseRole4}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="5" class="bool">
				<c:if test="${row.purchaseRole5}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="6" class="bool">
				<c:if test="${row.purchaseRole6}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="7" class="bool">
				<c:if test="${row.purchaseRole7}">
					<img src="images/bullet_done.png" />
				</c:if>
			</display:column>






		</display:table>



	</body>
</html>