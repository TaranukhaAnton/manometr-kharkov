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
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Simple jsp page</title>
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
<%--<script type="text/javascript" src="../../jquery-1.4.2.js"></script> --%>
<%--<script type="text/javascript" src="../../ui/jquery.ui.core.js"></script> --%>
<%--<script type="text/javascript" src="../../ui/jquery.ui.widget.js"></script> --%>
<%--<script type="text/javascript" src="../../ui/jquery.ui.datepicker.js"></script> --%>


<link type="text/css" href=" css/tst/jquery.ui.all.css" rel="stylesheet"/>
<%--<link type="text/css" href=" css/tst/jquery-ui.css" rel="stylesheet"/>--%>
<%--<link rel="stylesheet" type="text/css" href="css/datepicker.css"/>--%>

<%--//  <script src="js/datepicker.js" type="text/javascript" charset="UTF-8" language="javascript"></script>--%>
<script src="js/ui/i18n/jquery.ui.datepicker-ru.js"></script>
<script src="js/ui/jquery.ui.datepicker.js"></script>

<script type="text/javascript">


    $(function() {
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

    function removeErrorClass()
    {
        $('#number').removeClass("error");
        $('#numberModifier').removeClass("error");
        $('#date').removeClass("error");
        $('#emploer').removeClass("error");
        $('#consumer').removeClass("error");
    }
    function intInput(evt) {
        var theEvent = evt || window.event;
        key = String.fromCharCode(theEvent.keyCode || theEvent.which);
        return /\d/.test(key);
    }

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
<%
    // User u = (User) request.getSession().getAttribute("logonUser");
    User u = Factory.getUserDAO().findById((Long) request.getSession().getAttribute("logonUserId"));


    request.setAttribute("invoices", (u == null) ? Factory.getInvoiceDAO().findAll() : (u.getInvoiceFilter() != null) ? u.getInvoiceFilter().doFilter() : Factory.getInvoiceDAO().findAll());
    // Invoice invoice  =
    NumberFormat df = NumberFormat.getInstance();
    df.setMinimumFractionDigits(2);
    df.setMaximumFractionDigits(2);
%>


<DIV ID="content">


    <%--<x:parse var="bookshelf">--%>
    <%--<jsp:include page="code.php.xml"/>--%>
    <%--</x:parse>--%>

    <%--<c:set var="uri" value="<%=request.getRequestURI() %>" />--%>
    <%--<x:out select="$bookshelf/sape/page[@uri =$uri ]" escapeXml="false"/>--%>

    <% if (((Integer) session.getAttribute("level")) > 2) {%>
    <a href="javascript:void($('#newInvoice-div').dialog('open'))">Добавить</a><br>
    <% } %>


    <a href="javascript:void(openFilterWindow())">Фильтр</a><br>
    <%--<a href="javascript:void($('#filter-form').dialog('open'))">Фильтр</a><br>--%>
    <br>
    <%--<c:br>--%>
    <display:table keepStatus="true" name="invoices" requestURI="invoiceAction.do?method=viewInvoices" excludedParams="method"
                   requestURIcontext="false" pagesize="20" sort="list"
                   class="simple" id="invoice" >


        <display:column>
            <%=(((Invoice) pageContext.getAttribute("invoice")).isInvoice()) ? "" : "к"%>

        </display:column>
        <%--number--%>
        <display:column url="/invoiceAction.do?method=viewInvoice"  title="№" class="right" paramId="id" paramProperty="id" property="number" sortable="true"/>

        <display:column property="numberModifier" title="м" class="left"/>

        <display:column title="дата" sortable="true" format="{0,date,dd.MM.yyyy}" property="date" />

        <display:column title="Назн" class="center">
            <%= Invoice.purposeAlias[((Invoice) pageContext.getAttribute("invoice")).getPurpose()]%>
        </display:column>
        <display:column title="заказчик" maxLength="15" url= "/CustomerProcess.do?method=setUpForInsertOrUpdate"   paramId="id" paramProperty="emploer.id" property="emploer.shortName" />

        <display:column property="executor" class="center" title="спец <br> ОСО"/>

        <display:column property="t0" class="col30 center" title="ДД"/>
        <display:column property="t1" class="col30 center" title="БП"/>
        <display:column property="t2" class="col30 center" title="ДКС"/>
        <display:column property="t3" class="col30 center" title="ИБ"/>
        <display:column property="t4" class="col30 center" title="пр"/>
        <display:column property="t5" class="col30 center" title="стор"/>

        <display:column title="Сумма,<br> тыс" class="right">
            <%=df.format(((Invoice) pageContext.getAttribute("invoice")).getTotal().divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP)) %>
        </display:column>
        <display:column title="Нац-ка" class="right">
            <%=df.format(((Invoice) pageContext.getAttribute("invoice")).getAdditionToPrice()) %>
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
            <a href="invoiceAction.do?method=viewBooking&id=<%=booking.getId()%>"><%=booking.getNumber()%><%= ((booking.getNumberModifier() == null) || (booking.getNumberModifier().isEmpty())) ? "" : ("/" + booking.getNumberModifier())%>
            </a>
            <%

                }
            %>
        </display:column>

        <display:column title="Оплата,<br> %" class="right">
            <%=df.format(((Invoice) pageContext.getAttribute("invoice")).getPaymentPercent()) %>
        </display:column>


        <% if (((Integer) session.getAttribute("level")) == 4) {%>
        <display:column title="">
            <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='invoiceAction.do?method=deleteInvoice&id=${invoice.id}'">
                <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
            </a>
        </display:column>
        <% } %>

    </display:table>
</DIV>


<div id="newInvoice-div" title="Добавить счет/кп">
    <Br>

    <form action="#" id="newInvoice_form">
        <table>
            <tr>
                <td><input type="radio" name="isInvoice" id="isInv" value="true" checked onclick="removeErrorClass()">
                </td>

                <td>
                    <label for="isInv">Счет</label></td>
            </tr>
            <tr>
                <td><input type="radio" name="isInvoice" id="isKp" value="false" onclick="removeErrorClass()"></td>
                <td><label for="isKp">КП</label></td>
            </tr>
            <tr>
                <td><label for="number">Номер</label></td>
                <td><input type="text" name="number" id="number" class="text ui-widget-content ui-corner-all"
                           onclick="removeErrorClass()" onkeypress='return intInput(event)'/></td>

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
                <td><label for="emploer">Заказчик</label></td>
                <td><input type="text" id="emploer" name="emploer" onclick="removeErrorClass()"
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

<div id="filter-form" title="Фильтр">

    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">Счет/КП</a></li>
            <li><a href="#tabs-2">Номер</a></li>
            <li><a href="#tabs-3">Дата</a></li>
            <li><a href="#tabs-4">Назначение</a></li>
            <li><a href="#tabs-5">Состояние</a></li>
            <li><a href="#tabs-6">Валюта</a></li>
            <% if (((Integer) session.getAttribute("level")) > 2) {%>
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
            <% if (((Integer) session.getAttribute("level")) > 2) {%>
            <div id="tabs-7" class="tabdiv">

                <%--<div id="users">--%>


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

</div>
</body>
</html>