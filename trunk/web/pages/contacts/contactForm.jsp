<%@ page import="application.hibernate.Factory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%--<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>--%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<c:set var="actual" value="${! contactForm.status}" scope="page"/>

<c:set var="update"
       value="${!empty contactForm.id && customerForm.id!= 0 ?'true':'false'}"
       scope="page"/>


<%
    request.setAttribute("professions", Factory.getProfessionDAO().findAll());

%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/datepicker.css"/>
    <link rel="stylesheet" type="text/css"
          href="css/jquery.autocomplete.css"/>
    <script src="js/datepicker.js" type="text/javascript" charset="UTF-8"
            language="javascript"></script>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.autocomplete.js" type='text/javascript'></script>
    <script type="text/javascript">
        $().ready(function() {
            $("#customer").autocomplete("CustomerSetUp.do?method=findCustomers", {
                width :260,
                selectFirst :false
            });

            $("#cInput").hide();
            $("#cButton").hide();
            $("#slick-down").click(function() {
                $('#cInput').toggle(400);
                $('#cButton').toggle(400);
                return false;
            });


        });
    </script>


    <style>
        td {
            white-space: nowrap;
        }
    </style>
    <title><c:out value="${insertUpdateTitle}"/>
    </title>
</head>
<body>


<html:form action="/ContactSaveOrUpdate.do">
    <table>

        <tr>
            <td class="tdLabel">
                Фамилия
            </td>
            <td>
                <html:text property="lastName" size="40" disabled="${update}" />
                <font color="#ff0000"><html:errors property="lastName"/></font>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Имя
            </td>
            <td>
                <html:text property="name" size="40" disabled="${update}"/>
                <font color="#ff0000"><html:errors property="name"/></font>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Отчество
            </td>
            <td>
                <html:text property="patronymic" size="40" disabled="${update}"/>
                <font color="#ff0000"> <html:errors property="patronymic"/></font>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Место работы
            </td>
            <td>
                <html:text property="customer" size="40" styleId="customer"
                           disabled="${actual}"/>
                <font color="#ff0000"><html:errors property="customer"/></font>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Должность
            </td>
            <td>
                <html:select property="profession" disabled="${actual}">
                    <html:options collection="professions" property="id"
                                  labelProperty="name"/>
                </html:select>

                    <a href="#" id="slick-down"> <img src="images/add.gif" width="16" height="16" border="0"/></a>
                    <html:text property="newProfession" size="20" disabled="${actual}" styleId="cInput"/>
                    <html:submit value="Добавить" onclick="document.contactForm.method.value='addProfession'"
                                 styleId="cButton"/>
                
                <font color="#ff0000"><html:errors property="profession"/></font>

            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Подразделение
            </td>
            <td>
                <html:text property="subdivision" size="40" disabled="${actual}"/>
                <font color="#ff0000"><html:errors property="subdivision"/></font>
            </td>
        </tr>
        <tr>
            <td>

            </td>
            <td>
                <fieldset style="">
                    <legend>
                        Группа
                    </legend>
                    <html:checkbox property="group1" disabled="${actual}"/>
                    - Техническая
                    <font color="#ff0000"> <html:errors property="group"/> </font>
                    <br>
                    <html:checkbox property="group2" disabled="${actual}"/>
                    - Снабжение
                    <font color="#ff0000"> <html:errors property="group"/> </font>
                    <br>
                    <html:checkbox property="group3" disabled="${actual}"/>
                    - Руководитель
                    <font color="#ff0000"> <html:errors property="group"/> </font>
                    <br>
                </fieldset>
            </td>
        </tr>

        <tr>
            <td class="tdLabel">
                Телефон
            </td>
            <td>
                <html:text property="tel" size="40" disabled="${actual}"/>
                <font color="#ff0000"> <html:errors property="tel"/> </font>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Факс
            </td>
            <td>
                <html:text property="fax" size="40" disabled="${actual}"/>
                <html:errors property="fax"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                Мобильный
            </td>
            <td>
                <html:text property="mobTel" size="40" disabled="${actual}"/>
                <html:errors property="mobTel"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel">
                e-mail
            </td>
            <td>
                <html:text property="EMail" size="40" disabled="${actual}"/>
                <html:errors property="eMail"/>
            </td>
        </tr>

        <tr>
            <td>

            </td>
            <td>
                <fieldset style="">
                    <legend>
                        Роль в закупках
                    </legend>
                    <html:checkbox property="purchaseRole1" disabled="${actual}"/>
                    - Клерк (техническая работа)
                    <br>
                    <html:checkbox property="purchaseRole2" disabled="${actual}"/>
                    - Влияет на техническую оценку продукции
                    <br>
                    <html:checkbox property="purchaseRole3" disabled="${actual}"/>
                    - Влияет на коммерческую оценку предложения
                    <br>
                    <html:checkbox property="purchaseRole4" disabled="${actual}"/>
                    - Формирует техническую политику
                    <br>
                    <html:checkbox property="purchaseRole5" disabled="${actual}"/>
                    - Формирует политику закупок
                    <br>
                    <html:checkbox property="purchaseRole6" disabled="${actual}"/>
                    - Член тендерного комитета
                    <br>
                    <html:checkbox property="purchaseRole7" disabled="${actual}"/>
                    - Единолично принимает решение
                    <br>
                </fieldset>
            </td>
        </tr>


        <tr>
            <td class="tdLabel">
                Актуальность записи
            </td>
            <td>
                <html:checkbox property="status" disabled="${actual}"/>
            </td>
        </tr>


        <tr>
            <td colspan="2">
                <html:hidden property="method" value="insertOrUpdate" />
                <%--<bean:input type="hidden" name="method" value="insertOrUpdate"></bean:input>--%>
                <html:hidden property="id"/>

                <c:if test="${! actual}">
                    <html:submit>
                        <bean:message key="button.submit"/>
                        <%--Применить  22--%>
                    </html:submit>
                </c:if>

                <html:cancel>
                    <bean:message key="button.cancel"/>
                    <%--Отмена--%>
                </html:cancel>
            </td>
        </tr>
    </table>
</html:form>
</body>
</html>