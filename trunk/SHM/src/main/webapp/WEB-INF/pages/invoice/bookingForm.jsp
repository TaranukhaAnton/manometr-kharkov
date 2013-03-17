<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="ua.com.manometer.model.invoice.Invoice" %>
<%@ page import="ua.com.manometer.model.invoice.Booking" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="ua.com.manometer.model.invoice.InvoiceItem" %>
<%@ page import="ua.com.manometer.model.User" %>
<%@ page import="ua.com.manometer.util.InvoiceUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <link rel="stylesheet" type="text/css" href="../css/invoice.css"/>
    <script type="text/javascript" src="../js/local/editBooking.js"></script>
    <script type="text/javascript">


        $(function() {
            var dates = $(".date").datepicker({
                mindate: d,
                onSelect: function(selectedDate) {
                    var id = this.id;
                    $.post("../invoices/editInvoiceItem", {"param" : "data",
                        "value":selectedDate, "id":id.substr(0, id.indexOf('_')), "invId":<%=((Invoice) request.getAttribute("invoice")).getId()%> }, function(data) {

                    });
                }
            });

            var d = new Date();
            d.setTime(Date.parse($("#mindate").val()));
            dates.datepicker("option", "minDate", d);

            bodyResize();
        });
        function bodyResize()
        {
            var winHeight = $("body").height();
            var IICheight = (winHeight < 600) ? 197 : winHeight - 315 - $("#toptable").height();
            var sc = (winHeight < 600) ? "visible" : "hidden";
            $("body").css("overflow-y", sc);
            $("#invItemsContent").css("height", IICheight + "px");
            //            var text = "IICheight = " + IICheight;
            //            text += "   topTableHeight = " + $("#toptable").height();
            //            text += "   contentTDHeight = " + $("#contentTD").height();
            //            text += "  bodyHeight = " + $("body").height();
            //            text += "  htmlHeight = " + $("html").height();
            //                 $("#console").text(text);

        }
        function changeBookingState(state)
        {
            $.post("../bookings/editBookingParams", {"param":"currentState", "value":state, "invoice_id":$("#invoice_id").val()},
                    function (response) {

                        if (response.res) {
                            window.location.reload(true);
                        } else {
                            alert(response.message);
                        }


                    });
        }

    </script>
    <style type="text/css">
        label, input {
            display: block;
        }

        #invItemsContent {
            overflow-y: auto;
            height: 405px;
        }

        .topAlign {
            vertical-align: top;
        }
    </style>


</head>

<%

    Invoice invoice = (Invoice) request.getAttribute("invoice");
    Booking  booking = invoice.getBooking();

    String textFieldDisab = "disabled=\"true\"";
    Boolean changesAllowed = true;
    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);

%>
<div id="content">
<div id="topDiv">
<input type="hidden" name="invoice_id" id="invoice_id" value="<%=invoice.getId()%>">
<input type="hidden" name="id" id="id" value="<%= booking.getId() %>">
<input type="hidden" id="mindate" value="<%= booking.getDate().toGMTString() %>">

<div id="toptable">
    <table class="toptable">
        <tr>
            <td>

                <b><%=Booking.curStateAlias[booking.getCurrentState()]%>
                </b>
                Заказ-наряд
                <%=booking.getNumber()%> <%= ((booking.getNumberModifier() == null) || (booking.getNumberModifier().isEmpty())) ? "" : ("/" + booking.getNumberModifier())%>

                <% if (booking.getDate() != null) {%>
                от <%=(new SimpleDateFormat("dd.MM.yyyy")).format(booking.getDate())%><%}%>


                &nbsp; &nbsp; &nbsp; &nbsp;

                <a href="../invoices/view?invoice_id=<%=invoice.getId()%>">по счету
                    &nbsp;<%=invoice.getNumber()%>  <%= ((invoice.getNumberModifier() == null) || (invoice.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%>
                    &nbsp; от <%= (new SimpleDateFormat("dd.MM.yyyy")).format(invoice.getDate()) %>
                </a>


            </td>
            <td>

            </td>
            <td align="right">

                <%--<a href="invoiceAction.do?method=viewInvoice&id=<%=invoice.getId()%>"> <%=invoice.getNumber()%><%= ((invoice.getNumberModifier() == null) || (invoice.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%></a>--%>

            </td>
            <td>

            </td>
            <td></td>
            <td>

            </td>

            <td><%=invoice.getExecutor().getLogin()%>
            </td>
        </tr>
    </table>

    <table class="toptable">
        <tr>
            <td class="width80">
                Заказчик
            </td>
            <td class="width200"><%= (invoice.getEmployer() == null) ? "" : invoice.getEmployer()%>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td class="width100">Назначение:</td>
            <td class="width80"><%=Invoice.purposeAlias[invoice.getPurpose()]%>
            </td>


        </tr>
        <tr>
            <td>Конечный</td>
            <td><%= (invoice.getConsumer() == null) ? "" : invoice.getConsumer()%>
            </td>
            <td></td>
            <td></td>
            <td></td>

            <td></td>
            <td></td>
            <td></td>
            <td>Состояние:</td>
            <td>
                <%=Invoice.curStateAlias[invoice.getCurrentState()]%>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>Оплата</td>
            <td>

                <%=df.format(invoice.getPaymentPercent())%>&nbsp;%

            </td>


        </tr>
    </table>
</div>
<div id="invItems">
    <TABLE class="invItemsTab" id="headerTable">
        <TR>
            <TH class="width20">№</TH>
            <TH class="width550">Наименование продукции</TH>
            <TH class="width90">Кол-во</TH>
            <TH class="width90">Дата пост.</TH>
            <TH class="width90">отгружено</TH>
            <TH class="width90">остаток</TH>


        </TR>
    </TABLE>


    <div id="invItemsContent">


        <TABLE class="invItemsTab" id="contentTable">


            <%
                int k = 1;
                for (int i = 0; i < invoice.getInvoiceItems().size(); i++) {
                    if (invoice.getInvoiceItems().get(i).getType() == 9) continue;
            %>
            <TR>
                <TD class="width20 topAlign">
                    <%=k++%>
                </TD>
                <TD class="width550 topAlign" align="left">

                    <%= invoice.getInvoiceItems().get(i).getName() %>


                </TD>
                <TD class="width90 topAlign">
                    <%= invoice.getInvoiceItems().get(i).getQuantity() %>

                </TD>
                <TD class="width90 topAlign">
                    <input type="text"
                           class="date"
                           value="<%=(new SimpleDateFormat("dd.MM.yyyy")).format(InvoiceItem.dateBeforeNDays(booking.getDate(),invoice.getInvoiceItems().get(i).getDeliveryTime()))%>"
                           onkeypressEn="true" readonly="true"
                           id="<%= invoice.getInvoiceItems().get(i).getId() %>_date"
                    <%--onkeydown="invoiceItemChange('quantity',<%= invoice.getInvoiceItems().get(i).getId() %>,'dig',<%= invoice.getId()%>);"--%>
                            />


                    <%--<%=(new SimpleDateFormat("dd.MM.yyyy")).format(booking.getDate())%>--%>
                    <%--<%--%>

                    <%--Date date = booking.getDate();--%>
                    <%--int deliveryTime        =invoice.getInvoiceItems().get(i).getDeliveryTime();--%>
                    <%--out.print(date);--%>
                    <%--out.print(deliveryTime);--%>

                    <%--try{--%>
                    <%--InvoiceItem.dateBeforeNDays(date,deliveryTime);--%>
                    <%--}catch (Exception e){e.printStackTrace();}// out.print(s);--%>
                    <%--%>--%>


                </TD>
                <TD class="width90 topAlign">


                </TD>
                <TD class="width90 topAlign">


                </TD>


            </TR>
            <%
                }
            %>
        </TABLE>
    </div>
</div>

<table>
    <tr>
        <td>Примечания:</td>
        <td> Комментарий:
        </td>
    </tr>
    <tr>
        <td>
            <textarea name="notes"
                    <%= changesAllowed ? "" : textFieldDisab %>
                      id="bookingNotes" cols="40" rows="3" onkeypressEn="true"
                      onkeydown="paramChange('bookingNotes','any');"><%=(booking.getNotes() == null) ? "" : booking.getNotes()%></textarea>
        <td>
            <textarea id="bookingComments" cols="40" rows="3" onkeypressEn="true"
                      onkeydown="paramChange('bookingComments','any');"><%= (booking.getComments() == null) ? "" : booking.getComments()%></textarea>
        </td>
    </tr>
</table>


</div>


<div id="downButtons">
    <input type="button" value="К счету"
           onclick="location.href='../invoices/view?invoice_id=<%=invoice.getId()%>'" class="butt">

    <sec:authorize access="hasRole('ROLE_ECONOMIST')">

        <%if (InvoiceUtils.isBZapIzmAllowed(booking)) {%>
        <input type="button" value="Запрет изм." onclick="changeBookingState(<%=Booking.STATE_PROIZV%>);" class="butt">
        <% }%>

        <%if (InvoiceUtils.isBAnnulAllowed(booking)) {%>
        <input type="button" value="Аннулировать" onclick="changeBookingState(<%=Booking.STATE_ANN%>);" class="butt">
        <% }%>

        <%if (InvoiceUtils.isBPriostAllowed(booking)) {%>
        <input type="button" value="Приостановить" onclick="changeBookingState(<%=Booking.STATE_PRIOST%>);"
               class="butt">
        <% }%>

        <%if (InvoiceUtils.isBVozobAllowed(booking)) {%>
        <input type="button" value="Возобновить" onclick="changeBookingState(<%=Booking.STATE_CHERN%>);" class="butt">
        <% }%>

        <%if (InvoiceUtils.isBSkladAllowed(booking)) {%>
        <input type="button" value="Склад" onclick="changeBookingState(<%=Booking.STATE_SKLAD%>);" class="butt">
        <% }%>
    </sec:authorize>

    <input type="button" value="Печать"
           onclick="location.href='invoiceAction.do?method=bookingPrint&id=<%= ((Long) request.getAttribute("id"))%>'"
           class="butt">

</div>
</div>

