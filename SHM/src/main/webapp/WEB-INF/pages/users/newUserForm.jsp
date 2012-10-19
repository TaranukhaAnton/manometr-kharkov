<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">


$(function() {
    $("#dischargingDate").datepicker({
        showOn: 'button',
        buttonImage: '../images/datepicker.jpg',
        buttonImageOnly: true
    });
    $("#receptionOnWorkDate").datepicker({
        showOn: 'button',
        buttonImage: '../images/datepicker.jpg',
        buttonImageOnly: true
    });
}  );
</script>



<form:form modelAttribute="user" action="add" method="post">
    <form:hidden path="id"/>
    <form:hidden path="invoiceFilter.id"/>

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
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Дата увольнения
            </td>
            <td>
                <form:input path="dischargingDate" size="20" />
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
                    <form:option value="1" label="пользователь"/>
                    <form:option value="2" label="менеджер"/>
                    <form:option value="3" label="экономист"/>
                    <form:option value="4" label="администратор"/>
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