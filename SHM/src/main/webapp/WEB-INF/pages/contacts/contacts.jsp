
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${!empty customer}">
<h2 align="center">Контактные лица предприятия&nbsp;&nbsp;&nbsp;
<c:out value='${customer}' />	<br />	<br />
</h2>




</c:if>



<a href="edit" >Добавить	контакт</a>
		<display:table name="listContact" id="row" keepStatus="true"
			requestURI="./"
			excludedParams="method" requestURIcontext="false" pagesize="20"
			sort="list" class="simple">


			<display:column property="lastName" title="Фамилия" sortable="true"  maxLength="10" url="/contacts/edit" paramId="id" paramProperty="id" />
			<display:column property="customer" title="Предприятие" maxLength="10"/>
			<display:column property="profession" title="Должность" />
			<display:column property="subdivision" title="Подраздел"  maxLength="10"/>
			<display:column title="тех" class="bool2">
				<c:if test="${row.group1}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column title="сн" class="bool2">
				<c:if test="${row.group2}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>

			<display:column title="рук" class="bool2">
				<c:if test="${row.group3}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>


			<display:column sortable="true" title="акт" class="bool2">
				<c:if test="${row.status}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>

			<display:column sortable="true" title="1" class="bool">
				<c:if test="${row.purchaseRole1}">
					<img src="../images/bullet_done.png" border="0" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="2" class="bool">
				<c:if test="${row.purchaseRole2}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="3" class="bool">
				<c:if test="${row.purchaseRole3}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="4" class="bool">
				<c:if test="${row.purchaseRole4}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="5" class="bool">
				<c:if test="${row.purchaseRole5}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="6" class="bool">
				<c:if test="${row.purchaseRole6}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>
			<display:column sortable="true" title="7" class="bool">
				<c:if test="${row.purchaseRole7}">
					<img src="../images/bullet_done.png" />
				</c:if>
			</display:column>






		</display:table>

