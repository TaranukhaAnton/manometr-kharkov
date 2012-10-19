<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<a href="edit">Добавить пользователя</a>


<display:table name="userList"
               requestURI="./"
               excludedParams="method"
               requestURIcontext="false"
               pagesize="20" sort="list"
               class="simple">
    <display:column property="id" title="ID" sortable="true" url="/users/edit" paramId="id" class="col1"/>
    <display:column property="lastName" title="Фамилия" sortable="true" maxLength="10" class="col2"/>
    <display:column property="name" title="Имя" maxLength="8" class="col3"/>
    <display:column property="patronymic" title="Отчество" maxLength="8" class="col4"/>
    <display:column property="position" title="Должность" class="col5" maxLength="8"/>
    <display:column property="receptionOnWorkDate" title="Дата пр." class="col6"/>
    <display:column property="dischargingDate" title="Дата ув." class="col7"/>
    <display:column property="tel" title="Телефон" class="col8"/>
    <display:column property="telMob" title="Моб. тел." maxLength="10" class="col9"/>
    <display:column property="powLevStr" title="Ур. полн."  sortable="true" class="col10"/>
    <display:column property="login" title="Логин  "     sortable="true"/>
    <display:column title="" url="/UserProcess.do?method=deleteUser"
                    paramId="id" paramProperty="id" class="col12">
        <img src="../images/delete.gif" width="18" height="18" hspace="4"   border="0"/>
    </display:column>
</display:table>