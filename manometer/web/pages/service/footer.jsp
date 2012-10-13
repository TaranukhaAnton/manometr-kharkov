

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	</head>
	<body>
		<logic:present name="logonUser">
				
			<div align="left">
				Здравствуйте
				<bean:write name="logonUser" property="login" />
			</div>
		</logic:present>
		
		<logic:notPresent name="logonUser">
				
			<div align="left">
				Здравствуйте гость.
			</div>
		</logic:notPresent>




	</body>
</html>
