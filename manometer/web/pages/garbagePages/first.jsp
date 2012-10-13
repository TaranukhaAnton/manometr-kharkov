
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<tiles:insert page="/pages/service/baseLayout.jsp" flush="true">
	<tiles:put name="header" value="/pages/service/header.jsp" />
	<tiles:put name="menu" value="/pages/service/menu.jsp" />
	<tiles:put name="body" value="/pages/garbage/Welcome.jsp" />
	<tiles:put name="footer" value="/pages/service/footer.jsp" />
</tiles:insert>
