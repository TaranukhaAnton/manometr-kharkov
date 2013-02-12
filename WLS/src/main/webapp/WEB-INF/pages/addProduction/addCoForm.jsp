<%request.setCharacterEncoding("UTF-8");%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

    <link href="../css/addPressureSensor.css" rel="stylesheet" type="text/css"/>
    <script src="../js/local/add/addCo.js" type="text/javascript"></script>


<form action="add_pressure_sensor" name="mainForm" onsubmit="return  validate();">
<div id="parent"  >


<input type="hidden" name="invoice_id" id="invoice_id" value="<%=request.getParameter("invoice_id")%>">
<% if(request.getParameter("invoice_item_id")!=null){%>
<input type="hidden" name="invoice_item_id" id="invoice_item_id" value="<%=request.getParameter("invoice_item_id")%>">
<% }%>


<div id="head">
    <label><input type="radio" name="typeTxt" value="0" checked="checked"> &quot;Сафiр М&quot;</label>
    <label><input type="radio" name="typeTxt" value="1"> &quot;Сафiр&quot; </label>
    <label><input type="radio" name="typeTxt" value="2"> СМХ </label>
    &nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;
    Перед спецификацией &nbsp;&nbsp;
    <input type="text" name="beforeSpec" id="beforeSpec" size="40">


</div>
<div id="model">
<table cellspacing="0" class="models">
<tbody>
<tr>
    <td class="silver_rb">  &nbsp;</td>
    <td class="silver_b">..01</td>
    <td class="silver_b">..10</td>
    <td class="silver_rb">..15</td>
    <td class="silver_b">..20</td>
    <td class="silver_rb">..24</td>
    <td class="silver_b">..30</td>
    <td class="silver_b">..33</td>
    <td class="silver_b">..34</td>
    <td class="silver_rb">..35</td>
    <td class="silver_b">..40</td>
    <td class="silver_b">..41</td>
    <td class="silver_b">..42</td>
    <td class="silver_b">..43</td>
    <td class="silver_b">..44</td>
    <td class="silver_rb">..45</td>
    <td class="silver_b">..50</td>
    <td class="silver_b">..51</td>
    <td class="silver_b">..52</td>
    <td class="silver_b">..53</td>
    <td class="silver_b">..54</td>
    <td class="silver_rb">..55</td>
    <td class="silver_b">..60</td>
    <td class="silver_b">..61</td>
    <td class="silver_b">..62</td>
    <td class="silver_b">..63</td>
    <td class="silver_rb">..64</td>
    <td class="silver_b">..70</td>
    <td class="silver_b">..71</td>
</tr>
<tr>
    <td class="silver_r">50..</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver_r">&nbsp;</td>
    <td class="row0  silver "><input type="radio" value="5020" name="model" onclick="test();"></td>
    <td class="row0 silver_r">&nbsp;</td>
    <td class="row0  silver "><input type="radio" value="5030" name="model" onclick="test();"></td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver_r">&nbsp;</td>
    <td class="row0  silver "><input type="radio" value="5040" name="model" onclick="test();"></td>
    <td class="row0  silver "><input type="radio" value="5041" name="model" onclick="test();"></td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver_r">&nbsp;</td>
    <td class="row0  silver "><input type="radio" value="5050" name="model" onclick="test();"></td>
    <td class="row0  silver "><input type="radio" value="5051" name="model" onclick="test();"></td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver_r">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver_r">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
    <td class="row0 silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">51..</td>
    <td class="row1  silver "><input type="radio" value="5101" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5110" name="model" onclick="test();"></td>
    <td class="row1  silver_r "><input type="radio" value="5115" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5120" name="model" onclick="test();"></td>
    <td class="row1 silver_r">&nbsp;</td>
    <td class="row1  silver "><input type="radio" value="5130" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5133" name="model" onclick="test();"></td>
    <td class="row1 silver">&nbsp;</td>
    <td class="row1  silver_r "><input type="radio" value="5135" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5140" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5141" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5142" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5143" name="model" onclick="test();"></td>
    <td class="row1 silver">&nbsp;</td>
    <td class="row1  silver_r "><input type="radio" value="5145" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5150" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5151" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5152" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5153" name="model" onclick="test();"></td>
    <td class="row1 silver">&nbsp;</td>
    <td class="row1  silver_r "><input type="radio" value="5155" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5160" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5161" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5162" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5163" name="model" onclick="test();"></td>
    <td class="row1 silver_r">&nbsp;</td>
    <td class="row1  silver "><input type="radio" value="5170" name="model" onclick="test();"></td>
    <td class="row1  silver "><input type="radio" value="5171" name="model" onclick="test();"></td>
</tr>
<tr>
    <td class="silver_r">52..</td>
    <td class="row2  silver "><input type="radio" value="5201" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5210" name="model" onclick="test();"></td>
    <td class="row2  silver_r "><input type="radio" value="5215" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5220" name="model" onclick="test();"></td>
    <td class="row2 silver_r">&nbsp;</td>
    <td class="row2  silver "><input type="radio" value="5230" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5233" name="model" onclick="test();"></td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2  silver_r "><input type="radio" value="5235" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5240" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5241" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5242" name="model" onclick="test();"></td>
    <td class="row2  silver "><input type="radio" value="5243" name="model" onclick="test();"></td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2  silver_r "><input type="radio" value="5245" name="model" onclick="test();"></td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver_r">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver_r">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
    <td class="row2 silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">53..</td>
    <td class="row3  silver "><input type="radio" value="5301" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5310" name="model" onclick="test();"></td>
    <td class="row3  silver_r "><input type="radio" value="5315" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5320" name="model" onclick="test();"></td>
    <td class="row3 silver_r">&nbsp;</td>
    <td class="row3  silver "><input type="radio" value="5330" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5333" name="model" onclick="test();"></td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3  silver_r "><input type="radio" value="5335" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5340" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5341" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5342" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5343" name="model" onclick="test();"></td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3  silver_r "><input type="radio" value="5345" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5350" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5351" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5352" name="model" onclick="test();"></td>
    <td class="row3  silver "><input type="radio" value="5353" name="model" onclick="test();"></td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3  silver_r "><input type="radio" value="5355" name="model" onclick="test();"></td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3 silver_r">&nbsp;</td>
    <td class="row3 silver">&nbsp;</td>
    <td class="row3 silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">54..</td>
    <td class="row4  silver "><input type="radio" value="5401" name="model" onclick="test();"></td>
    <td class="row4  silver "><input type="radio" value="5410" name="model" onclick="test();"></td>
    <td class="row4  silver_r "><input type="radio" value="5415" name="model" onclick="test();"></td>
    <td class="row4  silver "><input type="radio" value="5420" name="model" onclick="test();"></td>
    <td class="row4  silver_r "><input type="radio" value="5424" name="model" onclick="test();"></td>
    <td class="row4  silver "><input type="radio" value="5430" name="model" onclick="test();"></td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4  silver "><input type="radio" value="5434" name="model" onclick="test();"></td>
    <td class="row4 silver_r">&nbsp;</td>
    <td class="row4  silver "><input type="radio" value="5440" name="model" onclick="test();"></td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4  silver "><input type="radio" value="5444" name="model" onclick="test();"></td>
    <td class="row4 silver_r">&nbsp;</td>
    <td class="row4  silver "><input type="radio" value="5450" name="model" onclick="test();"></td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4  silver "><input type="radio" value="5454" name="model" onclick="test();"></td>
    <td class="row4 silver_r">&nbsp;</td>
    <td class="row4  silver "><input type="radio" value="5460" name="model" onclick="test();"></td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4  silver_r "><input type="radio" value="5464" name="model" onclick="test();"></td>
    <td class="row4 silver">&nbsp;</td>
    <td class="row4 silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">55..</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver_r">&nbsp;</td>
    <td class="row5  silver "><input type="radio" value="5520" name="model" onclick="test();"></td>
    <td class="row5 silver_r">&nbsp;</td>
    <td class="row5  silver "><input type="radio" value="5530" name="model" onclick="test();"></td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver_r">&nbsp;</td>
    <td class="row5  silver "><input type="radio" value="5540" name="model" onclick="test();"></td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver_r">&nbsp;</td>
    <td class="row5  silver "><input type="radio" value="5550" name="model" onclick="test();"></td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver_r">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver_r">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
    <td class="row5 silver">&nbsp;</td>
</tr>

</tbody>
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
                    <input type="radio" name="ed_izm" value="6" onclick="changeEdIzm();"> &nbsp;kgf/сm&sup2; <br>
                    <input type="radio" name="ed_izm" value="7" onclick="changeEdIzm();">&nbsp;kgf/m&sup2; <br>
                    <input type="radio" name="ed_izm" value="8" onclick="changeEdIzm();">&nbsp;bar <br>
                    <input type="radio" name="ed_izm" value="9" onclick="changeEdIzm();">&nbsp;mbar <br>
                </fieldset>
            </div>
        </td>
        <td class="topAlig">
            <div id="stat"/>
        </td>
        <td class="topAlig">
            <div id="output"/>
        </td>
        <td class="topAlig">
            <div id="du"/>
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
                        <td class="width60"><input type="text" name="hi" id="hi" size="8"
                                                   onkeydown="changeNsLimit()"></td>
                        <td class="width60"><select name="hid" id="hid" style="width:80px;"
                                                    onchange="changeLimit();">


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
                        <td class="width60" colspan="7">
                            Опции
                        </td>
                    </tr>
                    <tr>
                        <td class="col3">КМЧ</td>
                        <td class="col3">-И</td>
                        <td class="col3">-ПИ</td>
                        <td class="col3">-П</td>
                        <td class="col3">-ВМ</td>
                        <td class="col3">-Хим</td>
                        <td class="col3">-Р</td>
                    </tr>
                    <tr>
                        <td class="col3"><select name="kmch" style="width:60px;" id="kmch"></select></td>
                        <td class="col3"><input type="checkbox" name="i" id="i"></td>
                        <td class="col3"><input type="checkbox" name="pi" id="PI"></td>
                        <td class="col3"><input type="checkbox" name="p" id="p"></td>
                        <td class="col3"><input type="checkbox" name="vm" id="vm"></td>
                        <td class="col3"><input type="checkbox" name="him" id="him"></td>
                        <td class="col3"><input type="checkbox" name="r" id="r"></td>


                    </tr>
                </table>
            </div>
        </td>
        <td>
            <div id="right">
                <label> <input type="checkbox" name="tu" id="tu"> &nbsp; ТУ </label>
                <br> После спецификации: <br>
                <input type="text" name="afterSpec" id="afterSpec" size="40">

            </div>
        </td>
    </tr>
</table>


<%--<input type="hidden" value="addPressureSensor" name="method">--%>


</div>
<div id="downButtons">
    <input type="submit" value="Отправить">
    <input type="button" value="Отменить" onclick="cancel();">

</div>
</form>



