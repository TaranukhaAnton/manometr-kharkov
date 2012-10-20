<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="js/ui/jquery-1.4.1.js"></script>
    <style type="text/css">
        div#menu ul {
            padding: 0;
        }

        div#menu ul li {
            display: block;
            border-bottom: 1px solid #211f1b;
            margin-bottom: 5px;
            padding: 0;
        }

        div#menu ul li a {
            color: #211F1B;
            display: block;
            font-family: Verdana;
            font-size: 15px;
            font-weight: bold;
            padding: 5px 0;
             padding-left: 5px;
        }

        div#menu ul li a:hover,
        div#menu ul li a.active,
        div#menu ul li a:active {
            color: #005693;
            text-decoration: none;
            outline: none;
        }

        div#menu ul ul {
            display: none; /* собственно, самое важное :) */
        }

        div#menu ul ul li {
            border: none;
            margin: 0;
        }

        div#menu ul ul li a {
            font-size: 11px;
            padding-left: 20px;
        }

        div#menu ul ul li a:hover,
        div#menu ul ul li.active-trail a {
            background: #b7b7b7;
            color: #fff;
        }

        div#menu ul ul li.active-trail ul li a {
            background: #fff;
            color: #000;
        }


    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $('div#menu>ul>li>a').click(function() {
                if ($(this).parent().find('ul').length) {
                    $(this).parent().find('ul').slideToggle(200);
                    return false;
                }
            });
        });
    </script>


</head>
<body>
<div id="menu">
    <ul id="sidebarmenu1">
        <logic:present name="level" scope="session">
            <li><a href="invoiceAction.do?method=viewInvoices">Счета и к.п.</a></li>
            <li><a href="invoiceAction.do?method=viewBookings">Заказ-наряды</a></li>


            <logic:equal value="4" name="level">
                <li><a href="supplierAction.do?method=viewSuppliers">Поставщики</a></li>


                <LI><A href="#">Прайс</A>
                    <UL >
                        <LI><A href="priceAction.do?method=redactMatrixPriceForward">Матричный</A></LI>
                        <LI><A href="priceAction.do?method=redactListPriceForward">Список</A></LI>
                        <LI><A href="priceAction.do?method=redactOptionPriceForward">Опции</A></LI>
                    </UL>
                </LI>

                <LI><A href="#">Таб. совм.</A>
                    <UL >
                        <LI><A href="testAction.do?method=redactCoForward">ЦО</A></LI>
                        <LI><A href="testAction.do?method=redactOpForward">ОП</A></LI>
                        <LI><A href="testAction.do?method=redactAoForward">АО</A></LI>
                    </UL>
                </LI>
                <LI><A href="#">Статистика</A>
                    <UL >
                        <LI><A href="statisticAction.do?method=viewInvoicesWithDebts">Задолженности по оплате</A></LI>

                    </UL>
                </LI>
                <%--<li><a href="priceAction.do?method=redactMatrixPriceForward">Ред матр. прайс.</a></li>--%>
                <%--<li><a href="priceAction.do?method=redactListPriceForward">Ред сп. прайс.</a>--%>
                <%--<li><a href="priceAction.do?method=redactOptionPriceForward">Ред оп. прайс.</a></li>--%>


                <%--<li><a href="testAction.do?method=redactCoForward">Таб. цо совм.</a></li>--%>
                <%--<li><a href="testAction.do?method=redactOpForward">Таб. oп совм.</a></li>--%>
                <%--<li><a href="testAction.do?method=redactAoForward">Таб. аo совм.</a></li>--%>


            </logic:equal>
        </logic:present>
        <li><a href="CustomerProcess.do?method=getCustomers">Предприятия</a></li>
        <li><a href="ContactShow.do?method=getContacts&show=all">Конт. лица</a></li>


        <logic:present name="level" scope="session">
            <logic:equal value="4" name="level">
                <li><a href="UserProcess.do?method=getUsers">Пользователи</a></li>
            </logic:equal>
        </logic:present>
        <li><a href="userLogin.do?method=logof">Выход</a></li>
    </ul>
</div>

</body>
</html>