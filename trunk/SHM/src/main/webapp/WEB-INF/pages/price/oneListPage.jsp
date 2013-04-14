<%@ page import="ua.com.manometer.model.price.ProductionPrice" %>
<%@ page import="java.util.Enumeration" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 27.10.12
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="height: 600px;">

    <%String id = (String) request.getParameter("type"); %>

    <a href="<c:out value="javascript:void(view(${type}))"/>">Добавить</a><br>
    <display:table name="list" requestURI="./"
                   excludedParams="type" keepStatus="true"
                   requestURIcontext="false" pagesize="16" sort="list" class="simple" id="table${type}">
        <display:column title="Обозначение" class="col1">


            <a href="javascript:void(view(<%=
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getType()+","+
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getId()+",'"+
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getName()+"',"+
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getCost()+","+
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getPrice() %>))">
                <%= ((ProductionPrice) pageContext.getAttribute("table" + id)).getName() %>
            </a>
        </display:column>
        <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
        <display:column property="price" title="Цена" class="col2" maxLength="8"/>
        <display:column title="" class="col13">
            <a href="javascript:void(del(<%=
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getId()+","+
            ((ProductionPrice) pageContext.getAttribute("table"+id)).getType() %>))">
                <img src="../images/delete.gif" width="18" height="18" hspace="4" border="0"/>
            </a>
        </display:column>
    </display:table>

</div>