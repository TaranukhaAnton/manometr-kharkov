<%@ page import="application.data.Currency" %>
<%@ page import="application.data.User" %>
<%@ page import="application.data.invoice.Booking" %>
<%@ page import="application.data.invoice.Invoice" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
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

<script type="text/javascript">


/*    $(function() {
        var row = $("#invoice tr:eq(0) ");
        $(row).clone().insertAfter($(row));

        $('#invoice tr:eq(0) th:eq(8)').remove();
        $('#invoice tr:eq(0) th:eq(8)').remove();
        $('#invoice tr:eq(0) th:eq(8)').remove();
        $('#invoice tr:eq(0) th:eq(8)').remove();
        $('#invoice tr:eq(0) th:eq(8)').remove();
        //        $('#invoice tr:eq(0) th:eq(8)').remove();
        $('#invoice tr:eq(0) th:eq(7)').attr("colspan", 6);
        $('#invoice tr:eq(0) th:eq(7)').text("продукция");

        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(0)').remove();
        $('#invoice tr:eq(1) th:eq(6)').remove();
        $('#invoice tr:eq(1) th:eq(6)').remove();
        $('#invoice tr:eq(1) th:eq(6)').remove();
        $('#invoice tr:eq(1) th:eq(6)').remove();
        $('#invoice tr:eq(1) th:eq(6)').remove();
        $('#invoice tr:eq(1) th:eq(6)').remove();

        $('#invoice tr:eq(0) th:eq(0)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(1)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(2)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(3)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(4)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(5)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(6)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(8)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(9)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(10)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(11)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(12)').attr("rowspan", 2);
        $('#invoice tr:eq(0) th:eq(13)').attr("rowspan", 2);


        $("#date").datepicker({
            showOn: 'button',
            buttonImage: 'images/datepicker.jpg',
            buttonImageOnly: true
        });
        var dates = $("#f2_from, #f2_to").datepicker({
            showOn: 'button',
            buttonImage: 'images/datepicker.jpg',
            buttonImageOnly: true,
            onSelect: function(selectedDate) {
                var option = this.id == "f2_from" ? "minDate" : "maxDate";
                var instance = $(this).data("datepicker");
                var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                dates.not(this).datepicker("option", option, date);
            }
        });

        $("#emploer").autocomplete("invoiceAction.do?method=findCustomers", {
            width :260,
            selectFirst :false

        });
        $("#consumer").autocomplete("invoiceAction.do?method=findCustomers", {
            width :260,
            selectFirst :false
        });


        $("#ui-datepicker-div").css("z-index", 1000000); //задаем z-index

        $("#newInvoice-div").dialog({
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            resizable:false,
            buttons: {
                'Создать': function() {
                    $('#number').removeClass("error");
                    $('#numberModifier').removeClass("error");
                    $('#date').removeClass("error");
                    $('#consumer').removeClass("error");
                    $('#emploer').removeClass("error");

                    //                        $.post("invoiceAction.do?method=verifyInvoicePresence", {"isInvoice" : $('[name=isInvoice]:checked').val(), "number":$('#number').val(), "numberModifier":$('#numberModifier').val(), "date":$('#date').val() }, function(data) {
                    $.post("invoiceAction.do?method=verifyInvoicePresence", $('#newInvoice_form').serialize(), function(data) {
                        if (data.length > 0) {
                            var response = eval("(" + data + ")");
                            if (response.correct) {
                                location.replace("invoiceAction.do?method=addInvoice&" + $('#newInvoice_form').serialize());
                            } else {
                                alert(response.mes);
                                if (!response.presence) {
                                    $('#number').addClass("error");
                                    $('#numberModifier').addClass("error");
                                    $('#date').addClass("error");
                                }
                                if (!response.emploer) {
                                    $('#emploer').addClass("error");
                                }
                                if (!response.consumer) {
                                    $('#consumer').addClass("error");
                                }
                            }
                        }
                    });


                },
                'Отмена': function() {
                    $(this).dialog('close');
                }
            },
            open: function(event, ui) {
                $('body').css('overflow', 'hidden');
                $('.ui-widget-overlay').css('width', '100%');
            },
            close: function(event, ui) {
                $('body').css('overflow', 'auto');
            }
        });
        $("#filter-form").dialog({
            autoOpen: false,
            height: 550,
            width: 600,
            modal: true,
            resizable:false,
            buttons: {
                'Применить': function() {
                    location.replace("invoiceAction.do?method=setupFilter&" + $('#filterForm').serialize());
                },
                'Очистить': function() {
                    // alert($('#filterForm').serialize());

                    $(this).dialog('close');
                },
                'Отмена': function() {
                    $(this).dialog('close');
                }
            },
            open: function(event, ui) {
                $('body').css('overflow', 'hidden');
                $('.ui-widget-overlay').css('width', '100%');
            },
            close: function(event, ui) {
                $('body').css('overflow', 'auto');
            }
        });
        $("#tabs").tabs();

    });
    function openFilterWindow() {
        $.post("invoiceAction.do?method=getFilter", "", function(data) {
            var response = eval("(" + data + ")");
            $('#filterForm').deserialize(response);
        });
        $('#filter-form').dialog('open');
    }

    function removeErrorClass() {
        $('#number').removeClass("error");
        $('#numberModifier').removeClass("error");
        $('#date').removeClass("error");
        $('#emploer').removeClass("error");
        $('#consumer').removeClass("error");
    }*/


</script>

<style type="text/css">
    #content {
        margin: 10px;
    }

    .tabdiv {
        height: 300px; /*overflow-y: scroll;*/
    }

    /*#users {*/
    /*height: 145px;*/
    /*overflow-y: scroll; */

    /*}*/

    .error {
        border-color: #ff9999;
        border-width: 2px;
    }

    .col30 {
        width: 35px;

    }

    #invoice th {
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
<% //User u = Factory.getUserDAO().findById((Long) request.getSession().getAttribute("logonUserId"));
   // request.setAttribute("invoices", (u == null) ? Factory.getInvoiceDAO().findAll() : (u.getInvoiceFilter() != null) ? u.getInvoiceFilter().doFilter() : Factory.getInvoiceDAO().findAll());
    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
%>


<DIV ID="content">

    <display:table name="invoices" requestURI="invoiceAction.do?method=viewInvoices" excludedParams="method"
                   requestURIcontext="false" pagesize="20" sort="list"
                   class="simple" id="invoice">
        <display:column title="заказчик" maxLength="15">
            <a href="CustomerProcess.do?method=setUpForInsertOrUpdate&id=<%=((Invoice) pageContext.getAttribute("invoice")).getEmploer().getId()%>"><%=((Invoice) pageContext.getAttribute("invoice")).getEmploer().getShortName()%></a>
        </display:column>
        <display:column property="executor" class="center" title="спец <br> ОСО"/>



        <display:column url="/invoiceAction.do?method=viewInvoice" title="№" class="right" paramId="id" paramProperty="id">
            ${invoice.number} ${(invoice.numberModifier=="")?"":"/"} ${invoice.numberModifier}
        </display:column>


        <display:column title="з/н">
            <%  Booking booking = ((Invoice) pageContext.getAttribute("invoice")).getBooking();
                if (booking != null) {
            %>
            <a href="invoiceAction.do?method=viewBooking&id=<%=booking.getId()%>"><%=booking.getNumber()%><%= ((booking.getNumberModifier() == null) || (booking.getNumberModifier().isEmpty())) ? "" : ("/" + booking.getNumberModifier())%>
            </a>
            <% } %>
        </display:column>


        <display:column title="дата">
            <%  Booking booking = ((Invoice) pageContext.getAttribute("invoice")).getBooking();
                if (booking != null)
                   out.println(sdf.format(booking.getDate()));  %>
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

        <display:column title="Сумма,<br> тыс" class="right">
            <%=df.format(((Invoice) pageContext.getAttribute("invoice")).getTotal().divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP)) %>
        </display:column>
            <display:column title="долг">

        </display:column>

        <display:column title="Оплата,<br> %" class="right">
            <%=df.format(((Invoice) pageContext.getAttribute("invoice")).getPaymentPercent()) %>
        </display:column>
        <display:column title="долг, <br> % ">

        </display:column>

        <display:column title="дней">

        </display:column>

        <display:column title="отгружено">

        </display:column>




    </display:table>
</DIV>


</body>
</html>