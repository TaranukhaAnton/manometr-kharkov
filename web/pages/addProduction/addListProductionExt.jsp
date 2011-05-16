<%@ page import="application.hibernate.Factory" %>
<%@ page import="java.util.List" %>
<%@ page import="application.data.price.ProductionPrice" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title>
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

    <script type="text/javascript">
        $(function() {
            $("#tabs").tabs();
        });
        function cancel() {
            var str = "invoiceAction.do?method=viewInvoice&id=" + $('#invoiceId').val();
            location.replace(str);
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
            background-color: #7fffd4;
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


</head>
<body>

<div id="bodyDiv">


    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">Из списка</a></li>
            <li><a href="#tabs-2">Новая позиция</a></li>
        </ul>
        <div id="tabs-1" class="tabdiv">
            <form action="testAction.do">
                <input type="hidden" name="invoiceId" id="invoiceId" value="<%=request.getParameter("invoiceId")%>">
                <input type="hidden" name="type" value="<%=request.getParameter("type")%>">
                <% String[] arr = {"датчик давления специальный", "блок питания", "измерительный блок", "диаф. кам.", "проч продукция", "вычислитель", "продукцию сторонних производителей"};
                %>
                <%--<h2 align="center">Добавить <%=arr[new Integer(request.getParameter("type"))-3]%> </h2>--%>
                <div id="tableDiv">
                    <%
                        GenericHibernateDAO<ProductionPrice> dao = Factory.getProductionDAO();

                        List<ProductionPrice> result = dao.findListByParameter("type", new Integer(request.getParameter("type")));
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
                                <td class="short"><input type="radio" name="productionId"
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
            <form action="testAction.do">
                <div id="container">
                    <p class="validateTips">Все поля должны быть заполненны.</p>
                    <fieldset>
                        <input type="hidden" name="invoiceId"  value="<%=request.getParameter("invoiceId")%>">
                        <input type="hidden" name="type" value="<%=request.getParameter("type")%>">
                         <input type="hidden" name="type" value="<%=request.getParameter("type")%>">
                        <label for="name">Название</label>
                        <textarea rows="4" cols="50" name="name" id="name"></textarea><br><br>
                        <label for="cost">Себестоимость</label>
                        <input type="text" name="cost" id="cost" class="text ui-widget-content ui-corner-all"/>
                        <label for="price">Цена</label>
                        <input type="text" name="price" id="price" class="text ui-widget-content ui-corner-all"/>
                        <label for="addToList">Добавлять в список</label>
                        <input type="checkbox" name="addToList" id="addToList" class="text ui-widget-content ui-corner-all"/>
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