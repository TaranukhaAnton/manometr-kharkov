
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="ua.com.manometer.model.invoice.Invoice" %>
<%@ page import="ua.com.manometer.model.invoice.Booking" %>
<%@ page import="ua.com.manometer.model.invoice.Payment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




    <link rel="stylesheet" type="text/css" href="../css/invoice.css"/>


    <script type="text/javascript">
        $(function() {
            $("#date").datepicker({
                showOn: 'button',
                buttonImage: '../images/datepicker.jpg',
                buttonImageOnly: true

            });
            $("#ui-datepicker-div").css("z-index", 1000000); //задаем z-index


            $("#addPayment").dialog({
                autoOpen: false,
                height: 450,
                width: 450,
                modal: true,
                resizable:false,
                buttons: {
                    'Создать': function() {

                        $('#paymentSum').removeClass('ui-state-error');
                        $('#exchangeRate').removeClass('ui-state-error');
                        var err = false;
                        if ($('#paymentSum').val().length == 0) {
                            $('#paymentSum').addClass('ui-state-error');
                            err = true;
                        }
                        if ($('#exchangeRate').val().length == 0) {
                            $('#exchangeRate').addClass('ui-state-error');
                            err = true;
                        }
                        if (err) return;
                        // if
                        //  var exchangeRate = $('#exchangeRate').value;

                        location.replace("./add_payment?" + $('#addPayment_form').serialize());
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
            bodyResize(24);

        })
        function digInput(evt, input) {
            var theEvent = evt || window.event;
            var key = theEvent.keyCode || theEvent.which;

            if ((key == 8) | (key == 37) | (key == 39)) return true;
            // alert(key);
            key = String.fromCharCode(key);
            // alert(key);
            var regex = /[-|\d|\.|,]/;
            if (!regex.test(key)) {
                theEvent.returnValue = false;
                theEvent.preventDefault();
                return false;
            }

            var pos = getCaretPos(input);
            var value = input.value;
            value = (value.substr(0, pos) + key + value.substr(pos)).replace(",", ".");


            regex = /^-?\d*(\.\d{0,2})?$/
            if (!regex.test(value)) {
                theEvent.returnValue = false;
                theEvent.preventDefault();
                return false;
            }

            return true;
        }


        function getCaretPos(obj) {
            obj.focus();

            if (obj.selectionStart) return obj.selectionStart;//Gecko
            else if (document.selection)//IE
            {
                var sel = document.selection.createRange();
                var clone = sel.duplicate();
                sel.collapse(true);
                clone.moveToElementText(obj);
                clone.setEndPoint('EndToEnd', sel);
                return clone.text.length;
            }

            return 0;
        }
        function bodyResize(height) {
            var winHeight = $("body").height();
            var footerHeight = $("#total").height();
            var IICheight = (winHeight < 600) ? 210 : winHeight - 389 - $("#toptable").height();
            var sc = (winHeight < 600) ? "visible" : "hidden";
            IICheight += height;
            $("body").css("overflow-y", sc);

//                var text = "winHeihgt = " + winHeight;
//                    text += "   IICheight = " + IICheight;
//                    text += "   total = " + footerHeight;
////        text += "   contentTDHeight = " + $("#contentTD").height();
////        text += "  bodyHeight = " + $("body").height();
////        text += "  htmlHeight = " + $("html").height();
//
//
//        $("#console").text(text);


            $("#variableHeightElement").css("height", IICheight + "px");

        }

    </script>
    <style type="text/css">


        #variableHeightElement {
        /*overflow-y: auto;*/
        /*height: 300px;*/
            width: 562px;

        }

        #mainTables {
            margin-top: 10px;
        }

        .dig {
            text-align: right; /*padding-right: 5px;*/
        /*margin:  10px;*/
        }

        .dt {
            border-collapse: collapse;
        }

        .dt tr, .dt  td {
        /*border: 1px solid black;*/

        }


    </style>




<%
    Invoice invoice = (Invoice) request.getAttribute("invoice");
    Booking booking = invoice.getBooking();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
%>

<div id="content">


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
            <a href="invoiceAction.do?method=viewBooking&id=<%=booking.getId()%>">
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
            <%=invoice.getEmployer()%>
        </td>


        <td></td>
        <td>
        </td>
        <td>


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
        <td><%=invoice.getConsumer()%>
        </td>
        <td></td>
        <td></td>
        <td>

        </td>

        <td></td>
        <td></td>

        <td></td>


        <td>Валюта</td>
        <td><%=invoice.getSupplier().getCurrency().getName() %>
        </td>


    </tr>
</table>

<div id="mainTables">
    <div id="headerTable">
        <TABLE class="invItemsTab" id="invItemsTab">
            <tr>
                <th class="width25">№</th>
                <th class="width90">Дата</th>
                <th class="width140">Назначение</th>
                <th class="width90">Сумма</th>
                <th class="width90">Курс</th>
                <th class="width90">%</th>
            </tr>
        </TABLE>
    </div>

    <div id="invItems">
        <div id="variableHeightElement">

            <TABLE class="invItemsTab">

                <%
                    BigDecimal p1 = new BigDecimal("0");
                    BigDecimal p2 = new BigDecimal("0");
                    NumberFormat df = NumberFormat.getInstance();
                    df.setMinimumFractionDigits(2);
                    df.setMaximumFractionDigits(2);

                    if ((invoice.getPayments() == null) || (invoice.getPayments().isEmpty())) {
                        for (int i = 0; i < 20; i++) {
                %>
                <tr>
                    <td class="width25">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                    <td class="width140">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                </tr>
                <%
                    }
                } else {


                    int k = 1;

                    for (Payment payment : invoice.getPayments()) {

                        p1 = p1.add(payment.getPaymentSum().multiply(payment.getExchangeRate()));
                        p2 = p2.add(payment.getPaymentSum());
                %>

                <tr>
                    <td class="width25"><%=k++%>
                    </td>
                    <td class="width90"><%=(new SimpleDateFormat("dd.MM.yyyy")).format(payment.getDate())%>
                    </td>
                    <td class="width140"><%=Payment.PAYMENT_PURPOSE_ALIAS[payment.getPurpose()]%>
                    </td>
                    <td class="dig width90"><%=df.format(payment.getPaymentSum())%> &nbsp;
                    </td>
                    <td class="dig width90"><%=df.format(payment.getExchangeRate())%>    &nbsp;
                    </td>
                    <td class="dig width90">
                        <%= payment.getPaymentSum().multiply(new BigDecimal("100")).divide(invoice.getTotal(), 2, BigDecimal.ROUND_HALF_UP)%>
                        &nbsp;
                    </td>
                </tr>

                <% }
                    if (invoice.getPayments().size() < 20) {
                        for (int i = 0; i < (20 - invoice.getPayments().size()); i++) {
                %>
                <tr>
                    <td class="width25">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                    <td class="width140">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                    <td class="width90">&nbsp;</td>
                </tr>
                <%
                            }
                        }
                    }
                %>
            </table>
        </div>
    </div>
    <div id="total">


        <TABLE class="dt">
            <tr>
                <td class="width25"></td>
                <td class="width230">Всего оплачено</td>

                <td class="width90 dig"><%=df.format(p2)%>
                </td>

                <td class="width90 dig"><%=(p2.compareTo(BigDecimal.ZERO) == 0) ? "0.00" : df.format(p1.divide(p2, 2))%>
                </td>
                <td class="width90 dig"></td>
            </tr>
            <tr>
                <td class="width25"></td>
                <td>Задолженность</td>


                <td class="dig"><%=df.format(invoice.getTotal().subtract(p2))%>
                </td>
                <td class="dig">
                </td>
                <td></td>
            </tr>

            <tr>
                <td class="width25"></td>
                <td>Сумма по счету</td>


                <td class="dig"><%=df.format(invoice.getTotal())%>
                </td>
                <td class="dig"><%=df.format(invoice.getExchangeRate())%>
                </td>
                <td></td>
            </tr>


        </table>

    </div>
</div>


</div>
<div id="downButtons">


    <input type="button" value="К счету"
           onclick="location.href='../invoices/view?invoice_id=<%=invoice.getId()%>'" class="butt">

    <%--<%--%>
        <%--Integer livel = (Integer) request.getSession().getAttribute("livel");--%>
        <%--if ((User.LIVEL_ECONOMIST.equals(livel) || User.LIVEL_ADMINISTRATOR.equals(livel)) && (!invoice.getCurrentState().equals(Invoice.STATE_ISP))) {%>--%>
    <%--todo  security--%>
    <input type="button" value="Добавить" onclick="javascript:void($('#addPayment').dialog('open'))" class="butt">
    <%--<%} %>--%>


</div>

<div id="addPayment" title="Добавить оплату">
    <Br>

    <form action="#" id="addPayment_form">
        <input type="hidden" name="invoice_id" value="<%=invoice.getId()%>">
        <table>
            <tr>
                <td><label for="date">Дата &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</label></td>
                <td><input type="text" name="date" id="date" readonly="true"
                           value="<%= (new SimpleDateFormat("dd.MM.yyyy")).format(new Date()) %>"
                           class="text ui-widget-content ui-corner-all"/></td>
            </tr>


            <tr>
                <td><label for="paymentSum">Сумма &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</label></td>
                <td><input type="text" name="paymentSum" id="paymentSum" class="text ui-widget-content ui-corner-all"
                           onclick="javascript:void($('#paymentSum').removeClass('ui-state-error'))"
                           onkeypress='digInput(event,this)'/>
                </td>
            </tr>


            <tr>
                <td><label for="exchangeRate">Курс на день платежа &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</label></td>
                <td><input type="text" name="exchangeRate" id="exchangeRate"
                           onclick="javascript:void($('#exchangeRate').removeClass('ui-state-error'))"
                           onkeypress='digInput(event,this)'
                           class="text ui-widget-content ui-corner-all"/></td>
            </tr>

            <tr>
                <td>Характер платежа</td>

                <td></td>
            </tr>
            <tr>
                <td style="text-align:right;"><input type="radio" name="purpose" value="0" checked id="purpose0"></td>

                <td><label for="purpose0">предоплата</label></td>
            </tr>
            <tr>
                <td style="text-align:right;"><input type="radio" name="purpose" value="1" id="purpose1"></td>
                <td><label for="purpose1">за запущенную</label></td>
            </tr>
            <tr>
                <td style="text-align:right;"><input type="radio" name="purpose" value="2" id="purpose2"></td>
                <td><label for="purpose2">за изготовленную</label></td>
            </tr>
            <tr>
                <td style="text-align:right;"><input type="radio" name="purpose" value="3" id="purpose3"></td>
                <td><label for="purpose3">за отгруженную</label></td>
            </tr>
            <tr>
                <td style="text-align:right;"><input type="radio" name="purpose" value="4" id="purpose4"></td>
                <td><label for="purpose3">ручная коррекция</label></td>
            </tr>


        </table>
    </form>

</div>


