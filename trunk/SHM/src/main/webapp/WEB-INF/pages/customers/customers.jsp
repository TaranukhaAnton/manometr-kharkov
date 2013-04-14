<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<a href="edit">Добавить предприятие</a>
<br>
<br>
<display:table name="listCustomer" id="row"
               requestURI="./"     keepStatus="true"
               excludedParams="method" requestURIcontext="false" pagesize="24"
               sort="list" class="simple" defaultsort="3">

    <display:column title="Ст" sortable="true" class="bool2">
        <c:if test="${row.status}">
            <img src="../images/bullet_done.png" />
        </c:if>
    </display:column>
    <display:column title="Нов" sortable="true" class="bool2"  >
        <c:if test="${row.new}">
            <img src="../images/bullet_done.png" />
        </c:if>
    </display:column>

    <display:column property="shortName" title="кор название" url="/customers/edit" paramId="id" paramProperty="id" sortable="true"/>
    <display:column property="branchString" title="отр"/>
    <display:column property="person.login" title="спец. OCO"/>
    <display:column property="nomList" title="№ списка"/>
    <display:column property="prospect" title="Потенциал"/>
</display:table>

</body>
</html>