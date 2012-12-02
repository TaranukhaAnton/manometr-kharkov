<%@ page import="application.data.price.OptionsPrice" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="org.hibernate.criterion.SimpleExpression" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 03.02.2010
  Time: 22:49:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title>


    <script src="js/cookie.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/ui/jquery-1.4.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery.bgiframe-2.1.1.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.core.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.mouse.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.button.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.draggable.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.position.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.resizable.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.dialog.js"></script>
    <script type="text/javascript" src="js/ui/jquery.ui.tabs.js"></script>
    <script type="text/javascript" src="js/ui/jquery.effects.core.js"></script>
    <%--<link type="text/css" href=" css/tst/ui.all.css" rel="stylesheet"/>--%>
    <link type="text/css" href=" css/tst/jquery.ui.all.css" rel="stylesheet"/>
    <link type="text/css" href=" css/tabs.css" rel="stylesheet"/>
    <script src="js/redactOptions.js" type="text/javascript"></script>

    <style type="text/css">
        label, input {
            display: block;
        }

        #dialog-form {
            font-size: 62.5%;
        }

        table.options {
            border-collapse: collapse; /*border: 1px dashed black;*/
            margin: 0 auto;
        }

        table.options th {
            width: 80px;
            background-color: #aaa;
            border: 1px solid #000;
        }

        table.options td {
            background-color: #eee;
            border: 1px solid #000;

            text-align: right;
        }

        table.options th.first {
            width: 60px;
            background-color: #aaa;
        }

        div.tabs div {
            overflow-y: scroll; /* Добавляем полосы прокрутки */
            height: 600px;
        }

        div.tabs {
        /*background: #333;*/
        /*padding: 20px;*/
            height: 651px;
        }

        /*#co, #op, #ao {*/
        /*overflow-y: scroll; *//* Добавляем полосы прокрутки */

        /*height: 600px; *//*padding: 5px; *//* Поля вокруг текста */
        /*border: solid 1px black; *//* Параметры рамки */
        /*}*/

        /*div.tabs {*/
        /*background: #333;*/
        /*padding: 20px;*/
        /*height: 651px;*/
        /*}*/

        /*ul.tabNavigation {*/
        /*list-style: none;*/
        /*margin: 0;*/
        /*padding: 0;*/
        /*}*/

        /*ul.tabNavigation li {*/
        /*display: inline;*/
        /*}*/

        /*ul.tabNavigation li a {*/
        /*-moz-border-radius-topleft: 4px *//*{cornerRadius}*//*;*/
        /*-webkit-border-top-left-radius: 4px *//*{cornerRadius}*//*;*/
        /*border-top-left-radius: 4px *//*{cornerRadius}*//*;*/
        /*-moz-border-radius-topright: 4px *//*{cornerRadius}*//*;*/
        /*-webkit-border-top-right-radius: 4px *//*{cornerRadius}*//*;*/
        /*border-top-right-radius: 4px *//*{cornerRadius}*//*;*/
        /*padding: 3px 9px;*/
        /*background-color: #666;*/
        /*color: #000;*/
        /*text-decoration: none;*/
        /*}*/

        /*ul.tabNavigation li a.selected,*/
        /*ul.tabNavigation li a.selected:hover {*/
        /*background: #FFF;*/
        /*color: #000;*/
        /*}*/

        /*ul.tabNavigation li a:hover {*/
        /*background: #ccc;*/
        /*color: #000;*/
        /*}*/

        /*ul.tabNavigation li a:focus {*/
        /*outline: 0;*/
        /*}*/

        /*div.tabs div {*/
        /*overflow-y: scroll; *//* Добавляем полосы прокрутки */

        /*visibility:hidden; */
        /*height: 600px;*/
        /*padding: 5px;*/
        /*margin-top: 3px;*/
        /*border: 1px solid #FFF;*/
        /*background: #FFF;*/
        /*}*/

        /*div.tabs div h2 {*/
        /*margin-top: 0;*/
        /*}*/


    </style>


</head>
<body>
<div class="tabs">
    <!-- Это сами вкладки -->
    <ul class="tabNavigation">
        <li><a class="" href="#co">Цифровые</a></li>
        <li><a class="" href="#ao">Аналоговые</a></li>
        <li><a class="" href="#op">Однопредельные</a></li>
    </ul>
    <div id="co">
        <table class="options">
            <tr>
                <th class="first" rowspan="2"></th>
                <th colspan="2">общ.</th>
                <th colspan="2">Вн.</th>
                <th colspan="2">Ex</th>
                <th colspan="2">AC</th>
                <th colspan="2">K</th>
            </tr>
            <tr>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>

            </tr>
            <%
                //                String[] array = {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H22", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"};
                String[] array = {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"};
                String[] lab = {"42", "24", "&#8730 42", "05", "50", "&#8730 05", "ДУ 50", "ДУ 80", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "-И", "-ПИ", "ВМ", "-Хим", "-P", "-PK", "-PD", "госповерка"};
                GenericHibernateDAO<OptionsPrice> ddd = Factory.getOptionsPriceDAO();

                for (int c = 0; c < array.length; c++) {
                    out.println("<tr><th>" + lab[c] + "</th>");
                    for (int isp = 0; isp < 5; isp++) {
//                        OptionsPrice p = new OptionsPrice();
//                        p.setType(0);
//                        p.setIsp(isp);
//                        p.setParam(array[c]);
                        //                        List<OptionsPrice> res = ddd.findByExample(p, new LinkedList());

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("type", new Integer(0));
                        map.put("isp", isp);
                        map.put("param", array[c]);

                        List<OptionsPrice> res = ddd.findByExample(map);
                        if (res.size() != 0) { %>
            <td>
                <a id="c0-<%=isp%>-<%=array[c]%>"
                   href="javascript:void(view(0,<%=res.get(0).getIsp()%>,'<%=lab[c]%>','<%=array[c]%>',<%=res.get(0).getPrice()%>,<%=res.get(0).getCost()%>))"><%=res.get(0).getCost()%>
                </a>
            </td>
            <td>
                <a id="p0-<%=isp%>-<%=array[c]%>"
                   href="javascript:void(view(0,<%=res.get(0).getIsp()%>,'<%=lab[c]%>','<%=array[c]%>',<%=res.get(0).getPrice()%>,<%=res.get(0).getCost()%>))"><%=res.get(0).getPrice()%>
                </a>
            </td>
            <%
                        }
                    }
                    out.println("</tr>");
                }
            %>
        </table>
    </div>
    <div id="ao">
        <table class="options">
            <tr>
                <th class="first" rowspan="2"></th>
                <th colspan="2">общ.</th>
                <th colspan="2">Вн.</th>
                <th colspan="2">Ex</th>
                <th colspan="2">AC</th>
                <th colspan="2">K</th>
            </tr>
            <tr>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>

            </tr>
            <%
                //   String[] array = {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H22", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"};
                //   String[] lab = {"05", "&#8730 05", "50", "42", "&#8730 42", "24", "ДУ 50", "ДУ 80", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H22", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "-И", "-ПИ", "ВМ", "-Хим", "-P", "-PK", "-PD", "госповерка"};
                //  GenericHibernateDAO<OptionsPrice> ddd = Factory.getSINGLETON().getOptionsPriceDAO();

                for (int c = 0; c < array.length; c++) {
                    out.println("<tr><th>" + lab[c] + "</th>");
                    for (int isp = 0; isp < 5; isp++) {
//                        OptionsPrice p = new OptionsPrice();
//                        p.setType(1);
//                        p.setIsp(isp);
//                        p.setParam(array[c]);
                        //  List<OptionsPrice> res = ddd.findByExample(p, new LinkedList());

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("type", new Integer(1));
                        map.put("isp", isp);
                        map.put("param", array[c]);

                        List<OptionsPrice> res = ddd.findByExample(map);

                        if (res.size() != 0) { %>
            <td>
                <a id="c1-<%=isp%>-<%=array[c]%>"
                   href="javascript:void(view(1,<%=res.get(0).getIsp()%>,'<%=lab[c]%>','<%=array[c]%>',<%=res.get(0).getPrice()%>,<%=res.get(0).getCost()%>))"><%=res.get(0).getCost()%>
                </a>
            </td>
            <td>
                <a id="p1-<%=isp%>-<%=array[c]%>"
                   href="javascript:void(view(1,<%=res.get(0).getIsp()%>,'<%=lab[c]%>','<%=array[c]%>',<%=res.get(0).getPrice()%>,<%=res.get(0).getCost()%>))"><%=res.get(0).getPrice()%>
                </a>
            </td>
            <%
                        }
                    }
                    out.println("</tr>");
                }
            %>
        </table>
    </div>
    <div id="op">
        <table class="options">
            <tr>
                <th class="first" rowspan="2"></th>
                <th colspan="2">общ.</th>
                <th colspan="2">Вн.</th>
                <th colspan="2">Ex</th>
                <th colspan="2">AC</th>
                <th colspan="2">K</th>
            </tr>
            <tr>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>
                <th>c/c</th>
                <th>цена</th>

            </tr>
            <%
                String[] array1 = {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"};
                String[] lab1 = {"42", "24" , "&#8730 42" ,"05","50", "&#8730 05" ,  "ДУ 50", "ДУ 80", "-И", "-ПИ", "ВМ", "-Хим", "-P", "-PK", "-PD", "госповерка"};
                //  GenericHibernateDAO<OptionsPrice> ddd = Factory.getSINGLETON().getOptionsPriceDAO();

                for (int c = 0; c < array1.length; c++) {
                    out.println("<tr><th>" + lab1[c] + "</th>");
                    for (int isp = 0; isp < 5; isp++) {
//                        OptionsPrice p = new OptionsPrice();
//                        p.setType(2);
//                        p.setIsp(isp);
//                        p.setParam(array1[c]);
//                        List<OptionsPrice> res = ddd.findByExample(p, new LinkedList());
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("type", new Integer(2));
                        map.put("isp", isp);
                        map.put("param", array1[c]);

                        List<OptionsPrice> res = ddd.findByExample(map);


                        if (res.size() != 0) { %>
            <td>
                <a id="c2-<%=isp%>-<%=array1[c]%>"
                   href="javascript:void(view(2,<%=res.get(0).getIsp()%>,'<%=lab1[c]%>','<%=array1[c]%>',<%=res.get(0).getPrice()%>,<%=res.get(0).getCost()%>))"><%=res.get(0).getCost()%>
                </a>
            </td>
            <td>
                <a id="p2-<%=isp%>-<%=array1[c]%>"
                   href="javascript:void(view(2,<%=res.get(0).getIsp()%>,'<%=lab1[c]%>','<%=array1[c]%>',<%=res.get(0).getPrice()%>,<%=res.get(0).getCost()%>))"><%=res.get(0).getPrice()%>
                </a>
            </td>
            <%
                        }
                    }
                    out.println("</tr>");
                }
            %>
        </table>


    </div>
</div>

<div id="dialog-form" title="Радактировать с.с. и цену опций">
    <p class="validateTips">Все поля должны быть заполненны.</p> <br>

    <p id="TypeLab"/>

    <p id="IspLab"/>

    <p id="ParamLab"/>


    <fieldset>
        <input type="hidden" name="type" id="type"/>
        <input type="hidden" name="isp" id="isp"/>
        <input type="hidden" name="param" id="param"/>

        <label id="costl" for="cost">Себестоимость</label>
        <input type="text" name="cost" id="cost" class="text ui-widget-content ui-corner-all"/>
        <label for="price">Цена</label>
        <input type="text" name="price" id="price" class="text ui-widget-content ui-corner-all"/>
    </fieldset>

</div>

</body>
</html>