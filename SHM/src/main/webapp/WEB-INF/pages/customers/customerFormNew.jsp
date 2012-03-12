<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>





<style type="text/css">


    #variableHeightDiv {
        overflow-y: auto;
    }

    #footerButtons {
        height: 30px;
    }
</style>


<div id="bodyDiv">
<form:form modelAttribute="customer" action="add" method="post" id="customerForm">
<form:hidden path="id" size="40"/>
<form:hidden path="oldRecord.id" size="40"/>
<form:hidden path="headCustomer.id" size="40"/>

<div id="variableHeightDiv">
<table>

<tr>
    <td class="tdLabel">
        Короткое название
    </td>
    <td>
        <form:input path="shortName" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Организационная форма
    </td>
    <td>
        <form:select path="orgForm.id">
            <form:options items="${orgForms}" itemValue="id" itemLabel="name"/>
        </form:select>
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
        Доля госсобственности
    </td>
    <td>
        <form:input path="stateProperty" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
      Отрасль

    </td>
    <td>
        <form:select path="branch">
            <form:options items="${branches}"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <%--<form:input path="purposeForItself" size="40"/>--%>


            <fieldset >
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
        <%--<form:input path="purposeIntermediary" size="40"/>--%>
            <fieldset >
                <legend>
                    Цель покупок
                </legend>
                <form:checkbox path="purposeForItself" />
                - для себя
                <br>
                <form:checkbox path="purposeIntermediary" />
                - посредник
                <br>
                <form:checkbox path="purposeSupply" />
                - снабжение
                <br>
                <form:checkbox path="purposeInstallation" />
                - монтаж
                <br>
            </fieldset>




    </td>
</tr>

<tr>
    <td class="tdLabel">nomList</td>
    <td>
        <form:input path="nomList" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">New</td>
    <td>
        <form:input path="New" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">prospect</td>
    <td>
        <form:input path="prospect" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">country</td>
    <td>
        <form:select path="country" id="country">
            <form:options items="${countries}" itemValue="id" itemLabel="name"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdLabel">area</td>
    <td>
        <form:select path="area" id="area">
            <form:options items="${areas}" itemValue="id" itemLabel="name"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdLabel">city</td>
    <td>
        <form:select path="city" id="city">
            <form:options items="${cities}" itemValue="id" itemLabel="name"/>
        </form:select>

    </td>
</tr>

<tr>
    <td class="tdLabel">
     Регион
    </td>
    <td>
        <form:select path="region">
            <form:options items="${regions}" itemValue="id" itemLabel="name"/>
        </form:select>

    </td>
</tr>
<tr>
    <td class="tdLabel">
        localityType
    </td>
    <td>
        <form:input path="localityType" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <form:input path="address1" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <form:input path="address2" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <form:input path="address3" size="40"/>
    </td>
</tr>
<%--<tr>--%>
    <%--<td class="tdLabel">--%>

    <%--</td>--%>
    <%--<td>--%>
        <%--<form:input path="headCustomer" size="40"/>--%>
    <%--</td>--%>
<%--</tr>--%>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <form:input path="mergeData" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <form:input path="questionnaire" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <form:input path="status" size="40"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Старая запись
    </td>
    <td>
        <form:input path="oldRecord.shortName" id="oldRecord"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Спец. ОСО
    </td>
    <td>


        <form:select path="person.id">
            <form:options items="${users}" itemValue="id" itemLabel="login"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdLabel">dateOfRecord</td>
    <td>
        <form:input path="dateOfRecord" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">dateLastCorrection</td>
    <td>
        <form:input path="dateLastCorrection" size="40"/>
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
    <td class="tdLabel">requisite</td>
    <td>
        <form:input path="requisite" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">registrationNumber</td>
    <td>
        <form:input path="registrationNumber" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">site</td>
    <td>
        <form:input path="site" size="40"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">moreInformation</td>
    <td>
        <form:input path="moreInformation" size="40"/>
    </td>
</tr>
</table>
</div>

<div id="footerButtons">
    <input type="submit"/>
</div>
</form:form>
</div>
