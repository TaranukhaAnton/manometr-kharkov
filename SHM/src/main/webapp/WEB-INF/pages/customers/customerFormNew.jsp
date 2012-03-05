<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<form:form modelAttribute="customer" action="add" method="post">
<form:hidden path="id" size="40"/>

<table>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="orgForm" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="name" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="shortName" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="stateProperty" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="branch" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="purposeForItself" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="purposeIntermediary" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="purposeSupply" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="purposeInstallation" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="applicationProcess" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="applicationProduction" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="applicationProject" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="applicationEngineering" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="nomList" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="New" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="prospect" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="country" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="city" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="area" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="region" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

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
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="headCustomer" size="40"/>
        </td>
    </tr>
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

        </td>
        <td>
            <form:input path="oldRecord" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="person" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="dateOfRecord" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="dateLastCorrection" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="codeOKPO" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="requisite" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="registrationNumber" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="site" size="40"/>
        </td>
    </tr>
    <tr>
        <td class="tdLabel">

        </td>
        <td>
            <form:input path="moreInformation" size="40"/>
        </td>
    </tr>
</table>
<input type="submit"/>
</form:form>