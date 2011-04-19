<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ page import="application.data.User" %>
<%@ page import="application.data.invoice.*" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title>


    <link rel="stylesheet" type="text/css" href="css/jquery.autocomplete.css"/>
    <link rel="stylesheet" type="text/css" href="css/invoice.css"/>

    <script type="text/javascript" src="/js/ui/jquery-1.4.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery.bgiframe-2.1.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.mouse.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.button.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.draggable.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.position.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.resizable.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.dialog.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.tabs.js"></script>
    <script type="text/javascript" src="js/ui/jquery.effects.core.js"></script>
    <script type='text/javascript' src="js/jquery.autocomplete.js"></script>

    <script src="js/ui/i18n/jquery.ui.datepicker-ru.js"></script>
    <script src="js/ui/jquery.ui.datepicker.js"></script>

    <%--<link type="text/css" href="/Manometr/css/tst/ui.all.css" rel="stylesheet"/>--%>
    <link type="text/css" href=" css/tst/jquery.ui.all.css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function() {
            $("#date").datepicker({
                showOn: 'button',
                buttonImage: 'images/datepicker.jpg',
                buttonImageOnly: true

            });
            $("#ui-datepicker-div").css("z-index", 1000000); //задаем z-index

            bodyResize(115);
        })

        function toggleWidth(element)
        {
            $(".shipColumn").css("width", "30px").text("*");
            if (element) {
                var elem = $(element);
                elem.css("width", "60px");
                elem.text(elem.attr("title"));
            }

        }

        function paramChange(param)
        {
            if ($(param).attr("onkeypressEn") == 'true') {
                $(param).attr("onkeypressEn", "false");

                setTimeout(function() {
                    $(param).attr("onkeypressEn", "true");
                    $.post("invoiceAction.do?method=getShipmentSum", $('#addShipment').serialize(), function(data) {
                        $("#sum").text(data);
                    });
                }
                        , 2000);
            }


        }
        function addShipmentFunc()
        {
            if ($('#shipmentNum').val().length == 0) {
                $('#shipmentNum').addClass('ui-state-error');
                return;
            }

            location.replace("invoiceAction.do?method=addShipment&" + $('#addShipment').serialize());
        }
        function bodyResize(height)
        {
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
    Booking booking = invoice.getBooking();
    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);
    Integer livel = (Integer) request.getSession().getAttribute("livel");
    boolean changesAllowed = (User.LIVEL_ECONOMIST.equals(livel) || User.LIVEL_ADMINISTRATOR.equals(livel)) && (!invoice.getCurrentState().equals(Invoice.STATE_ISP));

%>

<div id="content">


<form action="#" name="addShipment" id="addShipment">


<table class="toptable">
    <tr>
        <td class="width100">
            <%=(invoice.isInvoice()) ? "  Счет" : "  КП"%>
            <a href="invoiceAction.do?method=viewInvoice&id=<%=invoice.getId()%>">

                <b>
                    <%=invoice.getNumber()%>
                    <%= ((invoice.getNumberModifier() == null) || (invoice.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%>
                </b>
            </a>
        </td>
        <td class="width230">
            от <%= (new SimpleDateFormat("dd.MM.yy")).format(invoice.getDate()) %>
            &nbsp;изм.<%= (new SimpleDateFormat("dd.MM.yy")).format(invoice.getChangeDate()) %>
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
            <% if (booking!=null){ %>
            З.Н. &nbsp;
            <a href="invoiceAction.do?method=viewBooking&id=<%=booking.getId()%>">
                <b>
                    <%=booking.getNumber()%>
                    <%= ((booking.getNumberModifier() == null) || (booking.getNumberModifier().isEmpty())) ? "" : ("/" + invoice.getNumberModifier())%>
                </b>
            </a>
            <%}%>
        </td>
        <td> <% if (booking!=null){ %> от <%= (new SimpleDateFormat("dd.MM.yy")).format(booking.getDate()) %>  <%}%>
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
            <%= (invoice.getEmploer() == null) ? "" : invoice.getEmploer().getShortName()%>
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
        <td><%= (invoice.getConsumer() == null) ? "" : invoice.getConsumer().getShortName()  %>
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


    <input type="hidden" name="invoiceId" value="<%=invoice.getId()%>">

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
                        GenericHibernateDAO<ShipmentMediator> SMdao = new GenericHibernateDAO<ShipmentMediator>() {
                        };


                        Map<String, Object> ex = new HashMap();
                        ex.put("invoiceItem", item);
                        ex.put("shipment", shipment);

                        List<ShipmentMediator> result = SMdao.findByExample(ex);
                        if (result.size() > 0)
                            out.print(result.get(0).getCount());

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
           onclick="location.href='invoiceAction.do?method=viewInvoice&id=<%=invoice.getId()%>'" class="butt">


    <%
        if (changesAllowed) {%>
    <input type="button" value="Добавить" onclick="addShipmentFunc()" class="butt">
    <%} %>

</div>

</body>
</html>