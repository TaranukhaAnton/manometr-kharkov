
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="ua.com.manometer.model.invoice.Invoice" %>
<%@ page import="ua.com.manometer.model.Supplier" %>
<%@ page import="ua.com.manometer.model.invoice.InvoiceItem" %>
<%@ page import="ua.com.manometer.model.invoice.Booking" %>
<%@ page import="ua.com.manometer.util.InvoiceUtils" %>
<%@ page import="static ua.com.manometer.util.InvoiceUtils.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<script type="text/javascript" src="../js/local/editInvoice.js"></script>
<link rel="stylesheet" type="text/css" href="../css/invoice.css"/>


<body onResize="bodyResize();">
<%

    Invoice invoice = (Invoice) request.getAttribute("invoice");
    boolean changesAllowed = invoice.getCurrentState().equals(Invoice.STATE_CHERN) || invoice.getCurrentState().equals(Invoice.STATE_MOD);
    String textFieldDisab = "disabled=\"true\"";
    Calendar cal = Calendar.getInstance();
    cal.setTime(invoice.getDate());
%>
<input type="hidden" id="year" value="<%=cal.get(Calendar.YEAR)%>">

<div id="content">
<div id="topDiv">
<input type="hidden" name="id" id="id" value="<%= invoice.getId() %>">

<div id="toptable">
<table class="toptable">
<tr>
    <td>
        <%=(invoice.isInvoice()) ? "  Счет" : "  КП"%>
        <%=invoice.getNumber()%>
        <%= ((invoice.getNumberModifier() == null) || (invoice.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%>
    </td>
    <td colspan="3"> от <%= (new SimpleDateFormat("dd.MM.yy")).format(invoice.getDate()) %>
        <%if (invoice.getChangeDate()!=null){%>
        &nbsp;изм.<%= (new SimpleDateFormat("dd.MM.yy")).format(invoice.getChangeDate()) %>
        <%}%>
    </td>
    <td> Срок действия
    </td>
    <td><input type="text" value="<%= (invoice.getValidity()==null)?"":invoice.getValidity() %>"
               id="validity" name="validity" onkeypressEn="true" onkeypress='return digInput(event)'
               onkeydown="paramChange('validity','dig');" class="width40"/>
    </td>
    <td> Поставщик</td>

    <td><select name="supplier" id="supplier" onkeypressEn="true"   <%= changesAllowed ? "" : "disabled=\"true\"" %>
                onchange="paramChange('supplier','any',supplierChange);">
        <% List<Supplier> supplierList = (List<Supplier>) request.getAttribute("supplierList");
            for (Supplier supplier : supplierList) { %>
        <option value="<%=supplier.getId()%>"  <%=(supplier.getId().equals(invoice.getSupplier().getId())) ? "selected" : ""%>    ><%=supplier.getAlias()%>
        </option>

        <%}%>
    </select>
    </td>
    <td> Менеджер
    </td>
    <td><%=invoice.getExecutor().getLogin()%>
    </td>

</tr>
<tr>
    <td>
        Заказчик
    </td>
    <td colspan="3"><input type="text"  <%= changesAllowed ? "" : textFieldDisab %>
                           value="<%= (invoice.getEmployer() ==null)?"":invoice.getEmployer().getShortName()%>"
                           id="employer" name="employer" onkeypressEn="true"
                           onblur="paramChange('employer','any',customerSetup);"/>
    </td>
    <td>
        Вниманию
    </td>
    <td>
        <input type="text" value="<%= (invoice.getNotice() ==null)?"":invoice.getNotice()%>" id="notice"
               name="notice" onkeypressEn="true"         <%= changesAllowed ? "" : textFieldDisab %>
               onkeydown="paramChange('notice','any');"/>

    </td>
    <td>
        контроль &nbsp;
    </td>
    <td>

        <input type="text"
               value="<%= (invoice.getControlDate() ==null)?"":(new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getControlDate())  %>"
               id="controlDate" name="controlDate" onkeypressEn="true"
               style="float:left;" readonly="true"/>

        <%--<img src="images/datepicker.jpg" onclick="displayDatePicker('controlDate', false, 'dmy', '.');">--%>


    </td>
    <td> Состояние</td>
    <td><%=Invoice.curStateAlias[invoice.getCurrentState()]%>
    </td>


</tr>
<tr>
    <td>Конечный</td>
    <td colspan="3"><input
            type="text"   <%= invoice.getCurrentState().equals(Invoice.STATE_CHERN) ? "" : textFieldDisab %>
            value="<%= (invoice.getConsumer() ==null)?"":invoice.getConsumer().getShortName()  %>"
            id="consumer" name="consumer" onkeypressEn="true"
            onblur="paramChange('consumer','any',customerSetup);"/></td>
    <td>Срок пост</td>
    <td>
        <input type="text" value="<%= (invoice.getDeliveryTime() ==null)?"":invoice.getDeliveryTime()%>"
               id="deliveryTime"
               name="deliveryTime" onkeypressEn="true"         <%= changesAllowed ? "" : textFieldDisab %>
               onkeydown="paramChange('deliveryTime','any');"/>


    </td>
    <td>ож.опл.</td>

    <td colspan="2"><input type="text"
                           value="<%= (invoice.getAwaitingPayment()==null)?"":(new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getAwaitingPayment())  %>"
                           id="awaitingPayment" name="awaitingPayment" onkeypressEn="true" readonly="true"
    <%--onkeydown="paramChange('awaitingPayment','any');" --%>
                           style="float:left;"/>

        <%--<img src="images/datepicker.jpg" onclick="displayDatePicker('awaitingPayment', false, 'dmy', '.');">--%>
    </td>
    <td>

    </td>
</tr>
<tr>
    <td>Назначение</td>
    <td>

        <select name="purpose" id="purpose" onkeypressEn="true"
                <%= changesAllowed ? "" : "disabled=\"true\"" %>
                onchange="changePurpose(this)">
            <% for (Integer i = 0; i < Invoice.purposeAlias.length; i++) { %>
            <option value="<%=i%>" <%=(i.equals(invoice.getPurpose())) ? "selected" : "" %> ><%=Invoice.purposeAliasFull[i]%>
            </option>

            <%}%>
        </select>

    </td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td>ож.пост.</td>
    <td colspan="2"><input type="text"
                           value="<%= (invoice.getAwaitingDelivery()==null)?"":(new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getAwaitingDelivery())  %>"
                           id="awaitingDelivery" name="awaitingDelivery" onkeypressEn="true" readonly="true"
    <%--onkeydown="paramChange('awaitingDelivery','any');" --%>
                           style="float:left;"/>
        <%--<img src="images/datepicker.jpg" onclick="displayDatePicker('awaitingDelivery', false, 'dmy', '.');">--%>
    </td>
    <td>
    </td>


</tr>


<tr>
    <td>предоплата</td>
    <td><input type="text" value="<%= (invoice.getPrepayment()==null)?"":invoice.getPrepayment() %>"
               id="prepayment" name="prepayment" onkeypressEn="true"
            <%= changesAllowed ? "" : textFieldDisab %> onkeypress='return digInput(event)'
               onkeydown="paramChange('prepayment','float');" class="width40"/>
    </td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>

    <td>вер. опл.</td>
    <td><input type="text"
               value="<%= (invoice.getProbabilityOfPayment()==null)?"":invoice.getProbabilityOfPayment() %>"
               id="probabilityOfPayment"
               name="probabilityOfPayment"
               onkeypress='return digInput(event)'
               onkeypressEn="true"
               onkeydown="paramChange('probabilityOfPayment','dig');"
               class="width40"/></td>
    <td></td>
    <td></td>


</tr>
<tr>
    <td>по извещ</td>
    <td><input type="text"
            <%= changesAllowed ? "" : textFieldDisab %>
               value="<%= (invoice.getPaymentOnTheNotice()==null)?"":invoice.getPaymentOnTheNotice() %>"
               id="paymentOnTheNotice"
               name="paymentOnTheNotice" onkeypress='return digInput(event)'
               onkeypressEn="true" onkeydown="paramChange('paymentOnTheNotice','float');" class="width40"/></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td>валюта</td>
    <td>
        <div id="currency">
            <%=invoice.getSupplier().getCurrency()%>
        </div>


    </td>
    <td>

        <div id="currencyText" <%=(invoice.getSupplier().getCurrency().getId() == 1) ? "style=\"visibility:hidden;\"" : "" %>   >

            грн. за 1 <%=invoice.getSupplier().getCurrency()%>
        </div>
    </td>
    <td><input type="text" value="<%= invoice.getExchangeRate() %>" id="exchangeRate" name="exchangeRate"
            <%= changesAllowed ? "" : textFieldDisab %>
               onkeypressEn="true" onkeydown="paramChange('exchangeRate','float',exchangeRateChange);"
               class="width40" <%=(invoice.getSupplier().getCurrency().getId() == 1) ? "style=\"visibility:hidden;\"" : "" %> />
    </td>

</tr>
<tr>
    <td>по факту</td>
    <td><input type="text"
            <%= changesAllowed ? "" : textFieldDisab %>
               value="<%= (invoice.getPostPay()==null)?"":invoice.getPostPay() %>" id="postPay"
               name="postPay"
               onkeypress='return digInput(event)'
               onkeypressEn="true"
               onkeydown="paramChange('postPay','float');" class="width40"/></td>
    <td>дней</td>
    <td>
        <input type="text"  <%= changesAllowed ? "" : textFieldDisab %>
               value="<%= (invoice.getDaysAfterDelivery()==null)?"":invoice.getDaysAfterDelivery() %>"
               id="daysAfterDelivery"
               onkeypress='return digInput(event)'
               name="daysAfterDelivery" onkeypressEn="true"
               onkeydown="paramChange('daysAfterDelivery','dig');" class="width40"/>

    </td>
    <td></td>
    <td></td>
    <td>НДС, %</td>
    <td><input type="text" value="<%= invoice.getNDS() %>" id="NDS"
               name="NDS"   <%= changesAllowed ? "" : textFieldDisab %>
               onkeypress='return digInput(event)'
               onkeypressEn="true" onkeydown="paramChange('NDS','float',exchangeRateChange);" class="width40"/></td>
    <td></td>
    <td></td>

</tr>
</table>
</div>

<%--<%if (changesAllowed) {%>--%>
<%--<a href="javascript:void($('#dialog-form').dialog('open'))">Добавить</a><br>--%>
<%--<%} %>--%>

<div id="invItems">
    <TABLE class="invItemsTab " id="headerTable">
        <TR>
            <TH class="width20">№</TH>
            <TH class="width415">Наименование продукции</TH>
            <TH class="width70">Кол-во</TH>
            <TH class="width70  <%if (changesAllowed){%>selectOnHover<%}%>"
                <%if (changesAllowed){%>onclick="$('#commonPercent-dialog').dialog('open')"<%}%> >%
            </TH>
            <TH class="width70  <%if (changesAllowed){%>selectOnHover<%}%>"
                <%if (changesAllowed){%>onclick="$('#commonTransportCost-dialog').dialog('open')"  <%}%>
                    >Транс
            </TH>
            <TH class="width70 ">Доп</TH>
            <TH class="width70  <%if (changesAllowed){%>selectOnHover<%}%> "
                <%if (changesAllowed){%>onclick="$('#roundPrice-dialog').dialog('open')"<%}%>
                    >Цена
            </TH>
            <TH class="width90">Сумма</TH>
            <TH class="width70  <%if (changesAllowed){%>selectOnHover<%}%>"
                <%if (changesAllowed){%>onclick="$('#commonDelivery-dialog').dialog('open')"  <%}%> >
                дней
            </TH>
            <TH class="width20"></TH>
            <TH class="width20"></TH>
            <TH class="width20"></TH>

        </TR>
    </TABLE>


    <div id="invItemsContent">


        <TABLE class="invItemsTab" id="contentTable">


            <%
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(2);
                numberFormat.setMaximumFractionDigits(2);


                String[] url = {"co", "ao", "op"};
                for (int i = 0; i < invoice.getInvoiceItems().size(); i++) {
                    InvoiceItem item = invoice.getInvoiceItems().get(i);

            %>
            <TR>
                <TD class="width20 topAlign">
                    <%=i + 1%>
                </TD>
                <TD class="topAlign width415" align="left">

                    <%if ((item.getType() < 3) && changesAllowed) {%>

                    <a href="../invoice_item/add?invoice_id=<%=invoice.getId()%>&invoice_item_id=<%=item.getId()%>&type=<%=url[item.getType()]%> ">
                        <%= item.getName() %>
                    </a> <br>
                    <% } else { %>
                    <%= item.getName() %>
                    <% } %>

                </TD>
                <TD class="width70 topAlign">
                    <%if(changesAllowed){ %>

                    <input type="text" class="inp" value="<%= item.getQuantity() %>"
                           onkeypressEn="true"    id="quantity<%= item.getId() %>"
                           onkeypress='return digInput(event)'
                           onkeydown="invoiceItemChange('quantity',<%= item.getId() %>,'dig',<%= invoice.getId()%>);">

                    <%}else{%>
                    <%= item.getQuantity() %>
                    <%}%>

                </TD>
                <TD class="width70 topAlign">
                    <%
                        numberFormat.setMinimumFractionDigits(4);
                        numberFormat.setMaximumFractionDigits(4);
                    %>
                    <input type="text" class="inp" value="<%= numberFormat.format(item.calculatePercent())%>"
                           onkeypressEn="true"   <%= changesAllowed?"":"readonly=\"true\"" %>
                           id="percent<%=item.getId() %>" onkeypress='return digInput(event)'
                           onkeydown="invoiceItemChange('percent',<%= item.getId() %>,'float',<%= invoice.getId()%>);">
                    <%
                        numberFormat.setMinimumFractionDigits(2);
                        numberFormat.setMaximumFractionDigits(2);
                    %>

                </TD>
                <TD class="width70 topAlign">
                    <input type="text" class="inp"
                           value="<%= numberFormat.format(item.getTransportationCost()) %>"
                           onkeypressEn="true" <%= changesAllowed?"":"readonly=\"true\"" %>
                           id="transportationCost<%=item.getId()%>" onkeypress='return digInput(event)'
                           onkeydown="invoiceItemChange('transportationCost',<%=item.getId() %>,'currency',<%= invoice.getId()%>);">


                </TD>
                <TD class="width70 topAlign">
                    <input type="text" class="inp"
                           value="<%= numberFormat.format(item.getAdditionalCost()) %>"
                           onkeypressEn="true" <%= changesAllowed?"":"readonly=\"true\"" %>
                           id="additionalCost<%=item.getId()%>" onkeypress='return digInput(event)'
                           onkeydown="invoiceItemChange('additionalCost',<%=item.getId() %>,'currency',<%= invoice.getId()%>);">


                </TD>


                <TD class="width70 topAlign">
                    <input type="text" class="inp"
                           value="<%= numberFormat.format(item.getSellingPrice()) %>"
                           onkeypressEn="true" <%= changesAllowed?"":"readonly=\"true\"" %>
                           id="sellingPrice<%=item.getId()%>" onkeypress='return digInput(event)'
                           onkeydown="invoiceItemChange('sellingPrice',<%=item.getId() %>,'currency',<%= invoice.getId()%>);">


                </TD>
                <TD class="width90 topAlign">
                    <input type="text" class="inp"
                           value="<%= numberFormat.format(item.getSum()) %>"

                           id="sum<%=item.getId() %>" readonly="true">


                </TD>
                <TD class="width70 topAlign">
                    <input type="text" class="inp" value=" <%=item.getDeliveryTime() %>"
                           onkeypressEn="true"   <%= changesAllowed?"":"readonly=\"true\"" %>
                           id="deliveryTime<%=item.getId() %>" onkeypress='return digInput(event)'
                           onkeydown="invoiceItemChange('deliveryTime',<%=item.getId() %>,'dig',<%= invoice.getId()%>);">


                </TD>


                <TD class="width20 topAlign">

                    <%if (changesAllowed) {%>
                    <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='../invoice_item/del_ii?invoice_item_id=<%= item.getId() %>&invoice_id=<%=invoice.getId()%>'">
                        <img src="../images/delete.gif" width="18" height="18" border="0"/>
                    </a>
                    <%} %>


                </TD>
                <TD class="width20 topAlign">
                    <%if (changesAllowed) {%>
                    <a href="javascript:   self.location.href='../invoice_item/moveUpItem?position=<%=i%>&invoice_id=<%=invoice.getId()%>'">
                        <img src="../images/prev_nav.gif" width="18" height="18" border="0"/>
                    </a>
                    <%} %>


                </TD>
                <TD class="width20 topAlign">

                    <%if (changesAllowed) {%>
                    <a href="javascript:  self.location.href='../invoice_item/moveDownItem?position=<%=i%>&invoice_id=<%=invoice.getId()%>'">
                        <img src="../images/next_nav.gif" width="18" height="18" border="0"/>
                    </a>
                    <%} %>

                </TD>


            </TR>
            <%
                }
            %>

        </TABLE>
    </div>
</div>

<%
    numberFormat.setMinimumFractionDigits(2);
    numberFormat.setMaximumFractionDigits(2);
%>
<div id="invItemsFooter">
    <TABLE>
        <TR>
            <Td class="width20"></Td>
            <Td class="width550" align="right">всего</Td>
            <Td class="width70"></Td>
            <Td class="width70"></Td>
            <%--<Td class="middle70"></Td>--%>
            <Td class="width90">
                <div id="sum" style="text-align:right;"><%=numberFormat.format(invoice.getSum())%>
                </div>
            </Td>
            <Td class="width70"></Td>
            <Td class="width20"></Td>

        </TR>
        <TR>
            <Td class="width20"></Td>
            <Td class="width550" align="right">НДС</Td>
            <Td class="width70"></Td>
            <Td class="width70"></Td>
            <%--<Td class="middle70"></Td>--%>
            <Td class="width90">
                <div id="NDSPayment" style="text-align:right;"><%= numberFormat.format(invoice.getNdsPayment())%>
                </div>
            </Td>
            <Td class="width70"></Td>
            <Td class="width20"></Td>

        </TR>
        <TR>
            <Td class="width20"></Td>
            <Td class="width550" align="right">итого</Td>
            <Td class="width70"></Td>
            <Td class="width70"></Td>
            <%--<Td class="middle70"></Td>--%>
            <Td class="width90">
                <div id="total" style="text-align:right;"><%= numberFormat.format(invoice.getTotal())%>
                </div>
            </Td>
            <Td class="width70"></Td>
            <Td class="width20"></Td>

        </TR>

    </TABLE>
</div>


<table>
    <tr>
        <td>Примечания:</td>
        <td> Комментарий:
        </td>
    </tr>
    <tr>
        <td>
            <textarea name="notes" class="width520"
                    <%--<%= changesAllowed ? "" : textFieldDisab %>--%>
                    <%= changesAllowed ? "" : "readonly=\"true\"" %>
                      id="notes" cols="40" rows="3" onkeypressEn="true"
                      onkeydown="paramChange('notes','any');"><%=(invoice.getNotes() == null) ? "" : invoice.getNotes()%></textarea>
        <td>
            <textarea name="comments" id="comments" cols="40" rows="3" onkeypressEn="true" class="width520"
                      onkeydown="paramChange('comments','any');"><%= (invoice.getComments() == null) ? "" : invoice.getComments()%></textarea>
        </td>
    </tr>
</table>


</div>
    <div id="downButtons">
        <table style="width: 100%;">
            <tr>
                <td>
            <div >
                <%if (InvoiceUtils.isAddProdAllowed(invoice)) {%>
                <input type="button" value="+ Продукция" onclick="javascript:void($('#addProduction-dialog').dialog('open'))" class="butt">
                <%} %>

                <%if (InvoiceUtils.isZapIzmAllowed(invoice)) {%>
                <input type="button" value="Запрет изм." onclick="banChanges();" class="butt">
                <%} %>

                <% if (InvoiceUtils.isAktivirAllowed(invoice)) {%>
                <input type="button" value="Активировать" onclick="setActive()" class="butt">
                <% }%>

                <%if (InvoiceUtils.isAnnulirovatAllowed(invoice)) {%>
                <input type="button" value="Аннулировать" onclick="setAnn()" class="butt">
                <% }  %>


                <%if (InvoiceUtils.isOtlAllowed(invoice)) {%>
                <input type="button" value="Отложить" onclick="setOtl()" class="butt">
                <% }%>

                <%if (InvoiceUtils.isInvIzKpAllowed(invoice)) {%>
                <input type="button" value="Счет из кп" onclick="$('#invFromKP').dialog('open')" class="butt">
                <% }%>

                <%if (InvoiceUtils.isOpenZNAllowed(invoice)) {%>
                <input type="button" value="Открыть З.Н." onclick="$('#order-dialog').dialog('open')" class="butt">
                <% }%>

                <%if (InvoiceUtils.isZnAllowed(invoice)) {%>
                <input type="button" value="заказ-наряд"
                       onclick="location.href='../bookings/view?invoice_id=<%=invoice.getId()%>'"
                       class="butt">
                <% }%>

                <%if (InvoiceUtils.isCopyAllowed(invoice)) {%>
                <input type="button" value="Копия" onclick="javascript:void($('#copyInvoice-dialog').dialog('open'))" class="butt">
                <% }%>

                <%if (InvoiceUtils.isIzmRazbAllowed(invoice)) {%>
                <input type="button" value="Изм./разбить" onclick="" class="butt">
                <% }%>

                <%if (InvoiceUtils.isAnalizAllowed(invoice)) {%>
                <input type="button" value="Анализ" onclick="" class="butt">
                <% }%>

                <%if (InvoiceUtils.isPrintAllowed(invoice)) {%>
                <input type="button" value="Печать"
                       onclick="javascript:void($('#print-dialog').dialog('open'))"
                       class="butt">
                <% }%>

                <%if (InvoiceUtils.isOtgrAllowed(invoice)) {%>
                <input type="button" value="Отгрузки"
                       onclick="location.href='../invoices/view_shipments?invoice_id=<%=invoice.getId()%>'"
                       class="butt">
                <% }%>

                <%if (InvoiceUtils.isOplatAllowed(invoice)) {%>
                <input type="button" value="Оплаты"
                       onclick="location.href='./view_payments?invoice_id=<%=invoice.getId()%>'"
                       class="butt">
                <% }%>

                <%if (InvoiceUtils.isIspolnAllowed(invoice)) {%>
                <input type="button" value="Исполнен"
                       onclick="setIsp()"
                       class="butt">
                <%} %>
            </div>
                </td>
                <td style="width: 50px;">
                    <div>
                        <%
                            if (invoice.getPrev() != null) {
                        %>
                        <a href="./view?invoice_id=<%=((Invoice) request.getAttribute("invoice")).getPrev()%>"><img
                                src="../images/back.png"></a>
                        <%
                            }
                            if (invoice.getNext() != null) {
                        %>
                        <a href="./view?invoice_id=<%=((Invoice) request.getAttribute("invoice")).getNext()%>"><img
                                src="../images/forward.png"></a>
                        <%
                            } %>


                    </div>
                </td>
            </tr>
        </table>
    </div>

</div>
<%-- content--%>

<div id="addProduction-dialog" title="Добавить позицию">
    <a href="../invoice_item/add?invoice_id=<%=invoice.getId()%>&type=co">
        датчик ЦО</a> <br>
    <a href="../invoice_item/add?invoice_id=<%=invoice.getId()%>&type=ao">
        датчик АО</a> <br>
    <a href="../invoice_item/add?invoice_id=<%=invoice.getId()%>&type=op">
        датчик ОП</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=3">
        д.д. спец</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=4">
        блок питания</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=5">
        ИБ</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=6">
        диаф. кам.</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=7">
        проч продукция</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=8">
        вычислитель</a> <br>
    <a href="../invoice_item/add_list?invoice_id=<%=invoice.getId()%>&type=9">
        прод. сторон. произв.</a> <br>
</div>

<div id="order-dialog" title="Открыть заказ-наряд">

    <table>
        <tr>
            <td>
                <label for="number">Номер &nbsp &nbsp &nbsp &nbsp &nbsp</label>
            </td>
            <td>
                <input type="text" name="number" id="number" class="width90 text ui-widget-content ui-corner-all"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="numberModifier">Модификатор &nbsp &nbsp </label>
            </td>
            <td><input type="text" name="numberModifier" id="numberModifier"
                       class="width90 text ui-widget-content ui-corner-all"/>
            </td>
        </tr>
        <tr>
            <td><label for="date">Дата &nbsp &nbsp &nbsp &nbsp &nbsp</label></td>
            <td><input type="text" name="date" id="date" readonly="true"
                       value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>"
                       class="width90 text ui-widget-content ui-corner-all"/>
            </td>
        </tr>
    </table>


    <%--<label for="date">Дата &nbsp &nbsp &nbsp &nbsp &nbsp</label>--%>
    <%--<input type="text" name="date" id="date" value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>"--%>
    <%--readonly="true" class="text ui-widget-content ui-corner-all"/>--%>


</div>

<div id="print-dialog" title="Печать">


    <input type="button" value="PDF"
           onclick="javascript:void(printInvoice('pdf',<%=invoice.getId()%>))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only "> <br>
    <input type="button" value="XLS"
           onclick="javascript:void(printInvoice('xls',<%=invoice.getId()%>))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"> <br>
    <input type="button" value="ODT"
           onclick="javascript:void(printInvoice('odt',<%=invoice.getId()%>))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"> <br>


</div>

<div id="roundPrice-dialog" title="Округлить цену">


    <input type="button" value="0,1"
           onclick="javascript:void(setRoundPrice ('4'))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only "> <br>
    <input type="button" value="1"
           onclick="javascript:void(setRoundPrice ('3'))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"> <br>
    <input type="button" value="10"
           onclick="javascript:void(setRoundPrice ('2'))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"> <br>
    <input type="button" value="100"
           onclick="javascript:void(setRoundPrice ('1'))"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"> <br>

</div>

<div id="commonPercent-dialog" title="Установить прoцент">


    <input type="text" value=""
           onclick="" id="commonPercent" onkeypress='sendOnEnter(event, this)'
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only "> <br>


</div>

<div id="commonDelivery-dialog" title="Установить срок поставки">


    <input type="text" value=""
           onclick="" id="commonDelivery"
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only "> <br>


</div>

<div id="commonTransportCost-dialog" title="Транспортные расходы">


    <input type="text" value=""
           onclick="" id="commonTransportCost" onkeypress='sendOnEnter(event, this)'
           class="butt ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only "> <br>


</div>

<div id="invFromKP" title="Счет из КП.">
    <Br>

    <form action="#" id="invFromKP_form">
        <table>
            <input type="hidden" name="isInvoice" value="true"/>
            <input type="hidden" id="emploer_invFromKP" name="employer"/>
            <input type="hidden" id="consumer_invFromKP" name="consumer"/>
            <input type="hidden" name="parentID" value="<%=invoice.getId()%>"/>
            <tr>
                <td><label for="number">Номер</label></td>
                <td><input type="text" name="number" id="number_invFromKP" class="text ui-widget-content ui-corner-all"
                           onclick="removeErrorClass(this)"/></td>

            </tr>
            <tr>
                <td><label for="numberModifier">Модификатор</label></td>
                <td><input type="text" name="numberModifier" id="numberModifier_invFromKP"
                           class="text ui-widget-content ui-corner-all" onclick="removeErrorClass(this)"/></td>
            </tr>
            <tr>
                <td><label for="date">Дата &nbsp &nbsp &nbsp &nbsp &nbsp</label></td>
                <td><input type="text" name="date" id="date_invFromKP" readonly="true" onclick="removeErrorClass(this)"
                           value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>"
                           class="text ui-widget-content ui-corner-all"/></td>
            </tr>


        </table>
    </form>

</div>

<div id="bunChanges-dialog" title="Запрет изменений">

    <table>

        <tr>
            <td><label for="invDate">Дата &nbsp &nbsp &nbsp &nbsp &nbsp</label></td>
            <td><input type="text" name="date" id="invDate" readonly="true"
                       value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getDate()) %>"
                       class="dateInput ui-widget-content ui-corner-all"/>
            </td>
        </tr>
    </table>

</div>

<div id="copyInvoice-dialog" title="Создать копию счета/кп">
    <Br>

    <form action="#" id="copyInvoice_form">
         <input type="hidden" name="parent_id" value="<%=invoice.getId()%>"/>
        <table>
            <tr>
                <td><input type="radio" name="isInvoice" id="isInv" value="true" checked onclick="removeErrorClass(this)">
                </td>

                <td><label for="isInv">Счет</label></td>
            </tr>
            <tr>
                <td><input type="radio" name="isInvoice" id="isKp" value="false" onclick="removeErrorClass(this)"></td>
                <td><label for="isKp">КП</label></td>
            </tr>
            <tr>
                <td><label for="number">Номер</label></td>
                <td><input type="text" name="number"  class="text ui-widget-content ui-corner-all"
                           onclick="removeErrorClass(this)"/></td>

            </tr>
            <tr>
                <td><label for="numberModifier">Модификатор</label></td>
                <td><input type="text" name="numberModifier"
                           class="text ui-widget-content ui-corner-all" onclick="removeErrorClass(this)"/></td>
            </tr>
            <tr>
                <td><label for="date">Дата &nbsp &nbsp &nbsp &nbsp &nbsp</label></td>
                <td><input type="text" name="date"  readonly="true" onclick="removeErrorClass(this)"
                           value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>"
                           class="text ui-widget-content ui-corner-all"/></td>
            </tr>

            <tr>
                <td><label for="employer">Заказчик</label></td>
                <td><input type="text"  name="employer" onclick="removeErrorClass(this)"
                           class="text ui-widget-content ui-corner-all"/>
                </td>
            </tr>

            <tr>
                <td><label for="consumer">Конечный</label></td>
                <td>
                    <input type="text" name="consumer" onclick="removeErrorClass(this)"
                           class="text ui-widget-content ui-corner-all"/>
                </td>
            </tr>


        </table>
    </form>

</div>

</body>
