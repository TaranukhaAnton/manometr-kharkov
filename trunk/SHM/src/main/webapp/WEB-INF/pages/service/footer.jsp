<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--<div align="left">--%>
    <%--Здравствуйте--%>
    <%--<bean:write name="logonUser" property="login"/>--%>
<%--</div>--%>
<sec:authentication property="principal" var="user" scope="request"/>
<div align="left">

    Вы вошли как   ${user.username}
</div>
