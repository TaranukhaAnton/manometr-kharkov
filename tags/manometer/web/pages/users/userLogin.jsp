<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
                       
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>JSP for UserLoginForm form</title>


</head>
<body>
	<html:form action="/userLogin.do?method=login" method="post">
		<table border="1" cellpadding="4">
			<tbody>
				<tr>
					<td align="right">
						Логин:
					</td>
					<td>
						<html:text property="userName" />
					</td>
					<td>
					<font color="#ff0000">	<html:errors property="userName" /></font>
					</td>
				</tr>
				<tr>
					<td align="right">
						Пароль:
					</td>
					<td>
						<html:password property="password" />
					</td>
					<td>
						<font color="#ff0000"><html:errors property="password" /></font>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<html:submit>
							Войти
						</html:submit>

					</td>
				</tr>
			</tbody>
		</table>
	</html:form>
</body>
</html:html>
