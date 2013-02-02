
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
            <input type="text" name="beforeSpec" id="beforeSpec" size="40">

        </div>

        <div id="model">
            <table class="models" cellspacing="0">
                <tr>
                    <td class="silver_rb">  &nbsp;</td>
                    <td class="silver_b">..01</td>
                    <td class="silver_b">..10</td>
                    <td class="silver_rb">..15</td>
                    <td class="silver_b">..20</td>
                    <td class="silver_rb">..24</td>
                    <td class="silver_b">..30</td>
                    <td class="silver_b">..34</td>
                    <td class="silver_b">..40</td>
                    <td class="silver_b">..41</td>
                    <td class="silver_b">..44</td>
                    <td class="silver_b">..50</td>
                    <td class="silver_b">..51</td>
                    <td class="silver_b">..54</td>
                    <td class="silver_b">..60</td>
                    <td class="silver_b">..61</td>
                    <td class="silver_rb">..64</td>
                    <td class="silver_b">..70</td>
                    <td class="silver_b">..71</td>
                </tr>
                <tr>
                    <td class="silver_r">20..</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2020"/></td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2030"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2040"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2041"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2050"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2051"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>
                <tr>
                    <td class="silver_r">21..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2101"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2110"/></td>
                    <td class="silver_r "><input type="radio" onclick="test();" name="model" value="2115"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2120"/></td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2130"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2140"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2141"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2150"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2151"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2160"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2161"/></td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2170"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2171"/></td>
                </tr>
                <tr>
                    <td class="silver_r">22..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2201"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2210"/></td>
                    <td class="silver_r "><input type="radio" onclick="test();" name="model" value="2215"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2220"/></td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2230"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2240"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2241"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>
                <tr>
                    <td class="silver_r">23..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2301"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2310"/></td>
                    <td class="silver_r "><input type="radio" onclick="test();" name="model" value="2315"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2320"/></td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2330"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2340"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2341"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2350"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2351"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>
                <tr>
                    <td class="silver_r">24..</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2401"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2410"/></td>
                    <td class="silver_r "><input type="radio" onclick="test();" name="model" value="2415"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2420"/></td>
                    <td class="silver_r "><input type="radio" onclick="test();" name="model" value="2424"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2430"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2434"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2440"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2444"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2450"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2454"/></td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2460"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r "><input type="radio" onclick="test();" name="model" value="2464"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                </tr>
                <tr>
                    <td class="silver_r">25..</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2520"/></td>
                    <td class="silver_r">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2530"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2540"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver "><input type="radio" onclick="test();" name="model" value="2550"/></td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver">&nbsp;</td>
                    <td class="silver_r">&nbsp;</td>
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
                            <input type="radio" name="ed_izm" value="6" onclick="changeEdIzm();"> &nbsp;kgf/cm&sup2;
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
                                <td class="width60"><input name="low" id="low" type="text" size="8"
                                                           onkeydown="changeNsLimit()">
                                </td>
                                <td class="width60"><input type="text" id="hi" name="hi" size="8"
                                                           onkeydown="changeNsLimit()"></td>
                                <td class="width60"><select name="hid" id="hid" style="width:80px;"
                                                            onchange="changeLimit();">

                                    <%--<td class="col1"><input type="text" name="low" size="8"></td>--%>
                                    <%--<td class="col1"><input type="text" name="hi" size="8"></td>--%>
                                    <%--<td class="col1"><select name="hid" style="width:80px;">--%>
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
                                <td class="col3">КМЧ</td>
                                <td class="col3">-П</td>
                                <td class="col3">-Хим</td>
                                <td class="col3">-Р</td>
                            </tr>
                            <tr>
                                <td class="col3"><select name="kmch" id="kmch" style="width:60px;"></select></td>
                                <td class="col3"><input type="checkbox" name="p" id="p"></td>
                                <td class="col3"><input type="checkbox" name="him" id="him"></td>
                                <td class="col3"><input type="checkbox" name="r" id="r"></td>


                            </tr>
                        </table>
                    </div>
                </td>
                <td>
                    <div id="right">
                        <label> <input type="checkbox" id="tu" name="tu"> &nbsp; ТУ </label> <br>
                        После спецификации: <br>
                        <input type="text" name="afterSpec" id="afterSpec" size="40">

                    </div>
                </td>
            </tr>
        </table>


        <input type="hidden" value="addPressureSensor" name="method">



</div>
<div id="downButtons">
    <input type="submit" value="Отправить">
    <input type="button" value="Отменить" onclick="cancel();">

</div>
</form>





