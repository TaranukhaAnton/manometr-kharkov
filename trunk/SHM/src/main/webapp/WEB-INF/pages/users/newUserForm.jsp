<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
<form:form modelAttribute="user" action="add" method="post">
    <fieldset>
        <legend>Account Fields</legend>
        <p>

            <form:hidden path="id"/>
        </p>

        <p>

            <form:input path="name"/>
        </p>

        <p>

            <form:input path="patronymic"/>
        </p>

        <p>


            <form:input path="lastName"/>
        </p>

        <p>


        </p>


        <p>
            <input type="submit"/>
        </p>
    </fieldset>
</form:form>
--%>



<form:form modelAttribute="user" action="add" method="post">
    <form:hidden path="id"/>
    <table>

        <tr>
            <td class="tdLabel">
                Фамилия
            </td>
            <td>

                <form:input path="lastName" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Имя
            </td>
            <td>
                <form:input path="name" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Отчество
            </td>
            <td>
                 <form:input path="patronymic" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Должность
            </td>
            <td>
                <form:input path="position" size="40"/>
            </td>
        </tr>


        <tr>
            <td class="tdLabel">
                Дата приёма
            </td>
            <td>
                <form:input path="receptionOnWorkDate" size="20"/>

                <html:img src="images/datepicker.jpg"
                          onclick="displayDatePicker('receptionOnWorkDate', false, 'dmy', '.');"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Дата увольнения
            </td>
            <td>
                <form:input path="dischargingDate" size="20"/>
                <html:img src="images/datepicker.jpg"
                          onclick="displayDatePicker('dischargingDate', false, 'dmy', '.');"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Телефон
            </td>
            <td>
                <form:input path="tel" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Моб. тел.
            </td>
            <td>
                <form:input path="telMob" size="40"/>

            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Уровень полномочий
            </td>
            <td>
                <form:select path="powersLevel">
                    <form:option value="пользователь"/>
                    <form:option value="менеджер"/>
                    <form:option value="экономист"/>
                    <form:option value="администратор"/>
                    <%--<form:options items="${user.POWER_LEVELS}" />--%>
                </form:select>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Логин
            </td>
            <td>
                <form:input path="login" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Пароль
            </td>
            <td>
                <form:input path="pass" size="40"/>
            </td>
        </tr>


        <tr>
            <td colspan="2">
                <input type="submit"/>
            </td>
        </tr>
    </table>
</form:form>