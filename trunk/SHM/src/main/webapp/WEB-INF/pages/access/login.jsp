<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href="css/style.css"/>
	<title>Login</title>
</head>

<body>
	<form class="login-form" action="j_spring_security_check" method="post" accept-charset="UTF-8">
		<fieldset>
			<legend>Манометр Харьков</legend>
			
			<p>
			<label for="j_username">Логин</label>:
			<input id="j_username" name="j_username" size="20" maxlength="50" type="text" AUTOCOMPLETE="off"/>
			</p>
			
			<p>
			<label for="j_password">Пароль</label>:
			<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
			</p>
			
			<p><input type="submit" value="Вход"/></p>
		</fieldset>
	</form>
<%--<%--%>

        <%--String mes = (String) request.getAttribute("message"); %>--%>
	<%--<p class="message">${message}</p>--%>
	<%--<p class="message"><%=mes%></p>--%>
    <c:if test="${hasError}">
        <p class="message">Ошибка при входе</p>
    </c:if>


</body>
</html>