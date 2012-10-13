<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tiles:insert page="/pages/garbage/baseLayout.jsp" flush="true">
    <tiles:put name="title" value="Tiles Example" />
    <tiles:put name="header" value="/pages/garbage/header.jsp" />
    <tiles:put name="menu" value="/pages/garbage/menu.jsp" />
    <tiles:put name="body" value="/pages/garbage/friends.jsp" />
    <tiles:put name="footer" value="/pages/garbage/footer.jsp" />
</tiles:insert>