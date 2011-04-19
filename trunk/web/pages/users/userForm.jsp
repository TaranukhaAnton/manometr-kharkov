<%@ page import="application.data.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<c:set var="insertUpdateTitle"
       value="${!empty userForm.id && userForm.id != 0 ?'Редактировать личные данные пользователя':'Добавить пользователя'}"/>


<%
    request.setAttribute("powersLivels", User.PowersLivel.values());
%>

<html>
<head>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="css/datepicker.css"/>

    <script src="js/datepicker.js" type="text/javascript" charset="UTF-8" language="javascript"></script>
    <style>
        td {
            white-space: nowrap;
        }
    </style>
    <title><c:out value="${insertUpdateTitle}"/>
    </title>
</head>
<body>


<html:form action="/UserSetUp.do?method=insertOrUpdate">
    <table>

        <tr>
            <td class="tdLabel">
                Фамилия
            </td>
            <td>
                <html:text property="lastName" size="40"/>
                <font color="#ff0000"> <html:errors property="lastName"/> </font>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Имя
            </td>
            <td>
                <html:text property="name" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Отчество
            </td>
            <td>
                <html:text property="patronymic" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Должность
            </td>
            <td>
                <html:text property="position" size="40"/>
            </td>
        </tr>


        <tr>
            <td class="tdLabel">
                Дата приёма
            </td>
            <td>
                <html:text property="receptionoOnWorkDate" size="20"/>
                <html:img src="images/datepicker.jpg"
                          onclick="displayDatePicker('receptionoOnWorkDate', false, 'dmy', '.');"/>
                <html:errors property="receptionoOnWorkDate"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Дата увольнения
            </td>
            <td>
                <html:text property="dischargingDate" size="20"/>
                <html:img src="images/datepicker.jpg"
                          onclick="displayDatePicker('dischargingDate', false, 'dmy', '.');"/>
                <html:errors property="dischargingDate"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Телефон
            </td>
            <td>
                <html:text property="tel" size="40"/>
                <font color="#ff0000"><html:errors property="tel"/></font>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Моб. тел.
            </td>
            <td>
                <html:text property="telMob" size="40"/>

            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Уровень полномочий
            </td>
            <td>
                <html:select property="powersLivel" size="1">
                    <html:options name="powersLivels"/>
                </html:select>

            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Логин
            </td>
            <td>
                <html:text property="login" size="40"/>
                <font color="#ff0000"><html:errors property="login"/> </font>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Пароль
            </td>
            <td>
                <html:text property="pass" size="40"/>
                <font color="#ff0000"> <html:errors property="pass"/></font>
            </td>
        </tr>


        <tr>
            <td colspan="2">
                <html:hidden property="id"/>

                <html:submit>
                    <bean:message key="button.submit"/>
                </html:submit>


                <html:cancel>
                    <bean:message key="button.cancel"/>
                </html:cancel>
            </td>
        </tr>
    </table>
</html:form>

</body>
</html>
 