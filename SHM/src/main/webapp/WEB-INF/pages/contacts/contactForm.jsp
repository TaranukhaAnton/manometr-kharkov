<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isNew" value="${empty contact.id}"/>

<form:form modelAttribute="contact" action="add" method="post">


    <table>
        <form:hidden path="id"/>
        <form:hidden path="oldRecord.id"/>
        <form:hidden path="method"/>

        <tr>
            <td class="tdLabel">
                Имя
            </td>
            <td>
                <c:if test="${isNew}">
                    <form:input path="name" size="40"/>
                </c:if>
                <c:if test="${!isNew}">
                    <form:hidden path="name" />
                    <c:out value="${contact.name}" />
                </c:if>

            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Отчество
            </td>
            <td>
                <c:if test="${isNew}">
                    <form:input path="patronymic" size="40"/>
                </c:if>
                <c:if test="${!isNew}">
                    <form:hidden path="patronymic" />
                    <c:out value="${contact.patronymic}" />
                </c:if>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Фамилия
            </td>
            <td>
                <c:if test="${isNew}">
                    <form:input path="lastName" size="40"/>
                </c:if>
                <c:if test="${!isNew}">
                    <form:hidden path="lastName" />
                    <c:out value="${contact.lastName}" />
                </c:if>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Место работы
            </td>
            <td>
                <form:input path="customer" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Должность
            </td>
            <td>
                <form:select path="profession.id">
                    <form:options items="${professions}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Подразделение
            </td>
            <td>
                <form:input path="subdivision" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <fieldset style="">
                    <legend>
                        Группа
                    </legend>
                    <form:checkbox path="group1"/> - Техническая <br>
                    <form:checkbox path="group2"/> - Снабжение <br>
                    <form:checkbox path="group3"/> - Руководитель <br>
                </fieldset>
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
                Факс
            </td>
            <td>
                <form:input path="fax" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Мобильный
            </td>
            <td>
                <form:input path="mobTel" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                e-mail
            </td>
            <td>
                <form:input path="eMail" size="40"/>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <fieldset style="">
                    <legend>
                        Роль в закупках
                    </legend>
                    <form:checkbox path="purchaseRole1"/> - Клерк (техническая работа) <br>
                    <form:checkbox path="purchaseRole2"/> - Влияет на техническую оценку продукции <br>
                    <form:checkbox path="purchaseRole3"/> - Влияет на коммерческую оценку предложения <br>
                    <form:checkbox path="purchaseRole4"/> - Формирует техническую политику <br>
                    <form:checkbox path="purchaseRole5"/> - Формирует политику закупок <br>
                    <form:checkbox path="purchaseRole6"/> - Член тендерного комитета <br>
                    <form:checkbox path="purchaseRole7"/> - Единолично принимает решение <br>
                </fieldset>

            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Актуальность записи
            </td>
            <td>
                <form:checkbox path="status" size="40"/>
            </td>
        </tr>

    </table>
    <input type="submit"/>


</form:form>