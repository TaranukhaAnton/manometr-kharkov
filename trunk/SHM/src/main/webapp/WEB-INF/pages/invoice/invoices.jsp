<%@ page import="ua.com.manometer.model.invoice.Invoice" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="ua.com.manometer.model.invoice.Booking" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript" src="../js/local/invoices.js"></script>
<link rel="stylesheet" type="text/css" href="../css/invoice.css"/>

<div ID="content">

    <a href="javascript:void($('#newInvoice-div').dialog('open'))">Добавить</a><br>



        <display:table name="listInvoices" requestURI="./" excludedParams="method"
                   requestURIcontext="false" pagesize="20" sort="list"
                   class="simple" id="invoice">


        <display:column>
            <c:if test="${not invoice.invoice}">
               <c:out value="к"/>
            </c:if>
        </display:column>

        <display:column url="/invoices/view"  title="№" class="right" paramId="invoice_id" paramProperty="id" property="number" sortable="true"/>

        <display:column property="numberModifier" title="м" class="left"/>

        <display:column title="дата" sortable="true" format="{0,date,dd.MM.yyyy}" property="date" />

        <display:column title="Назн" class="center">
            <%= Invoice.purposeAlias[((Invoice) pageContext.getAttribute("invoice")).getPurpose()]%>
        </display:column>
        <display:column title="заказчик" maxLength="15" property="employer"/>

        <display:column property="executor" class="center" title="спец <br> ОСО"/>

        <display:column property="t0" class="col30 center" title="ДД"/>
        <display:column property="t1" class="col30 center" title="БП"/>
        <display:column property="t2" class="col30 center" title="ДКС"/>
        <display:column property="t3" class="col30 center" title="ИБ"/>
        <display:column property="t4" class="col30 center" title="пр"/>
        <display:column property="t5" class="col30 center" title="стор"/>

        <display:column title="Сумма,<br> тыс" class="right">
            <%=((Invoice) pageContext.getAttribute("invoice")).getTotal().divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP) %>
        </display:column>
        <display:column title="Нац-ка" class="right">
            <%=((Invoice) pageContext.getAttribute("invoice")).getAdditionToPrice() %>
        </display:column>
        <display:column property="supplier.currency.name" class="center" title="Вал"/>
        <display:column title="сост.">
            <%=Invoice.curStateAlias[((Invoice) pageContext.getAttribute("invoice")).getCurrentState()]%>
        </display:column>

        <display:column title="з/н">
            <%
                Booking booking = ((Invoice) pageContext.getAttribute("invoice")).getBooking();
                if (booking != null) {
            %>
            <a href="../bookings/view?invoice_id=<%=((Invoice) pageContext.getAttribute("invoice")).getId()%>"><%=booking.getNumber()%> <%= (StringUtils.isBlank(booking.getNumberModifier()) ? "" : ("/" + booking.getNumberModifier()))%>
            </a>
            <%

                }
            %>
        </display:column>

        <display:column title="Оплата,<br> %" class="right">
            <%=((Invoice) pageContext.getAttribute("invoice")).getPaymentPercent()%>
        </display:column>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
            <display:column title="" url="/invoices/delete"         paramId="invoice_id" paramProperty="id" >
                <img src="../images/delete.gif" width="18" height="18" hspace="4"   border="0"/>
            </display:column>
    </sec:authorize>


    </display:table>
</div>


<div id="newInvoice-div" title="Добавить счет/кп">
    <Br>

    <form action="#" id="newInvoice_form">
        <table>
            <tr>
                <td><input type="radio" name="isInvoice" id="isInv" value="true" checked onclick="removeErrorClass()">
                </td>

                <td><label for="isInv">Счет</label></td>
            </tr>
            <tr>
                <td><input type="radio" name="isInvoice" id="isKp" value="false" onclick="removeErrorClass()"></td>
                <td><label for="isKp">КП</label></td>
            </tr>
            <tr>
                <td><label for="number">Номер</label></td>
                <td><input type="text" name="number" id="number" class="text ui-widget-content ui-corner-all"
                           onclick="removeErrorClass()"/></td>

            </tr>
            <tr>
                <td><label for="numberModifier">Модификатор</label></td>
                <td><input type="text" name="numberModifier" id="numberModifier"
                           class="text ui-widget-content ui-corner-all" onclick="removeErrorClass()"/></td>
            </tr>
            <tr>
                <td><label for="date">Дата &nbsp &nbsp &nbsp &nbsp &nbsp</label></td>
                <td><input type="text" name="date" id="date" readonly="true" onclick="removeErrorClass()"
                           value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>"
                           class="text ui-widget-content ui-corner-all"/></td>
            </tr>

            <tr>
                <td><label for="employer">Заказчик</label></td>
                <td><input type="text" id="employer" name="employer" onclick="removeErrorClass()"
                           class="text ui-widget-content ui-corner-all"/>
                </td>
            </tr>

            <tr>
                <td><label for="consumer">Конечный</label></td>
                <td>
                    <input type="text" id="consumer" name="consumer" onclick="removeErrorClass()"
                           class="text ui-widget-content ui-corner-all"/>
                </td>
            </tr>


        </table>
    </form>

</div>
<%--
<div id="filter-form" title="Фильтр">

    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">Счет/КП</a></li>
            <li><a href="#tabs-2">Номер</a></li>
            <li><a href="#tabs-3">Дата</a></li>
            <li><a href="#tabs-4">Назначение</a></li>
            <li><a href="#tabs-5">Состояние</a></li>
            <li><a href="#tabs-6">Валюта</a></li>
            <% if (((Integer) session.getAttribute("livel")) > 2) {%>
            <li><a href="#tabs-7">Спец. ОСО</a></li>
            <% } %>
        </ul>
        <form id="filterForm">
            <div id="tabs-1" class="tabdiv">

                <input type="radio" name="f0" value="0"/> все <Br>
                <input type="radio" name="f0" value="1"/> только счета <Br>
                <input type="radio" name="f0" value="2"/> только КП <Br>

            </div>
            <div id="tabs-2" class="tabdiv">
                <input type="radio" name="f1" value="0"/> все <Br>
                <input type="radio" name="f1" value="1"/> с <input type="text" name="f1_from">
                по<input type="text" name="f1_to"><Br>
            </div>
            <div id="tabs-3" class="tabdiv">

                <table>
                    <tr>
                        <td><input type="radio" name="f2" value="0"/></td>
                        <td>все</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="f2" value="1"/></td>
                        <td>текущий год</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="f2" value="2"/></td>
                        <td>последние 3 мес</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="f2" value="3"/></td>
                        <td>
                            <table>
                                <tr>
                                    <td>с</td>
                                    <td><input type="text" name="f2_from" id="f2_from"></td>
                                </tr>
                                <tr>
                                    <td>по</td>
                                    <td><input type="text" name="f2_to" id="f2_to"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>


            </div>
            <div id="tabs-4" class="tabdiv">

                <table>

                    <% for (Integer i = 0; i < Invoice.purposeAlias.length; i++) { %>
                    <tr>
                        <td><input type="checkbox" name="purpose" value="<%=i%>"></td>
                        <td><%=Invoice.purposeAlias[i]%>
                        </td>
                    </tr>
                    <%}%>


                </table>
            </div>
            <div id="tabs-5" class="tabdiv">
                <table>
                    <%
                        String[] aliases = Invoice.curStateAlias;
                        for (int i = 0; i < aliases.length; i++) {
                            String alias = aliases[i];
                    %>
                    <tr>
                        <td><input type="checkbox" name="state" value="<%=i%>"></td>
                        <td>&nbsp;<%=alias%>
                        </td>
                    </tr>
                    <%}%>


                </table>
            </div>
            <div id="tabs-6" class="tabdiv">
                <table>
                    <%
                        List<Currency> currencies = Factory.getCurrencyDAO().findAll();
                        for (Currency currency : currencies) {
                    %>
                    <tr>
                        <td><input type="checkbox" name="currency" value="<%=currency.getId()%>"></td>
                        <td>&nbsp;<%=currency.getName()%>
                        </td>
                    </tr>
                    <%}%>


                </table>
            </div>
            <% if (((Integer) session.getAttribute("livel")) > 2) {%>
            <div id="tabs-7" class="tabdiv">

                &lt;%&ndash;<div id="users">&ndash;%&gt;


                <table>
                    <%
                        List<User> users = Factory.getUserDAO().findAll();
                        for (User user : users) {
                    %>
                    <tr>
                        <td><input type="checkbox" name="user" value="<%=user.getId()%>"></td>
                        <td>&nbsp;<%=user.getLogin()%>
                        </td>
                    </tr>
                    <%}%>


                </table>
            </div>
            <% } %>
        </form>
    </div>

</div>--%>
