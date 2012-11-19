<%@ page import="application.hibernate.Factory" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>

<%@ page import="application.data.ModelDescription.ModelDescription" %>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Редактор таблицы совместимости.</title>
    <link href="css/addPressureSensor.css" rel="stylesheet" type="text/css"/>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/addOp.js" type="text/javascript"></script>

    <script type="text/javascript">
    </script>

</head>
<body>
<form action="testAction.do" name="mainForm" onsubmit="return  validate();">
    <div id="parent">


        <input type="hidden" name="invoiceId" id="invoiceId" value="<%=request.getParameter("invoiceId")%>">
        <input type="hidden" name="invoiceItemId" id="invoiceItemId" value="<%=request.getParameter("invoiceItemId")%>">

        <div id="head">
            <label><input type="radio" checked name="typeTxt" value="0"> Сафiр М</label>
            <label><input type="radio" name="typeTxt" value="1"> Сафiр </label>
            <label><input type="radio" name="typeTxt" value="2"> СМХ </label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;
            Перед спецификацией &nbsp;&nbsp;
            <input type="text" name="beforeSpec" size="40">

        </div>

        <div id="model">
            <%
                GenericHibernateDAO<ModelDescription> dao = Factory.getModelDescriptionDAO();

                String[] ar1 = {"30", "31", "40", "41", "50", "51", "60", "61", "70", "71"};
                String[] ar2 = {"30", "31", "32", "33"};
                out.println("<table class=\"models\"  cellspacing=\"0\" > <tr> <td  class=\"silver_rb\" >  &nbsp;</td>");
                for (String it1 : ar1) {
                    String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_rb" : "silver_b";
                    out.println(" <td class=\"" + style + "\" >.." + it1 + "</td>");
                }
                out.println("</tr>");
                for (String it2 : ar2) {
                    out.println("<tr>");
                    out.println(" <td class=\"silver_r\">" + it2 + "..</td>");
                    for (String it1 : ar1) {

                        String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_r" : "silver";

                        ModelDescription result = dao.findById(new Long(it2 + it1));
                        if (result != null) {
                            out.println(" <td  class=\"" + style + " \" ><input type=\"radio\" onclick=\"test();\" name=\"model\" value=\"" + it2 + it1 + "\"/></td>");
                        } else {
                            out.println(" <td  class=\"" + style + "\" >&nbsp;</td>");
                        }

                    }
                    out.println("</tr>");
                }

            %>
            </table>
        </div>

        <table>
            <tr>
                <td class="topAlig">
                    <div id="ispolnenie"></div>
                </td>
                <td class="topAlig">
                    <div id="materials"></div>
                </td>
                <td class="topAlig">
                    <div id="klimat" style="visibility:hidden;">
                        <fieldset>
                            <legend>Климатика</legend>
                            <input type="radio" checked name="klim" value="0"> &nbsp;УХЛ3.1* <br>
                            <input type="radio" name="klim" value="1"> &nbsp;УХЛ3.1* (+5..+50) <br>
                            <input type="radio" name="klim" value="2"> &nbsp;УХЛ3.1* (+5..+80) <br><br>
                            <input type="radio" name="klim" value="3"> &nbsp;У2*<br>
                            <input type="radio" name="klim" value="4"> &nbsp;У2* (-30..+50) <br>
                            <input type="radio" name="klim" value="5"> &nbsp;У2* (-40..+50) <br><br>
                            <input type="radio" name="klim" value="6"> &nbsp;Т3**<br>
                            <input type="radio" name="klim" value="7"> &nbsp;Т3** (-5..+80) <br>
                        </fieldset>


                    </div>
                </td>
                <td class="topAlig">
                    <div id="errors"></div>
                </td>
                <td class="topAlig">
                    <div id="ed_izm" style="visibility:hidden;">
                        <fieldset>
                            <legend>ед. измер.</legend>
                            <input type="radio" name="ed_izm" value="0" onclick="changeEdIzm();"> &nbsp;кПа<br>
                            <input type="radio" name="ed_izm" value="1" onclick="changeEdIzm();"> &nbsp;МПа<br>
                            <input type="radio" name="ed_izm" value="2" onclick="changeEdIzm();"> &nbsp;кгс/см&sup2;<br>
                            <input type="radio" name="ed_izm" value="3" onclick="changeEdIzm();"> &nbsp;кгс/м&sup2;<br>
                            <input type="radio" name="ed_izm" value="4" onclick="changeEdIzm();"> &nbsp;kPa <br>
                            <input type="radio" name="ed_izm" value="5" onclick="changeEdIzm();"> &nbsp;MPa <br>
                            <input type="radio" name="ed_izm" value="6" onclick="changeEdIzm();"> &nbsp;kgf/сm&sup2;
                            <br>
                            <input type="radio" name="ed_izm" value="7" onclick="changeEdIzm();">&nbsp;kgf/m&sup2; <br>
                            <input type="radio" name="ed_izm" value="8" onclick="changeEdIzm();">&nbsp;bar <br>
                            <input type="radio" name="ed_izm" value="9" onclick="changeEdIzm();">&nbsp;mbar <br>
                        </fieldset>
                    </div>
                </td>
                <td class="topAlig">
                    <div id="stat"></div>
                </td>
                <td class="topAlig">
                    <div id="otput"></div>
                </td>
                <td class="topAlig">
                    <div id="du"></div>
                </td>
            </tr>

        </table>


        <table>
            <tr>
                <td>
                    <div id="limits">
                        <table class="lim" cellspacing="0">
                            <tr>
                                <td class="width60" colspan="3">
                                    Пределы измерений
                                </td>
                            </tr>
                            <tr>
                                <td class="width60">нижн</td>
                                <td class="width60" colspan="2">верх</td>
                            </tr>
                            <tr>
                                <td class="width60"><input type="text" name="low" id="low" size="8"></td>
                                <td class="width60"><input type="text" name="hi" id="hi" size="8"></td>
                                <td class="width60"><select name="hid" id="hid" style="width:80px;">


                                </select></select>

                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
                <td>
                    <div id="options">
                        <table class="opt" cellspacing="0">
                            <tr>
                                <td class="width60" colspan="4">
                                    Опции
                                </td>
                            </tr>
                            <tr>
                                <%--<td class="col3">КМЧ</td>--%>
                                <td class="col3">-И</td>
                                <td class="col3">-ПИ</td>
                                <%--<td class="col3">-П</td>--%>
                                <td class="col3">-ВМ</td>
                                <td class="col3">-Хим</td>
                                <%--<td class="col3">-Р</td>--%>
                            </tr>
                            <tr>
                                <%--<td class="col3"><select name="kmch" style="width:60px;"></select></td>--%>
                                <td class="col3"><input type="checkbox" name="i" id="i"></td>
                                <td class="col3"><input type="checkbox" name="pi" id="pi"></td>
                                <%--<td class="col3"><input type="checkbox" name="p"></td>--%>
                                <td class="col3"><input type="checkbox" name="vm" id="vm"></td>
                                <td class="col3"><input type="checkbox" name="him" id="him"></td>
                                <%--<td class="col3"><input type="checkbox" name="r"></td>--%>


                            </tr>
                        </table>
                    </div>
                </td>
                <td>
                    <div id="right">
                        <label> <input type="checkbox" name="tu" id="tu"> &nbsp; ТУ </label> <br>
                        После спецификации: <br>
                        <input type="text" name="afterSpec" id="afterSpec" size="40">

                    </div>
                </td>
            </tr>
        </table>


        <input type="hidden" value="addPressureSensor" name="method">
        <input type="hidden" value="on" name="r">


    </div>

    <div id="downButtons">
        <input type="submit" value="Отправить">
        <input type="button" value="Отменить" onclick="cancel();">

    </div>
</form>

</body>
</html>



