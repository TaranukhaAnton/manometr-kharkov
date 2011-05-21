<%@ page import="application.hibernate.Factory" %>
<%@ page import="java.util.List" %>
<%@ page import="application.data.price.ProductionPrice" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title>
    <script src="js/jquery.js" type="text/javascript"></script>

    <script type="text/javascript">

        function cancel()
        {
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

        #downButtons {
            position: absolute; /*margin-top: 5px; *//*margin-right: auto;*/
            width: 400px; /*padding: 10px;*/
            height: 40px; /*background: #ccc;*/
            top: 630px;
            left: 200px;
            border: none;
        }

     

        #tableDiv {

            position: absolute;
            width: 668px;
            top: 25px;
            left: 200px;
        }

        #bodyDiv {

            position: relative;

        }

        #result {
            overflow-y: auto;
            height: 500px;

        }

           .butt {
            display: inline;
            width:100px;

        }
    </style>


</head>
<body>

<div id="bodyDiv">
    <form action="testAction.do">
        <input type="hidden" name="invoiceId" id="invoiceId" value="<%=request.getParameter("invoiceId")%>">
        <input type="hidden" name="type" id="type" value="<%=request.getParameter("type")%>">
        <% String[] arr = {"датчик давления специальный", "блок питания", "измерительный блок", "диаф. кам.", "проч продукция", "вычислитель", "продукцию сторонних производителей"};
        %>
        <h2 align="center">Добавить <%=arr[new Integer(request.getParameter("type"))-3]%></h2>
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
        <div id="downButtons">
            <input type="submit" value="Отправить"  class="butt">
            <input type="button" value="Отменить" onclick="cancel();" class="butt">
        </div>
    </form>
</div>
</body>
</html>