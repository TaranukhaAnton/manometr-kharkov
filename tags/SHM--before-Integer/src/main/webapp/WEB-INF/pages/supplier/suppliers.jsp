<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="edit">Добавить поставщика</a>

<display:table
        keepStatus="true"
        name="suppliers"
        requestURI="./" excludedParams="method"
               requestURIcontext="false" pagesize="20" sort="list" id="row"
               class="simple">
    <display:column property="alias" title="Ус. название" sortable="true"
                    url="/suppliers/edit" paramId="id" paramProperty="id"/>
    <display:column property="name" title="Имя" maxLength="15" />
    <display:column title="по умолчанию" >
        <c:if test="${row.def}">
            <img src="../images/bullet_done.png" />
        </c:if>
    </display:column>
    <display:column title="" url="/suppliers/delete"         paramId="supplier_id" paramProperty="id" >
        <img src="../images/delete.gif" width="18" height="18" hspace="4"
             border="0"/>
    </display:column>

</display:table>
