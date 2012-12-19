<%@ page import="ua.com.manometer.model.invoice.Invoice" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="ua.com.manometer.model.User" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="ua.com.manometer.model.invoice.Booking" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script type="text/javascript" src="../js/local/invoices.js"></script>
<link rel="stylesheet" type="text/css" href="../css/invoice.css"/>


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


    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

%>


<DIV ID="content">

    <display:table name="listBookings" requestURI="suplierAction.do?method=viewBookings" excludedParams="method"
                   requestURIcontext="false" pagesize="20" sort="list"
                   class="simple" id="booking">
        <display:column title="сост">
            <%=Booking.curStateAlias[((Booking) pageContext.getAttribute("booking")).getCurrentState()]%>
        </display:column>


        <display:column url="/bookings/view" title="№"  paramId="invoice_id" paramProperty="invoice.id" >
        ${booking.number}${(booking.numberModifier!="")? "/":""}${booking.numberModifier}
        </display:column>

        <display:column title="открыт">
            <%=sdf.format(((Booking) pageContext.getAttribute("booking")).getDate())%>
        </display:column>


        <display:column url="/invoices/view" title="№"  paramId="invoice_id" paramProperty="invoice.id" >
            ${booking.invoice.number}${(booking.invoice.numberModifier!="")? "/":""}${booking.invoice.numberModifier}
        </display:column>


        <display:column title="Назн">
            <%= Invoice.purposeAlias[((Booking) pageContext.getAttribute("booking")).getInvoice().getPurpose()] %>
        </display:column>
        <display:column property="invoice.employer" title="заказчик"/>

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
            <%--<%=df.format(((Booking) pageContext.getAttribute("booking")).getInvoice().computePaymentPercent()) %>--%>
        </display:column>


    </display:table>
</DIV>


</body>
</html>