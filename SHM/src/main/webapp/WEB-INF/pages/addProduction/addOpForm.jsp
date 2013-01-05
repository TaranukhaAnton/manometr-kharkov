<%request.setCharacterEncoding("UTF-8");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<link href="../css/addPressureSensor.css" rel="stylesheet" type="text/css"/>
<script src="../js/local/add/addCo.js" type="text/javascript"></script>

<form action="add_pressure_sensor" name="mainForm" onsubmit="return  validate();">
    <div id="parent">

        <input type="hidden" name="invoice_id" id="invoice_id" value="<%=request.getParameter("invoice_id")%>">
        <% if(request.getParameter("invoice_item_id")!=null){%>
        <input type="hidden" name="invoice_item_id" id="invoice_item_id" value="<%=request.getParameter("invoice_item_id")%>">
        <% }%>

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
            <table class="models" cellspacing="0">
                <tr>
                    <td class="silver_rb">  &nbsp;</td>
                    <td class="silver_b">..30</td>
                    <td class="silver_b">..31</td>
                    <td class="silver_b">..40</td>
                    <td class="silver_b">..41</td>
                    <td class="silver_b">..50</td>
                    <td class="silver_b">..51</td>
                    <td class="silver_b">..60</td>
                    <td class="silver_b">..61</td>
                    <td class="silver_b">..70</td>
                    <td class="silver_b">..71</td>
                </tr>
                <tr>
                    <td class="silver_r">30..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3030"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3040"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3041"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3050"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3051"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>
                <tr>
                    <td class="silver_r">31..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3130"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3131"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3140"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3141"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3150"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3151"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3160"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3161"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3170"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3171"/></td>
                </tr>
                <tr>
                    <td class="silver_r">32..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3230"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3231"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>
                <tr>
                    <td class="silver_r">33..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3330"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3331"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3340"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3341"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3350"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="3351"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>

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
                            <input type="radio" name="ed_izm" value="6" onclick="changeEdIzm();"> &nbsp;kgf/sm&sup2;
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
                    <div id="output"></div>
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



