<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<form:form modelAttribute="contact" action="add" method="post">
    <%--<form:hidden path="id"/>--%>
    <table>
                <form:hidden path="id" size="40"/>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="customer" size="40"/>
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

            </td>
            <td>
                <form:input path="patronymic" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="lastName" size="40"/>
            </td>
        </tr>
        <%--<tr>--%>
            <%--<td class="tdLabel">--%>
                <%--profession--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<form:input path="profession.id" size="40"/>--%>
            <%--</td>--%>
        <%--</tr>--%>

        <tr>
            <td class="tdLabel">
                profession
            </td>
            <td>
                <form:select path="profession.id">
                    <form:options items="${professions}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="subdivision" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="group1" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="group2" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="group3" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="tel" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="fax" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="mobTel" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="eMail" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole1" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole2" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole3" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole4" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole5" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole6" size="40"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">

            </td>
            <td>
                <form:input path="purchaseRole7" size="40"/>
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
                <form:input path="method" size="40"/>
            </td>
        </tr>
    </table>
    <input type="submit"/>


</form:form>