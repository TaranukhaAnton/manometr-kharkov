<%@ page import="ua.com.manometer.model.Supplier" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<form:form modelAttribute="supplier" action="add" method="post">

<form:hidden path="id"/>
<form:hidden path="def"/>

<div id="content">
<table>
<tr>
    <td>Условное название</td>
    <td>
        <form:input path="alias" size="120"/>
    </td>
</tr>
<tr>
    <td>Название</td>
    <td>
        <form:input path="name" size="120"/>
    </td>

</tr>
<tr>
    <td>Адрес</td>
    <td>
        <form:input path="address" size="120"/>
    </td>
</tr>
<tr>
    <td>Телефоны для связи</td>
    <td>
        <form:input path="phone" size="120"/>
    </td>
</tr>
<tr>
    <td>Код ЕРДПОУ</td>
    <td>
        <form:input path="erdpou" size="120"/>
    </td>
</tr>
<tr>
    <td>ИНН</td>
    <td>
        <form:input path="inn" size="120"/>
    </td>
</tr>
<tr>
    <td>ИНН/КПП</td>
    <td>
        <form:input path="innkpp" size="120"/>
    </td>
</tr>
<tr>
    <td>ОКПО</td>
    <td>
        <form:input path="okpo" size="120"/>
    </td>

</tr>
<tr>
    <td>ОГРН</td>
    <td>
        <form:input path="ogrn" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк посредник, название</td>
    <td>
        <form:input path="bankMediatorName" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк посредник, город, страна</td>
    <td>
        <form:input path="bankMediatorAddress" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк посредник, SWIFT</td>
    <td>
        <form:input path="bankMediatorSWIFT" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк получатель, название</td>
    <td>
        <form:input path="bankBeneficiaryName" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк получатель, город, страна</td>
    <td>
        <form:input path="bankBeneficiaryAddress" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк получатель, SWIFT</td>
    <td>
        <form:input path="bankBeneficiarySWIFT" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк получатель, асс</td>
    <td>
        <form:input path="bankBeneficiaryASS" size="120"/>
    </td>
</tr>
<tr>
    <td>р/с</td>
    <td>
        <form:input path="rs" size="120"/>
    </td>
</tr>
<tr>
    <td>т/с</td>
    <td>
        <form:input path="ts" size="120"/>
    </td>
</tr>
<tr>
    <td>Банк</td>
    <td>
        <form:input path="bank" size="120"/>
    </td>
</tr>
<tr>
    <td>МФО банка</td>
    <td>
        <form:input path="mfoBank" size="120"/>
    </td>
</tr>
<tr>
    <td>БИК</td>
    <td>
        <form:input path="BIC" size="120"/>
    </td>
</tr>
<tr>
    <td>к/с</td>
    <td>
        <form:input path="ks" size="120"/>
    </td>
</tr>
<tr>
    <td>Валюта р/с</td>
    <td>
        <form:select path="currency.id" cssStyle="width: 140px;">
            <form:options items="${currencies}" itemValue="id" itemLabel="name"/>
        </form:select>
    </td>
</tr>
<tr>
    <td>Язык ввода/с</td>
    <td>
        <form:select path="language" cssClass="dropdown" cssStyle="width: 140px;">
            <form:option value="ru" label="Русский"/>
            <form:option value="ua" label="Украинский"/>
            <form:option value="en" label="Английский"/>
        </form:select>

    </td>
</tr>
    <%--<tr>--%>
    <%--<td>Логотип</td>--%>
    <%--<td>--%>
    <%--<select name="logo" id="logo" onchange="changeImage()">--%>

    <%--<option value="1" <%=supplier.getLogo().equals("1") ? "selected" : ""%> >Логотип 1</option>--%>
    <%--<option value="2" <%=supplier.getLogo().equals("2") ? "selected" : ""%> >Логотип 2</option>--%>
    <%--<option value="3" <%=supplier.getLogo().equals("3") ? "selected" : ""%> >Логотип 3</option>--%>
    <%--<option value="4" <%=supplier.getLogo().equals("4") ? "selected" : ""%> >Логотип 4</option>--%>
    <%--</select>--%>

    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td></td>--%>
    <%--<td>--%>

    <%--<IMG id="preview"--%>
    <%--src="images/reportImages/preview_image<%=(supplier.getLogo().isEmpty())?"1":supplier.getLogo()%>.jpg">--%>


    <%--</tr>--%>

<tr>
    <td>Схема налогообложения</td>
    <td>
        <form:input path="taxationScheme" size="120"/>
    </td>
</tr>
<tr>
    <td>Должность начальника</td>
    <td>
        <form:input path="chief" size="120"/>
    </td>
</tr>
<tr>
    <td>ФИО начальника</td>
    <td>
        <form:input path="FIOChief" size="120"/>
    </td>
</tr>
<tr>
    <td>Должность начальника 2</td>
    <td>
        <form:input path="secondChief" size="120"/>
    </td>
</tr>
<tr>
    <td>ФИО начальника 2</td>
    <td>
        <form:input path="secondFIOChief" size="120"/>
    </td>
</tr>

<tr>
    <td>№ свидетельства плательщика НДС &nbsp; &nbsp; &nbsp; &nbsp;</td>
    <td>
        <form:input path="NDSPayerNom" size="120"/>
    </td>
</tr>


</table>
</div>


<input type="submit" value="Записать"/>
<input type="button" value="Отмена" onclick=" window.location.replace( '../suppliers/' );">

</form:form>
