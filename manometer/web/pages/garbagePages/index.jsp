<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tiles:insert page="/pages/garbage/baseLayout.jsp" flush="true">
    <tiles:put name="title"     value="This is the title."/>
    <tiles:put name="header"    value="/pages/service/header.jsp"/>
    <tiles:put name="menu"      value="/pages/service/menu.jsp"/>
    <tiles:put name="body"      value="/pages/priceRedactor/redactListPrice.jsp"/>
    <tiles:put name="footer"    value="/pages/service/footer.jsp"/>
</tiles:insert>