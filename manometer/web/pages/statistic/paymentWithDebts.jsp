<%@ page import="application.data.Currency" %>
<%@ page import="application.data.User" %>
<%@ page import="application.data.invoice.Booking" %>
<%@ page import="application.data.invoice.Invoice" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>


<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title></title>
    <link type="text/css" href=" css/smartTable.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/ui/jquery-1.4.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery-ui.js"></script>
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
    <script type="text/javascript" src="js/jquery.deserialize.js"></script>
    <script type='text/javascript' src="js/jquery.autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="css/jquery.autocomplete.css"/>
    <link type="text/css" href=" css/tst/jquery.ui.all.css" rel="stylesheet"/>
    <script src="js/ui/i18n/jquery.ui.datepicker-ru.js"></script>
    <script src="js/ui/jquery.ui.datepicker.js"></script>

 <style type="text/css">
      .col100 {
        width: 200px;

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

    <display:table keepStatus="true"  name="invoices" requestURI="statisticAction.do?method=viewInvoicesWithDebts" excludedParams="method"
                   requestURIcontext="false" pagesize="20" sort="list"
                   class="simple" id="invoice">


        <display:column title="заказчик" maxLength="60" sortable="true" class="col100">
            <a href="CustomerProcess.do?method=setUpForInsertOrUpdate&id=<%=((Invoice) pageContext.getAttribute("invoice")).getEmploer().getId()%>"><%=((Invoice) pageContext.getAttribute("invoice")).getEmploer().getShortName()%>
            </a>
        </display:column>
        <display:column property="executor" class="center" title="спец <br> ОСО" sortable="true"/>


        <display:column url="/invoiceAction.do?method=viewInvoice" title="№" class="right" paramId="id"
                        paramProperty="id" sortable="true">
            ${invoice.number} ${(invoice.numberModifier=="")?"":"/"} ${invoice.numberModifier}
        </display:column>


        <display:column title="з/н">
            <% Booking booking = ((Invoice) pageContext.getAttribute("invoice")).getBooking();
                if (booking != null) {
            %>
            <a href="invoiceAction.do?method=viewBooking&id=<%=booking.getId()%>"><%=booking.getNumber()%><%= ((booking.getNumberModifier() == null) || (booking.getNumberModifier().isEmpty())) ? "" : ("/" + booking.getNumberModifier())%>
            </a>
            <% } %>
        </display:column>


        <display:column title="дата">
            <% Booking booking = ((Invoice) pageContext.getAttribute("invoice")).getBooking();
                if (booking != null)
                    out.println(sdf.format(booking.getDate())); %>
        </display:column>

        <%--<display:column title="Назн" class="center">--%>
        <%--<%= Invoice.purposeAlias[((Invoice) pageContext.getAttribute("invoice")).getPurpose()]%>--%>
        <%--</display:column>--%>


        <display:column property="t0" class="col30 center" title="ДД"/>
        <display:column property="t1" class="col30 center" title="БП"/>
        <display:column property="t2" class="col30 center" title="ДКС"/>
        <display:column property="t3" class="col30 center" title="ИБ"/>
        <display:column property="t4" class="col30 center" title="пр"/>
        <display:column property="t5" class="col30 center" title="стор"/>


        <display:column property="supplier.currency.name" class="center" title="Вал"/>

        <display:column title="Сумма,<br> тыс" class="right" sortable="true">
            <%=((Invoice) pageContext.getAttribute("invoice")).getTotal().divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP)%>
        </display:column>
        <display:column property="debt" title="Долг" sortable="true"/>
        <display:column property="paymentPercent" title="Оплата,<br> %" class="right" sortable="true" />
        <display:column property="debtPercent" title="Долг, <br> % " sortable="true" />
        <display:column property="debtDayCount" title="дней" sortable="true"/>
        <display:column title="отгружено"/>


    </display:table>
</DIV>


</body>
</html>