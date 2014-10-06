<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="../js/local/customer.js"></script>
<script type="text/javascript" src="../js/select-chain.js"></script>

<style type="text/css">


    #variableHeightDiv {
        overflow-y: auto;
    }

    #footerButtons {
        height: 30px;
    }

    .tdLabel {
        width: 250px;
    }

    .dropdown {
        width: 200px;

    }

</style>


<div id="bodyDiv">

<c:set var="isNew" value="${empty customer.id}"/>


<form:form modelAttribute="customer" action="add" method="post" onsubmit="return validateForm()"  id="customerForm">
<form:hidden path="id"/>
<form:hidden path="oldRecord.id"/>
<form:hidden path="headCustomer.id"/>
<form:hidden path="registrationNumber"/>
<form:hidden path="dateOfRecord" />
<form:hidden path="dateLastCorrection" />

<div id="variableHeightDiv">
<table>

<tr>
    <td class="tdLabel">
        Короткое название
    </td>
    <td>
        <c:if test="${isNew}">
            <form:input path="shortName" id = "shortName"  size="40"/>
        </c:if>
        <c:if test="${!isNew}">
            <form:hidden path="shortName"/>
            <c:out value="${customer.shortName}"/>
        </c:if>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Организационная форма
    </td>
    <td>

            <form:select path="orgForm.id" id="orgForm" cssClass="dropdown">
                <form:options items="${orgForms}" itemValue="id" itemLabel="name"/>
            </form:select>
            <img src="../images/add.gif" width="16" height="16" border="0" onclick="javascript:void($('#OrgFormInput').dialog('open'))"/>

    </td>
</tr>
<tr>
    <td class="tdLabel">
        Полное название
    </td>
    <td>

            <form:input path="name" size="40"/>

    </td>
</tr>
<tr>
    <td class="tdLabel">
        Полное название укр.
    </td>
    <td>

            <form:input path="nameUkr" size="40"/>


    </td>
</tr>
<tr>
    <td class="tdLabel">
        Доля госсобственности
    </td>
    <td>
        <form:input path="stateProperty" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Код ОКПО
    </td>
    <td>
        <form:input path="codeOKPO" size="40"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Отрасль
    </td>
    <td>
        <form:select path="branch" cssClass="dropdown">
            <form:options items="${branches}"/>

        </form:select>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>

        <fieldset>
            <legend>
                Где использует
            </legend>
            <form:checkbox path="applicationProcess"/>
            - процессы
            <br>
            <form:checkbox path="applicationProduction"/>
            - продукция
            <br>
            <form:checkbox path="applicationProject"/>
            - проекты
            <br>
            <form:checkbox path="applicationEngineering"/>
            - инжиниринг
            <br>


        </fieldset>


    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <fieldset>
            <legend>
                Цель покупок
            </legend>
            <form:checkbox path="purposeForItself"/>
            - для себя
            <br>
            <form:checkbox path="purposeIntermediary"/>
            - посредник
            <br>
            <form:checkbox path="purposeSupply"/>
            - снабжение
            <br>
            <form:checkbox path="purposeInstallation"/>
            - монтаж
            <br>
        </fieldset>


    </td>
</tr>

<tr>
    <td class="tdLabel">Номер списка</td>
    <td>

        <form:select path="nomList" cssClass="dropdown">
            <form:option value="1"/>
            <form:option value="2"/>
            <form:option value="3"/>
            <form:option value="4"/>
        </form:select>
    </td>
</tr>

<tr>
    <td class="tdLabel">Перспектива</td>
    <td>
        <form:select path="prospect" cssClass="dropdown">
            <form:option value="1" label="A"/>
            <form:option value="2" label="B"/>
            <form:option value="3" label="C"/>
        </form:select>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Регион
    </td>
    <td>
        <form:select path="region" cssClass="dropdown">
            <form:options items="${regions}" itemValue="id" itemLabel="name"/>
        </form:select>
         <img src="../images/add.gif" width="16" height="16" border="0" onclick="javascript:void($('#RegionInput').dialog('open'))"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">Страна</td>
    <td>
        <form:select path="country" id="country" cssClass="dropdown">
            <form:options items="${countries}" itemValue="id" itemLabel="name"/>
        </form:select>

        <img src="../images/add.gif" width="16" height="16" border="0" onclick="javascript:void($('#CountryInput').dialog('open'))"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">Область</td>
    <td>
        <form:select path="area" id="area" cssClass="dropdown">
            <form:options items="${areas}" itemValue="id" itemLabel="name"/>
        </form:select>
        <img src="../images/add.gif" width="16" height="16" border="0" onclick="javascript:void($('#AreaInput').dialog('open'))"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        <form:select path="localityType" cssClass="dropdown">
            <form:option value="0" label="Город"/>
            <form:option value="1" label="ПГТ"/>
            <form:option value="2" label="Пос."/>
            <form:option value="3" label="Село"/>
        </form:select>
    </td>
    <td>
        <form:select path="city" id="city" cssClass="dropdown">
            <form:options items="${cities}" itemValue="id" itemLabel="name"/>
        </form:select>
        <img src="../images/add.gif" width="16" height="16" border="0" onclick="javascript:void($('#CityInput').dialog('open'))"/>
     </td>
</tr>

<tr>
    <td class="tdLabel">
        Адрес дирекции
    </td>
    <td>
        <form:input path="address1" size="40" cssClass="fillone"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Адрес комер. службы
    </td>
    <td>
        <form:input path="address2" size="40" cssClass="fillone"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Адрес тех службы
    </td>
    <td>
        <form:input path="address3" size="40" cssClass="fillone"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Дата поглощения
    </td>
    <td>
        <form:input path="mergeData" size="40"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Актуальность записи
    </td>
    <td>
        <form:checkbox path="status"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Старая запись
    </td>
    <td>
        <form:input path="oldRecord.shortName" id="oldRecord" size="40"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Спец. ОСО
    </td>
    <td>


        <form:select path="person.id" cssClass="dropdown">
            <form:options items="${users}" itemValue="id" itemLabel="login"/>
        </form:select>
    </td>
</tr>



<tr>
    <td class="tdLabel">Реквизиты</td>
    <td>
        <form:input path="requisite" size="40"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">Сайт</td>
    <td>
        <form:input path="site" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">Новое предприятие</td>
    <td>
        <form:checkbox path="New" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">Раск.инф</td>
    <td>
        <form:input path="moreInformation" size="40"/>
    </td>
</tr>
</table>
</div>

<div id="footerButtons">
    <input type="submit" value="Применить"/>
    <input type="button" value="Отмена" onclick="javascript:void(location.replace('./'))" >

    <c:if test="${!isNew}">
        <input type="button" value="Контактные лица" onclick="javascript:void(location.replace('../contacts/?customer=${customer.shortName}'))" >
    </c:if>



</div>
</form:form>
</div>

<div id="CountryInput" title="Добавить страну">
    <input type="text" id="newCountry"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">
</div>

<div id="RegionInput" title="Добавить регион">
    <input type="text" id="newRegion"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">

</div>

<div id="AreaInput" title="Добавить область">
    <input type="text" id="newArea"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">
</div>

<div id="CityInput" title="Добавить город">
    <table>
        <tr>
            <td>
                рус.
            </td>
            <td>
                <input type="text" id="newCityRus"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">
            </td>
        </tr>
        <tr>
            <td>
                укр.
            </td>
            <td>
                <input type="text" id="newCityUkr"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">
            </td>
        </tr>
    </table>



</div>

<div id="OrgFormInput" title="Добавить орг. форму">
    <table>
        <tr>
            <td>
                рус.
            </td>
            <td>
                <input type="text" id="newOrgFormRus"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">
            </td>
        </tr>
        <tr>
            <td>
                укр.
            </td>
            <td>
                <input type="text" id="newOrgFormUkr"  style="width: 300px; height: 35px; font-size: 30px;" AUTOCOMPLETE="off">
            </td>
        </tr>
    </table>


</div>

