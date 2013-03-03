<%@ page import="ua.com.manometer.model.price.ProductionPrice" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


    <script type="text/javascript">
        $(function() {
            $("#tabs").tabs({
		});

        });
        function cancel() {
            var str = "../invoices/view?invoice_id=" + $('#invoice_id').val();
            location.replace(str);
        }
        function checkForm() {
            var name = $("#name");
            var cost = $("#cost");
            var price = $("#price");
            var bValid = true;
            name.removeClass('ui-state-error');
            cost.removeClass('ui-state-error');
            price.removeClass('ui-state-error');

            bValid = bValid && checkLength(name, 5, 500);
            bValid = bValid && checkRegexp(cost, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
            bValid = bValid && checkRegexp(cost, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
            bValid = bValid && checkRegexp(price, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
            bValid = bValid && checkRegexp(price, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");

            return bValid;

        }
        function updateTips(t) {
            $(".validateTips").text(t).addClass('ui-state-highlight');
            setTimeout(function() {
                tips.removeClass('ui-state-highlight', 1500);
            }, 500);
        }

        function checkLength(o, min, max) {

            if (o.val().length > max || o.val().length < min) {
                o.addClass('ui-state-error');
                updateTips("Длина поля должна быть больше " + min + " и меньше " + max + " символов.");
                return false;
            } else {
                return true;
            }

        }

        function checkRegexp(o, regexp, n) {

            if (!( regexp.test(o.val()) )) {
                o.addClass('ui-state-error');
                updateTips(n);
                return false;
            } else {
                return true;
            }

        }

    </script>
    <style type="text/css">
        label, input {
            display: block;
        }

        table.invItemsTab {
            border: 1px solid #999999; /*width:100%;*/
            text-align: center;
            border-collapse: collapse;

        }

        table.invItemsTab    th {
            background-color: lightgray;
        }

        table.invItemsTab   td, th {
        /*border: 1px solid #800;  Параметры границы*/
            border-left: 1px solid #999999;
            border-right: 1px solid #999999; /*border-top: 2px solid #555;*/
            border-bottom: 2px solid #555; /*padding: 4px; *//* Поля в ячейках */
        }

        .toptable tr, .toptable  td {
        /*border: 1px solid black;*/

        }

        .middle {
            width: 70px;
        }

        .short {
            width: 20px;
        }

        .long {
            width: 550px;
        }

        .downButtons {
        /*position: absolute; *//*margin-top: 5px; *//**//*margin-right: auto;*/
        /*width: 400px; *//*padding: 10px;*/
        /*height: 40px; *//*background: #ccc;*/
        /*top: 630px;*/
        /*left: 200px;*/
        /*border: none;*/
            /*background-color: #7fffd4;*/
        }

        #container {
            height: 509px;

        }

        #tableDiv {

        /*position: absolute;*/
        /*width: 668px;*/
        /*top: 25px;*/
        /*left: 200px;*/
        /*background-color: #7fff00;*/
        }

        #tabs {
            width: 800px;
            margin-left: auto;
            margin-right: auto;

        }

        #bodyDiv {
        /*//  position: relative;*/
        /*margin: 0px auto;*/
        /*background-color:#ff8c00;*/
        }

        #result {
            overflow-y: auto;
            height: 500px;
        }

        .butt {
            display: inline;
            width: 100px;

        }
    </style>



<div id="bodyDiv">
     <% String[] arr = {"датчик давления специальный", "блок питания", "измерительный блок", "диаф. кам.", "проч продукция", "вычислитель", "продукцию сторонних производителей"};%>
        <h2 align="center">Добавить <%=arr[new Integer(request.getParameter("type"))-3]%></h2>

    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">Из списка</a></li>
            <li><a href="#tabs-2">Новая позиция</a></li>
        </ul>
        <div id="tabs-1" class="tabdiv">
            <form action="add_list" method="post">
                <input type="hidden" name="invoice_id" id="invoice_id" value="<%=request.getParameter("invoice_id")%>">
                <input type="hidden" name="type" value="<%=request.getParameter("type")%>">

                <div id="tableDiv">
                    <%
                        List<ProductionPrice> result = (List<ProductionPrice>) request.getAttribute("list");
                        if (result.size() == 0) {
                    %>
                    Ни одной записи не найдено.
                    <%
                    } else { %>
                    <table class="invItemsTab">
                        <tr>
                            <th class="short"></th>
                            <th class="long">Название</th>
                            <th class="middle">Цена</th>
                        </tr>
                    </table>
                    <div id="result">
                        <table class="invItemsTab">
                            <%
                                ProductionPrice item;
                                for (int i = 0; i < result.size(); i++) {
                                    item = result.get(i);
                            %>
                            <tr>
                                <td class="short"><input type="radio" name="production_id"
                                                         value="<%=item.getId()%>" <%=(i==0)?"checked":""%>  ></td>
                                <td class="long"><%=item.getName()%>
                                </td>
                                <td class="middle"><%=item.getPrice()%>
                                </td>
                            </tr>
                            <%}%>
                        </table>
                    </div>
                    <%}%>
                </div>
                <input type="hidden" value="addList" name="method">

                <div class="downButtons">
                    <input type="submit" value="Отправить" class="butt">
                    <input type="button" value="Отменить" onclick="cancel();" class="butt">
                </div>
            </form>
        </div>
        <div id="tabs-2" class="tabdiv">
            <form action="add_list" method="post" onsubmit="return checkForm();">
                <div id="container">
                    <p class="validateTips">Все поля должны быть заполненны.</p>
                    <fieldset>
                        <input type="hidden" name="invoice_id" value="<%=request.getParameter("invoice_id")%>">
                        <input type="hidden" name="type" value="<%=request.getParameter("type")%>">

                        <label for="name">Название</label>
                        <textarea rows="4" cols="50" name="name" id="name"></textarea><br><br>
                        <label for="cost">Себестоимость</label>
                        <input type="text" name="cost" id="cost" class="text ui-widget-content ui-corner-all"/>
                        <label for="price">Цена</label>
                        <input type="text" name="price" id="price" class="text ui-widget-content ui-corner-all"/>
                        <label for="addToList">Добавлять в список</label>
                        <input type="checkbox" name="addToList" id="addToList"
                               class="text ui-widget-content ui-corner-all"/>
                    </fieldset>
                    <input type="hidden" value="addListExt" name="method">
                </div>
                <div class="downButtons">
                    <input type="submit" value="Отправить" class="butt">
                    <input type="button" value="Отменить" onclick="cancel();" class="butt">
                </div>
            </form>

        </div>

    </div>


</div>
</body>
</html>