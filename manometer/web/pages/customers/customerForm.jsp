<%@ page import="application.data.Customer" %>
<%@ page import="application.hibernate.Factory" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<c:set var="actual"
       value="${!empty customerForm.status && customerForm.status ?'false':'true'}"
       scope="page"/>

<c:set var="update"
       value="${!empty customerForm.id && customerForm.id!= 0 ?'true':'false'}"
       scope="page"/>

<%

    request.setAttribute("contries", Factory.getCountryDAO().findAll());
    request.setAttribute("regions",Factory.getRegionDAO().findAll());
    request.setAttribute("users", Factory.getUserDAO().findAll());
    request.setAttribute("branches", Customer.Branch.values());
    request.setAttribute("prospects", Customer.Prospect.values());
    request.setAttribute("orgForms", Factory.getOrgFormDAO().findAll());
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Details</title>
    <%--<link href="css/style.css" rel="stylesheet" type="text/css"/>--%>
    <link href="css/datepicker.css" rel="stylesheet" type="text/css"/>
    <script src="js/datepicker.js" type="text/javascript"></script>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.autocomplete.js" type='text/javascript'></script>
    <script src="js/select-chain.js" type="text/javascript"></script>


    <link rel="stylesheet" type="text/css"
          href="css/jquery.autocomplete.css"/>

    <script type="text/javascript">
        $().ready(
                function() {
                    $("#headCustomer").autocomplete(
                            "CustomerSetUp.do?method=findCustomers", {
                        width :260,
                        selectFirst :false
                    });
                    $("#oldRecord").autocomplete(
                            "CustomerSetUp.do?method=findCustomers", {
                        width :260,
                        selectFirst :false
                    });


                    $("#CountryInput").hide();
                    $("#CountryButton").hide();

                    $("#RegionInput").hide();
                    $("#RegionButton").hide();
                    $("#AreaInput").hide();
                    $("#AreaButton").hide();

                    $("#CityInput").hide();
                    $("#CityButton").hide();

                    $("#OrgFormInput").hide();
                    $("#OrgFormButton").hide();
                    $('#OrgFormUkrInput').hide();
                    $('#OrgFormUkrButton').hide();

                    $("#CountryClick").click(function() {
                        $('#CountryInput').toggle(400);
                        $('#CountryButton').toggle(400);

                        return false;
                    });
                    $("#RegionClick").click(function() {
                        $('#RegionInput').toggle(400);
                        $('#RegionButton').toggle(400);

                        return false;
                    });
                    $("#AreaClick").click(function() {
                        $('#AreaInput').toggle(400);
                        $('#AreaButton').toggle(400);

                        return false;
                    });
                    $("#CityClick").click(function() {
                        $('#CityInput').toggle(400);
                        $('#CityButton').toggle(400);

                        return false;
                    });
                    $("#OrgFormClick").click(function() {
                        $('#OrgFormInput').toggle(400);
                        $('#OrgFormButton').toggle(400);

                        return false;
                    });
//                    $("#OrgFormUkrClick").click(function() {
//                        $('#OrgFormUkrInput').toggle(400);
//                        $('#OrgFormUkrButton').toggle(400);
//
//                        return false;
//                    });


                });

        $(function() {
            var country = $('#country');
            var area = $('#area');
            var city = $('#city');

            area.selectChain({
                target :city,
                url :'CustomerSetUp.do?method=address',
                type :'post',
                data :"ajax=true"
            });



            // note that we're assigning in reverse order
            // to allow the chaining change trigger to work
            country.selectChain({
                target :area,
                url :'CustomerSetUp.do?method=address',
                type :'post',
                data :"ajax=true"


            });
              bodyResize(160);

        });


             function bodyResize(height)
        {
            var winHeight = $("body").height();
            var footerHeight =  $("#total").height();
            var IICheight = (winHeight < 600) ? 210 : winHeight - 389 - $("#toptable").height();
            var sc = (winHeight < 600) ? "visible" : "hidden";
            IICheight += height;
            $("body").css("overflow-y", sc);

//                var text = "winHeihgt = " + winHeight;
//                    text += "   IICheight = " + IICheight;
//                    text += "   total = " + footerHeight;
//        text += "   contentTDHeight = " + $("#contentTD").height();
//        text += "  bodyHeight = " + $("body").height();
//        text += "  htmlHeight = " + $("html").height();


     //   $("#console").text(text);



            $("#variableHeightElement").css("height", IICheight + "px");

        }
    </script>


    <style type="text/css">
        SELECT {
            width: 200px; /* Ширина списка в пикселах */
        }
       .width600
       {  width: 600px;}
       .width200
       {  width: 200px;}


        #variableHeightElement
        {
            overflow-y:auto;
        }
    </style>


</head>
<body onResize="bodyResize(160);">

<c:if test="${update}">

    <h3 align="center">
        Заведено --
        <c:out value="${customerForm.dateOfRecord}"/>
        Последние изменения --
        <c:out value="${customerForm.dateLastCorrection}"/>
    </h3>

</c:if>


<html:form action="/CustomerSetUp.do">

<div id="variableHeightElement">
<table>

<tr>
    <td class="tdLabel">
        Короткое название
    </td>
    <td>
        <html:text property="shortName" size="40" disabled="${update}"/>
        <font color="#ff0000"><html:errors property="shortName"/>
        </font>
    </td>
</tr>


<tr>
    <td class="tdLabel">
        Организационная форма
    </td>
    <td>
        <html:select property="orgForm" size="1" disabled="${update}">
            <html:options collection="orgForms" property="id"
                          labelProperty="name"/>


        </html:select>

          <c:if test="${! update}">
        <a href="#" id="OrgFormClick"> <img src="images/add.gif" width="16" height="16" border="0"/></a>
        <html:text property="newOrgForm" size="20" styleId="OrgFormInput"/>
        <html:submit value="Добавить" onclick="document.customerForm.method.value='addOrgForm'"
                     styleId="OrgFormButton"/>
        </c:if>

        <font color="#ff0000"><html:errors property="orgForm"/> </font>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Полное название
    </td>
    <td>
        <html:text property="name" size="40" disabled="${update}"/>
        <font color="#ff0000"><html:errors property="name"/> </font>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Полное название укр.
    </td>
    <td>
        <html:text property="nameUkr" size="40" />
        <font color="#ff0000"><html:errors property="nameUkr"/> </font>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Доля госсобственности
    </td>
    <td>


        <html:text property="stateProperty" size="20" disabled="${actual}"/>


    </td>
</tr>
<tr>
    <td class="tdLabel">
        Код ОКПО
    </td>
    <td>
        <html:text property="codeOKPO" size="20" disabled="${actual}"/>

    </td>
</tr>
<tr>
    <td class="tdLabel">
        Отрасль
    </td>
    <td>
        <html:select property="branch" size="1" disabled="${actual}">
            <html:options name="branches"/>
        </html:select>

    </td>
</tr>
<tr>
    <td>

    </td>
    <td>


        <fieldset class="width200">
            <legend>
                Где использует
            </legend>
            <html:checkbox property="applicationProcess" disabled="${actual}"/>
            - процессы
            <br>
            <html:checkbox property="applicationProduction"
                           disabled="${actual}"/>
            - продукция
            <br>
            <html:checkbox property="applicationProject" disabled="${actual}"/>
            - проекты
            <br>
            <html:checkbox property="applicationEngineering"
                           disabled="${actual}"/>
            - инжиниринг
            <br>
        </fieldset>


    </td>
</tr>
<tr>
    <td class="tdLabel">

    </td>
    <td>
        <fieldset class="width200">
            <legend>
                Цель покупок
            </legend>
            <html:checkbox property="purposeForItself" disabled="${actual}"/>
            - для себя
            <br>
            <html:checkbox property="purposeIntermediary"
                           disabled="${actual}"/>
            - посредник
            <br>
            <html:checkbox property="purposeSuply" disabled="${actual}"/>
            - снабжение
            <br>
            <html:checkbox property="purposeInstalation" disabled="${actual}"/>
            - монтаж
            <br>
        </fieldset>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Номер списка
    </td>
    <td>
        <html:select property="nomList" size="1" disabled="${actual}">
            <html:option value=""></html:option>
            <html:option value="1">1</html:option>
            <html:option value="2">2</html:option>
            <html:option value="3">3</html:option>
            <html:option value="4">4</html:option>

        </html:select>
        <font color="#ff0000"><html:errors property="nomList"/> </font>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Перспектива
    </td>
    <td>
        <html:select property="prospect" size="1" disabled="${actual}">
            <html:options name="prospects"/>
        </html:select>
        <font color="#ff0000"><html:errors property="powersLivel"/>
        </font>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Регион
    </td>
    <td>

        <html:select property="region" styleId="region"
                     disabled="${actual}">
            <html:option value=""></html:option>

            <html:options collection="regions" property="id"
                          labelProperty="name"/>
        </html:select>
        <c:if test="${! actual}">
            <a href="#" id="RegionClick"> <img src="images/add.gif" width="16" height="16" border="0"/></a>
            <html:text property="newRegion" size="20" styleId="RegionInput"/>
            <html:submit value="Добавить" onclick="document.customerForm.method.value='addRegion'"
                         styleId="RegionButton"/>
        </c:if>

        <font color="#ff0000"><html:errors property="region"/> </font>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Страна
    </td>
    <td>


        <html:select property="country" styleId="country"
                     disabled="${update}">
            <html:option value=""></html:option>

            <html:options collection="contries" property="id"
                          labelProperty="name"/>
        </html:select>
        <c:if test="${! update}">
        <a href="#" id="CountryClick"> <img src="images/add.gif" width="16" height="16" border="0"/></a>
        <html:text property="newCountry" size="20" styleId="CountryInput"/>
        <html:submit value="Добавить" onclick="document.customerForm.method.value='addCountry'"
                     styleId="CountryButton"/>
         </c:if>

        <font color="#ff0000"><html:errors property="country"/> </font>
    </td>
</tr>



<tr>
    <td class="tdLabel">
        область
    </td>
    <td>
        <html:select property="area" styleId="area" disabled="${update}">
            <html:option value=""></html:option>
            <html:options collection="areas" property="id"
                          labelProperty="name"/>

        </html:select>
        <c:if test="${! update}">
        <a href="#" id="AreaClick"> <img src="images/add.gif" width="16" height="16" border="0"/></a>
        <html:text property="newArea" size="20" styleId="AreaInput"/>
        <html:submit value="Добавить" onclick="document.customerForm.method.value='addArea'"
                     styleId="AreaButton"/>
        </c:if>
        <font color="#ff0000"><html:errors property="area"/> </font>
    </td>
</tr>


<tr>
    <td class="tdLabel">
         <html:select property="localityType" size="1" disabled="${update}">

            <html:option value="0">Город</html:option>
            <html:option value="1">ПГТ</html:option>
            <html:option value="2">Пос.</html:option>
            <html:option value="3">Село</html:option>

        </html:select>
    </td>
    <td>
        <html:select property="city" styleId="city" disabled="${update}">
            <html:options collection="cities" property="id"
                          labelProperty="name"/>
        </html:select>
        <c:if test="${! update}">
        <a href="#" id="CityClick"> <img src="images/add.gif" width="16" height="16" border="0"/></a>
        <html:text property="newCity" size="20" styleId="CityInput"/>
        <html:submit value="Добавить" onclick="document.customerForm.method.value='addCity'"
                     styleId="CityButton"/>
        </c:if>
        <font color="#ff0000"><html:errors property="city"/> </font>
    </td>
</tr>


<tr>
    <td class="tdLabel">
        Адрес дирекции
    </td>
    <td>
        <html:text property="address1"  disabled="${actual}" styleClass="width600"/>
        <font color="#ff0000"><html:errors property="address"/> </font>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Адрес комер. службы
    </td>
    <td>
        <html:text property="address2" size="40" disabled="${actual}" styleClass="width600"/>
        <font color="#ff0000"><html:errors property="address"/> </font>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Адрес тех службы
    </td>
    <td>
        <html:text property="address3" size="40" disabled="${actual}" styleClass="width600"/>
        <font color="#ff0000"><html:errors property="address"/> </font>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Головное предприятие
    </td>
    <td>


        <html:text property="headCustomer" size="20" disabled="${actual}"
                   styleId="headCustomer" styleClass="width600"/>

        <font color="#ff0000"><html:errors property="headCustomer"/>
        </font>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Дата поглощения
    </td>
    <td>
        <html:text property="mergeData" size="20" readonly="true" disabled="${actual}"/>
        <c:if test="${actual}">
            <html:img src="images/datepicker.jpg"
                      onclick="displayDatePicker('mergeData', false, 'dmy', '.');"/>
        </c:if>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Актуальность записи
    </td>
    <td>
        <html:checkbox property="status" disabled="${actual}" styleClass="width600"/>
    </td>
</tr>

<tr>
    <td class="tdLabel">
        Старая запись
    </td>
    <td>

        <html:text property="oldRecord" size="20" disabled="${actual}"
                   styleId="oldRecord" styleClass="width600"/>


        <font color="#ff0000"><html:errors property="oldRecord"/>
        </font>


    </td>
</tr>


<tr>
    <td class="tdLabel">
        Спец. ОСО
    </td>
    <td>
        <html:select property="person" size="1" disabled="${actual}">

            <html:options collection="users" property="id"
                          labelProperty="login"/>
        </html:select>
    </td>
</tr>


<tr>
    <td class="tdLabel">
        сайт
    </td>
    <td>
        <html:text property="site" size="40" disabled="${actual}" styleClass="width600"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        реквизиты
    </td>
    <td>
        <html:text property="requisite" size="40" disabled="${actual}" styleClass="width600"/>
    </td>
</tr>
<tr>
    <td class="tdLabel">
        Новое предприятие
    </td>
    <td>
         <html:checkbox property="new" />

    </td>
</tr>

<tr>
    <td class="tdLabel">
        раск.инф
    </td>
    <td>
        <html:text property="moreInformation" size="40"
                   disabled="${actual}" styleClass="width600"/>
    </td>
</tr>



</table>
   </div>

<div id="downButtons">
    <html:hidden property="id"/>
        <input type="hidden" name="method" value="insertOrUpdate"/>

        <c:if test="${! actual}">
            <html:submit>
                <%--Применить--%>
                <bean:message key="button.submit"/>
            </html:submit>
        </c:if>

        <html:cancel>
            <%--Отменить--%>
            <bean:message key="button.cancel"/>
        </html:cancel>

        <c:if test="${update}">
            <input type="submit" value="Изменить"
                   onclick="document.customerForm.method.value='changeCustomer'"/>
        </c:if>


        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <c:if test="${update}">
            <input type="submit" value="Контактные лица"
                   onclick="document.customerForm.method.value='findContacts'"/>
        </c:if> 



</div>
</html:form>

</body>
</html>
