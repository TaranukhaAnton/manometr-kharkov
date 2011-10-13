<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<a href="UserSetUp.do?method=setUpForInsertOrUpdate">Добавить пользователя</a>


<display:table name="userList"
               requestURI="UserProcess.do?method=getUsers"
               excludedParams="method"
               requestURIcontext="false"
               pagesize="20" sort="list"
               class="simple">
    <display:column property="id" title="ID" sortable="true"    url="/UserSetUp.do?method=setUpForInsertOrUpdate" paramId="id" class="col1"/>
    <display:column property="lastName" title="Фамилия" sortable="true" maxLength="10" class="col2"/>
    <display:column property="name" title="Имя" maxLength="8" class="col3"/>
    <display:column property="patronymic" title="Отчество" maxLength="8" class="col4"/>
    <display:column property="position" title="Должность" class="col5" maxLength="8"/>
    <display:column property="receptionoOnWorkDate" title="Дата пр."
                    format="{0,date,dd.MM.yyyy}" class="col6"/>
    <display:column property="dischargingDate" title="Дата ув."
                    format="{0,date,dd.MM.yyyy}" class="col7"/>
    <display:column property="tel" title="Телефон" class="col8"/>
    <display:column property="telMob" title="Моб. тел." maxLength="10" class="col9"/>
    <display:column property="powersLivel" title="Ур. полн."
                    sortable="true" class="col10"/>
    <display:column property="login" title="Логин  "
                    sortable="true"/>
    <display:column title="" url="/UserProcess.do?method=deleteUser"
                    paramId="id" paramProperty="id" class="col12">

        <img src="images/delete.gif" width="18" height="18" hspace="4"
             border="0"/>
    </display:column>
</display:table>