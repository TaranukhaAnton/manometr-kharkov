<%@ page import="application.data.Supplier" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Simple jsp page</title>
    <link type="text/css" href="/Manometr/css/smartTable.css" rel="stylesheet"/>
    <script type="text/javascript" src="/Manometr/js/ui/jquery-1.4.1.js"></script>


 
     <style type="text/css">
         #content
         {
             margin:10px;
         }





     </style>

</head>
<body>
<%
    GenericHibernateDAO<Supplier> dao = Factory.getSupplierDAO();
    request.setAttribute("supliers", dao.findAll());
%>


 <DIV ID="content">
 		<a href="supplierAction.do?method=editSupplier">Добавить поставщика</a>
		<br>
		<br>
<display:table name="supliers" requestURI="supplierAction.do?method=viewSuppliers" excludedParams="method"
               requestURIcontext="false" pagesize="20" sort="list"
               class="simple">
    <display:column property="alias" title="Ус. название" sortable="true"   url="/supplierAction.do?method=editSupplier" paramId="id" paramProperty="id"/>
    <%--<display:column property="purposeAlias" title="Ус. название" sortable="true" maxLength="15" />--%>
    <display:column property="name" title="Имя" maxLength="15" />
    <display:column title="" url="/supplierAction.do?method=deleteSupplier"
                    paramId="id" paramProperty="id" class="col12">

        <img src="images/delete.gif" width="18" height="18" hspace="4"
             border="0"/>
    </display:column>
</display:table>
 </DIV>

</body>
</html>