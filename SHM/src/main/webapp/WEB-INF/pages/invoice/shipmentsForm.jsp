<%@ page import="ua.com.manometer.model.invoice.Invoice" %>
<%@ page import="ua.com.manometer.model.invoice.Booking" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="ua.com.manometer.model.invoice.Shipment" %>
<%@ page import="ua.com.manometer.model.invoice.InvoiceItem" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title>



    <link rel="stylesheet" type="text/css" href="../css/invoice.css"/>
    <script type="text/javascript">
        $(function() {
            $("#date").datepicker({
                showOn: 'button',
                buttonImage: '../images/datepicker.jpg',
                buttonImageOnly: true

            });
            $("#ui-datepicker-div").css("z-index", 1000000); //задаем z-index

            bodyResize(115);
        })

        function toggleWidth(element) {
            $(".shipColumn").css("width", "30px").text("*");
            if (element) {
                var elem = $(element);
                elem.css("width", "60px");
                elem.text(elem.attr("title"));
            }

        }

        function paramChange(param) {
            if ($(param).attr("onkeypressEn") == 'true') {
                $(param).attr("onkeypressEn", "false");

                setTimeout(function() {
                    $(param).attr("onkeypressEn", "true");
                    $.post("../invoices/get_shipment_sum", $('#addShipment').serialize(), function(data) {
                        $("#sum").text(data.sum);
                    });
                }
                        , 2000);
            }


        }
        function addShipmentFunc() {
            if ($('#shipmentNum').val().length == 0) {
                $('#shipmentNum').addClass('ui-state-error');
                return;
            }

            location.replace("../invoices/add_shipment?" + $('#addShipment').serialize());
        }
        function bodyResize(height) {
            var winHeight = $("body").height();
            var footerHeight = $("#total").height();
            var IICheight = (winHeight < 600) ? 210 : winHeight - 389 - $("#toptable").height();
            var sc = (winHeight < 600) ? "visible" : "hidden";
            IICheight += height;
            $("body").css("overflow-y", sc);

//            var text = "winHeihgt = " + winHeight;
//            text += "   IICheight = " + IICheight;
//            text += "   total = " + footerHeight;
//            //        text += "   contentTDHeight = " + $("#contentTD").height();
//            //        text += "  bodyHeight = " + $("body").height();
//            //        text += "  htmlHeight = " + $("html").height();
//
//
//            $("#console").text(text);


            $("#variableHeightElement").css("height", IICheight + "px");

        }


    </script>
    <style type="text/css">


        /*#invItems {*/
        /*overflow-y: auto;*/
        /*height: 405px;*/
        /*}*/

        #invItems {
            margin-top: 10px;
        }

    </style>


</head>
<body onResize="bodyResize(110);">
<%
    Invoice invoice = (Invoice) request.getAttribute("invoice");
    Map<Long, Map<Long,Integer>> map =  ( Map<Long, Map<Long,Integer>>) request.getAttribute("map");
    Booking booking = invoice.getBooking();
    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);
   // Integer livel = (Integer) request.getSession().getAttribute("livel");
//    boolean changesAllowed = (User.LIVEL_ECONOMIST.equals(livel) || User.LIVEL_ADMINISTRATOR.equals(livel)) && (!invoice.getCurrentState().equals(Invoice.STATE_ISP));
    boolean changesAllowed = true;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");

%>

<div id="content">


<form action="#" name="addShipment" id="addShipment">


<table class="toptable">
    <tr>
        <td class="width100">
            <%=(invoice.isInvoice()) ? "  Счет" : "  КП"%>
            <a href="../invoices/view?invoice_id=<%=invoice.getId()%>">

                <b>
                    <%=invoice.getNumber()%>
                    <%= ((invoice.getNumberModifier() == null) || (invoice.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%>
                </b>
            </a>
        </td>
        <td class="width230">
            от <%= simpleDateFormat.format(invoice.getDate()) %>
            <% if (invoice.getChangeDate() != null) {
                out.print("&nbsp; изм." + simpleDateFormat.format(invoice.getChangeDate()));
            } %>
        </td>
        <td></td>
        <td class="width80"></td>
        <td class="width140"></td>
        <td></td>
        <td></td>
        <td></td>
        <td class="width100">Менеджер</td>
        <td class="width80"><%=invoice.getExecutor().getLogin()%>
        </td>


    </tr>
    <tr>

        <td>
            <% if (booking != null) { %>
            З.Н. &nbsp;
            <a href="../bookings/view?invoice_id=<%=invoice.getId()%>">
                <b>
                    <%=booking.getNumber()%>
                    <%= ((booking.getNumberModifier() == null) || (booking.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%>
                </b>
            </a>
            <%}%>
        </td>
        <td><% if (booking != null) { %> от <%= (new SimpleDateFormat("dd.MM.yy")).format(booking.getDate()) %>  <%}%>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>Назначение</td>
        <td><%=Invoice.purposeAlias[invoice.getPurpose()]%>
        </td>


    </tr>
    <tr>
        <td>
            Заказчик &nbsp;
        </td>
        <td>
            <%= (invoice.getEmployer() == null) ? "" : invoice.getEmployer()%>
        </td>


        <td></td>
        <td><%if (changesAllowed) {%> Дата <%} %>
        </td>
        <td>
            <%
                if (changesAllowed) {%>
            <input type="text" name="date" id="date" class="width100"
                   value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>">
            <%} %>


        </td>
        <td></td>


        <td></td>
        <td></td>
        <td>Cостояние</td>
        <td>
            <%=Invoice.curStateAlias[invoice.getCurrentState()]%>
        </td>
    </tr>
    <tr>
        <td>Конечный &nbsp; </td>
        <td><%= (invoice.getConsumer() == null) ? "" : invoice.getConsumer()  %>
        </td>
        <td></td>
        <td><%if (changesAllowed) {%> Накладная <%} %></td>
        <td>
            <%if (changesAllowed) {%>
            <input type="text" class="width100" value="" name="shipmentNum" id="shipmentNum"
                   onclick="javascript:void($('#shipmentNum').removeClass('ui-state-error'))">
            <%} %>
        </td>

        <td></td>
        <td></td>

        <td></td>


        <td>Оплата</td>
        <td><%=df.format(invoice.getPaymentPercent())%>&nbsp;%</td>


    </tr>
</table>


<div id="invItems">


    <input type="hidden" name="invoice_id" value="<%=invoice.getId()%>">

    <div id="variableHeightElement">
        <TABLE class="invItemsTab" id="invItemsTab">
            <tr>
                <th class="width25" onclick="toggleWidth()">№</th>
                <th class="width550" onclick="toggleWidth()">Наименование продукции</th>
                <th class="width90" onclick="toggleWidth()">Дата пост.</th>
                <th class="width90" onclick="toggleWidth()">кол-во</th>
                <th class="width90" onclick="toggleWidth()">отгруж.</th>
                <th class="width90" onclick="toggleWidth()">остаток</th>


                <%
                    if (changesAllowed) {%>
                <th class="width90" onclick="toggleWidth()"> тек.<br> отгр.</th>
                <%} %>


                <%
                    int k = 0;
                    if (invoice.getShipments() != null)
                        for (Shipment ship : invoice.getShipments()) {
                %>
                <th class="width32 selectOnHover shipColumn"
                    title="<%=ship.getShipmentNum()%> &nbsp;<%=(new SimpleDateFormat("dd.MM.yy")).format(ship.getDate())%>"
                    onclick="toggleWidth(this)"> *
                </th>
                <% } %>


            </tr>


            <%--<c:forEach var="item" items="<%=  %>">--%>
            <%
                k = 1;
                for (InvoiceItem item : invoice.getInvoiceItems()) { %>
            <tr>
                <td><%=k++%>
                </td>
                <td class="alLeft"><%=item.getName()%>
                </td>
                <td><%=(new SimpleDateFormat("dd.MM.yyyy")).format(InvoiceItem.dateBeforeNDays(booking.getDate(), item.getDeliveryTime()))%>
                </td>
                <td><%=item.getQuantity()%>
                </td>
                <td><%=item.getTotalShipments()%>
                </td>
                <td><%=item.getQuantity() - item.getTotalShipments()%>
                </td>

                <%
                    if (changesAllowed) {%>
                <td><input type="text" id="invItId<%=item.getId()%>" class="width50" onkeydown="paramChange(this);"
                           onkeypressEn="true"
                           name="invItId<%=item.getId()%>"></td>
                <%} %>


                <% if (invoice.getShipments() != null) {
                    for (Shipment shipment : invoice.getShipments()) {%>
                <td class="hideColumn">
                  <%
                      Integer count = map.get(shipment.getId()).get(item.getId());
                      if (count!=null)
                      out.print(count);
                    %>
                </td>
                <% }
                } %>
            </tr>
            <% }
                if (changesAllowed) {%>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>


                <td>
                    <div id="sum">0.00</div>
                </td>


                <%
                    if (invoice.getShipments() != null)
                        for (Shipment ship : invoice.getShipments()) {%>
                <td></td>
                <% } %>


            </tr>
            <%} %>

        </table>
    </div>
</div>
</form>


</div>
<div id="downButtons">


    <input type="button" value="К счету"
           onclick="location.href='../invoices/view?invoice_id=<%=invoice.getId()%>'" class="butt">


    <%
        if (changesAllowed) {%>
    <input type="button" value="Добавить" onclick="addShipmentFunc()" class="butt">
    <%} %>

</div>

</body>
</html>