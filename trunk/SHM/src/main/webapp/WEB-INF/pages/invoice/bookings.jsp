<%@ page import="application.data.User" %>
<%@ page import="application.data.invoice.Booking" %>
<%@ page import="application.data.invoice.Invoice" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Simple jsp page</title>
    <link type="text/css" href="/Manometr/css/smartTable.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/ui/jquery-1.4.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery.bgiframe-2.1.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.mouse.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.button.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.draggable.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.position.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.resizable.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.dialog.js"></script>
    <script type="text/javascript" src="js/ui/jquery.effects.core.js"></script>
    <link type="text/css" href="css/tst/jquery.ui.all.css" rel="stylesheet"/>
    <%--<link type="text/css" href=" css/tst/ui.all.css" rel="stylesheet"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="css/datepicker.css"/>--%>

    <%--<script src="js/datepicker.js" type="text/javascript" charset="UTF-8" language="javascript"/>--%>


    <script type="text/javascript">
        $(function() {

            var row = $("#booking tr:eq(0) ");

            $(row).clone().insertAfter($(row));

            $('#booking tr:eq(0) th:eq(4)').remove();
            //            $('#booking tr:eq(0) th:eq(4)').remove();
            $('#booking tr:eq(0) th:eq(3)').attr("colspan", 2);
            $('#booking tr:eq(1) th:eq(3)').remove();
            $('#booking tr:eq(1) th:eq(3)').remove();

            $('#booking tr:eq(0) th:eq(3)').attr("rowspan", 2);
            $('#booking tr:eq(0) th:eq(3)').text("по счету");

            $('#booking tr:eq(0) th:eq(7)').remove();
            $('#booking tr:eq(0) th:eq(7)').remove();
            $('#booking tr:eq(0) th:eq(7)').remove();
            $('#booking tr:eq(0) th:eq(7)').remove();
            $('#booking tr:eq(0) th:eq(7)').remove();
            $('#booking tr:eq(0) th:eq(6)').attr("colspan", 6);
            $('#booking tr:eq(0) th:eq(6)').text("продукция");
            $('#booking tr:eq(0) th:eq(8)').remove();
            $('#booking tr:eq(0) th:eq(7)').attr("colspan", 2);
            $('#booking tr:eq(0) th:eq(7)').text("обязательства");

            $('#booking tr:eq(1) th:eq(0)').remove();
            $('#booking tr:eq(1) th:eq(0)').remove();
            $('#booking tr:eq(1) th:eq(0)').remove();
            $('#booking tr:eq(1) th:eq(0)').remove();
            $('#booking tr:eq(1) th:eq(0)').remove();
            $('#booking tr:eq(1) th:eq(8)').remove();

            $('#booking tr:eq(0) th:eq(0)').attr("rowspan", 2);
            $('#booking tr:eq(0) th:eq(1)').attr("rowspan", 2);
            $('#booking tr:eq(0) th:eq(2)').attr("rowspan", 2);
            $('#booking tr:eq(0) th:eq(4)').attr("rowspan", 2);
            $('#booking tr:eq(0) th:eq(5)').attr("rowspan", 2);
            $('#booking tr:eq(0) th:eq(8)').attr("rowspan", 2);
        });


    </script>


    <style type="text/css">
        #content {
            margin: 10px;
        }

        #booking th {
            text-align: center;
        }

        .right {
            text-align: right;

        }

        .center {
            text-align: center;

        }

        .left {
            text-align: left;

        }

    </style>

</head>
<body>
<%
    try {
       // List<Booking> result = Factory.getBookingDAO().findAll();
        User u = Factory.getUserDAO().findById((Long) request.getSession().getAttribute("logonUserId"));

        request.setAttribute("bookings", (u == null) ? Factory.getBookingDAO().findAll() : (u.getInvoiceFilter() != null) ?
                u.getInvoiceFilter().doBookingFilter() : Factory.getBookingDAO().findAll());
       // request.setAttribute("bookings", result);
    } catch (Exception e) {
        e.printStackTrace();
    }

    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

%>


<DIV ID="content">

    <display:table name="bookings" requestURI="suplierAction.do?method=viewBookings" excludedParams="method"
                   requestURIcontext="false" pagesize="20" sort="list"
                   class="simple" id="booking">
        <display:column title="сост">
            <%=Booking.curStateAlias[((Booking) pageContext.getAttribute("booking")).getCurrentState()]%>
        </display:column>


        <display:column url="/invoiceAction.do?method=viewBooking" paramId="id"
                        paramProperty="id">${booking.number}${(booking.numberModifier!="")? "/":""}${booking.numberModifier}</display:column>
        <%--<display:column>--%>
        <%--</display:column>--%>
        <display:column title="открыт">
            <%=sdf.format(((Booking) pageContext.getAttribute("booking")).getDate())%>
        </display:column>


        <display:column url="/invoiceAction.do?method=viewInvoice"
                        paramId="id" paramProperty="invoice.id">
            ${booking.invoice.number}${(booking.invoice.numberModifier!="")? "/":""}${booking.invoice.numberModifier}
        </display:column>


        <display:column title="Назн">
            <%= Invoice.purposeAlias[((Booking) pageContext.getAttribute("booking")).getInvoice().getPurpose()] %>
        </display:column>
        <display:column property="invoice.employer.shortName" title="заказчик"/>

        <display:column property="invoice.executor" title="спец<br>ОСО"/>
        <display:column property="invoice.t0" class="col30 center" title="ДД"/>
        <display:column property="invoice.t1" class="col30 center" title="БП"/>
        <display:column property="invoice.t2" class="col30 center" title="ДКС"/>
        <display:column property="invoice.t3" class="col30 center" title="ИБ"/>
        <display:column property="invoice.t4" class="col30 center" title="пр"/>
        <display:column property="invoice.t5" class="col30 center" title="стор"/>

        <display:column title="нач">
            <%= (((Booking) pageContext.getAttribute("booking")).getSupplierObligations1() != null) ? sdf.format(((Booking) pageContext.getAttribute("booking")).getSupplierObligations1()) : ""%>
        </display:column>
        <display:column title="кон">
            <%=(((Booking) pageContext.getAttribute("booking")).getSupplierObligations2() != null) ? sdf.format(((Booking) pageContext.getAttribute("booking")).getSupplierObligations2()) : ""%>
        </display:column>

        <display:column title="Оплата,<br> %" class="right">
            <%=df.format(((Booking) pageContext.getAttribute("booking")).getInvoice().getPaymentPercent()) %>
        </display:column>


    </display:table>
</DIV>


</body>
</html>