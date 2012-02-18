<%@page contentType="text/html" pageEncoding="UTF-8" %>



<div id="menu">
    <ul id="sidebarmenu1">
        <li><a href="invoiceAction.do?method=viewInvoices">Счета и к.п.</a></li>
        <li><a href="invoiceAction.do?method=viewBookings">Заказ-наряды</a></li>


        <li><a href="supplierAction.do?method=viewSuppliers">Поставщики</a></li>


        <LI><A href="#">Прайс</A>
            <UL>
                <LI><A href="priceAction.do?method=redactMatrixPriceForward">Матричный</A></LI>
                <LI><A href="priceAction.do?method=redactListPriceForward">Список</A></LI>
                <LI><A href="priceAction.do?method=redactOptionPriceForward">Опции</A></LI>
            </UL>
        </LI>

        <LI><A href="#">Таб. совм.</A>
            <UL>
                <LI><A href="testAction.do?method=redactCoForward">ЦО</A></LI>
                <LI><A href="testAction.do?method=redactOpForward">ОП</A></LI>
                <LI><A href="testAction.do?method=redactAoForward">АО</A></LI>
            </UL>
        </LI>
        <LI><A href="#">Статистика</A>
            <UL>
                <LI><A href="statisticAction.do?method=viewPaymentArrears">Задолженности по оплате</A></LI>

            </UL>
        </LI>
        <%--<li><a href="priceAction.do?method=redactMatrixPriceForward">Ред матр. прайс.</a></li>--%>
        <%--<li><a href="priceAction.do?method=redactListPriceForward">Ред сп. прайс.</a>--%>
        <%--<li><a href="priceAction.do?method=redactOptionPriceForward">Ред оп. прайс.</a></li>--%>


        <%--<li><a href="testAction.do?method=redactCoForward">Таб. цо совм.</a></li>--%>
        <%--<li><a href="testAction.do?method=redactOpForward">Таб. oп совм.</a></li>--%>
        <%--<li><a href="testAction.do?method=redactAoForward">Таб. аo совм.</a></li>--%>


        <li><a href="CustomerProcess.do?method=getCustomers">Предприятия</a></li>
        <li><a href="ContactShow.do?method=getContacts&show=all">Конт. лица</a></li>


        <li><a href="../users/">Пользователи</a></li>
        <li><a href="userLogin.do?method=logof">Выход</a></li>
    </ul>
</div>

