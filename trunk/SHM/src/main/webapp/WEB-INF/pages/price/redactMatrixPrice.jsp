<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="../js/local/matrixPrice.js"></script>
<link rel="stylesheet" type="text/css" href="../css/redactMatrixPrice.css"/>

<div id="header">
    <table class="ttt" style="border-collapse:collapse; padding:0px;">
        <tr>
            <td style="padding-top:0px;">
                <a href="#" onclick="applyTmpValues();"> Накатить черновик</a>
            </td>
            <td style="padding-top:0px;">
                <a href="#" onclick="resetTmpValues()"> Обнулить черновик</a>
            </td>
            <td style="padding-top:0px;">
                <a href="#" onclick="priceValuesToTmp()"> Прайс в черновик</a>
            </td>
        </tr>
    </table>


</div>

<div id="query">

<form action="#" name="myForm">

<div id="tabs">
<ul class="tabNavigation">
    <li><a href="#first">Цифровые</a></li>
    <li><a href="#second">Аналоговые</a></li>
    <li><a href="#third">Однопредельные</a></li>
</ul>

<div id="first">
<table class="models" cellspacing="0">
<tr>
    <td class="silver_rb"><img src="../images/field.png" width="18" height="18" onclick="selectAll('first');" hspace="4"
                               border="0"/></td>
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
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5020"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5030"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5040"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5041"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5050"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5051"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">51..</td>
    <td class="silver "><input type="checkbox" name="models" id="5101"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5110"/></td>
    <td class="silver_r "><input type="checkbox" name="models" id="5115"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5120"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5130"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5133"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5135"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5140"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5141"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5142"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5143"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5145"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5150"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5151"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5152"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5153"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5155"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5160"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5161"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5162"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5163"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5170"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5171"/></td>
</tr>
<tr>
    <td class="silver_r">52..</td>
    <td class="silver "><input type="checkbox" name="models" id="5201"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5210"/></td>
    <td class="silver_r "><input type="checkbox" name="models" id="5215"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5220"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5230"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5233"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5235"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5240"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5241"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5242"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5243"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5245"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">53..</td>
    <td class="silver "><input type="checkbox" name="models" id="5301"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5310"/></td>
    <td class="silver_r "><input type="checkbox" name="models" id="5315"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5320"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5330"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5333"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5335"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5340"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5341"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5342"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5343"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5345"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5350"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5351"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5352"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5353"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5355"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">54..</td>
    <td class="silver "><input type="checkbox" name="models" id="5401"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5410"/></td>
    <td class="silver_r "><input type="checkbox" name="models" id="5415"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5420"/></td>
    <td class="silver_r "><input type="checkbox" name="models" id="5424"/></td>
    <td class="silver "><input type="checkbox" name="models" id="5430"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5434"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5440"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5444"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5450"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5454"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5460"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r "><input type="checkbox" name="models" id="5464"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
</tr>
<tr>
    <td class="silver_r">55..</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5520"/></td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5530"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5540"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
    <td class="silver "><input type="checkbox" name="models" id="5550"/></td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver">&nbsp;</td>
    <td class="silver_r">&nbsp;</td>
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
<div id="second">
    <table class="models" cellspacing="0">
        <tr>
            <td class="silver_rb"><img src="../images/field.png" width="18" height="18" onclick="selectAll('second');"
                                       hspace="4"
                                       border="0"/></td>
            <td class="silver_b">..01</td>
            <td class="silver_b">..10</td>
            <td class="silver_b">..15</td>
            <td class="silver_b">..20</td>
            <td class="silver_b">..24</td>
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
            <td class="silver_b">..64</td>
            <td class="silver_b">..70</td>
            <td class="silver_b">..71</td>
        </tr>
        <tr>
            <td class="silver_r">20..</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2020"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2030"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2040"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2041"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2050"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2051"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
        </tr>
        <tr>
            <td class="silver_r">21..</td>
            <td class="silver "><input type="checkbox" name="models" id="2101"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2110"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2115"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2120"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2130"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2140"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2141"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2150"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2151"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2160"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2161"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2170"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2171"/></td>
        </tr>
        <tr>
            <td class="silver_r">22..</td>
            <td class="silver "><input type="checkbox" name="models" id="2201"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2210"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2215"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2220"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2230"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2240"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2241"/></td>
            <td class="silver">&nbsp;</td>
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
            <td class="silver_r">23..</td>
            <td class="silver "><input type="checkbox" name="models" id="2301"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2310"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2315"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2320"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2330"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2340"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2341"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2350"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2351"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
        </tr>
        <tr>
            <td class="silver_r">24..</td>
            <td class="silver "><input type="checkbox" name="models" id="2401"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2410"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2415"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2420"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2424"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2430"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2434"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2440"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2444"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2450"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2454"/></td>
            <td class="silver "><input type="checkbox" name="models" id="2460"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2464"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
        </tr>
        <tr>
            <td class="silver_r">25..</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2520"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2530"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2540"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="2550"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
        </tr>

    </table>
</div>
<div id="third">
    <table class="models" cellspacing="0">
        <tr>
            <td class="silver_rb"><img src="../images/field.png" width="18" height="18" onclick="selectAll('third');"
                                       hspace="4"
                                       border="0"/></td>
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
            <td class="silver "><input type="checkbox" name="models" id="3030"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver "><input type="checkbox" name="models" id="3040"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3041"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3050"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3051"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
        </tr>
        <tr>
            <td class="silver_r">31..</td>
            <td class="silver "><input type="checkbox" name="models" id="3130"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3131"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3140"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3141"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3150"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3151"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3160"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3161"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3170"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3171"/></td>
        </tr>
        <tr>
            <td class="silver_r">32..</td>
            <td class="silver "><input type="checkbox" name="models" id="3230"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3231"/></td>
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
            <td class="silver "><input type="checkbox" name="models" id="3330"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3331"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3340"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3341"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3350"/></td>
            <td class="silver "><input type="checkbox" name="models" id="3351"/></td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
            <td class="silver">&nbsp;</td>
        </tr>
    </table>

</div>
</div>


<table>
    <tr>
        <td style="vertical-align:top">
            <div id="ispolnenie">

                <fieldset>
                    <legend>
                        Исполнения
                    </legend>
                    <input type="checkbox" name="isp" value="0"> &nbsp; - общ <br/>
                    <input type="checkbox" name="isp" value="1"> &nbsp; - Вн <br/>
                    <input type="checkbox" name="isp" value="2"> &nbsp; - Ex <br/>
                    <input type="checkbox" name="isp" value="3"> &nbsp; - AC <br/>
                    <input type="checkbox" name="isp" value="4"> &nbsp; - K <br/> <br/><br/>


                </fieldset>

            </div>
        </td>
        <td style="vertical-align:top">
            <div id="materials">

                <fieldset>
                    <legend>
                        Материалы
                    </legend>
                    <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/>
                    <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>
                    <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>
                    <input type="checkbox" name="mat" value="3"> &nbsp; - 07 <br/>
                    <input type="checkbox" name="mat" value="4"> &nbsp; - 09 <br/>
                    <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>
                    <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/>

                </fieldset>

            </div>
        </td>
        <td style="vertical-align:top">
            <div id="error">

                <fieldset>
                    <legend>
                        Погрешность
                    </legend>
                    <input type="checkbox" name="err" value="0"> &nbsp; - 0,1 <br/>
                    <input type="checkbox" name="err" value="1"> &nbsp; - 0,15 <br/>
                    <input type="checkbox" name="err" value="2"> &nbsp; - 0,25 <br/>
                    <input type="checkbox" name="err" value="3"> &nbsp; - 0,5 <br/>
                    <input type="checkbox" name="err" value="4"> &nbsp; - 1,0 <br/><br/><br/>

                </fieldset>

            </div>
        </td>
        <td style="vertical-align:top">
            <div id="klimat">
                <fieldset>
                    <legend>Климатика</legend>


                    <input type="checkbox" name="klim" value="0"> &nbsp;УХЛ3.1*<br>
                    <input type="checkbox" name="klim" value="1"> &nbsp;У2*<br>
                    <input type="checkbox" name="klim" value="2"> &nbsp;Т3* <br> <br/><br/><br/><br/>


                </fieldset>
            </div>
        </td>
        <td style="vertical-align:top">
            <div id="inpCena">
                <fieldset>
                    <legend></legend>


                    <input type="text" name="price" maxlength="10" size="10"> &nbsp; Цена<br>
                    <input type="text" name="cost" maxlength="10" size="10"> &nbsp; Себест.<br>
                    <input type="button" value="Применить" class="okbut" onclick="okFunk();"/> <br/><br/><br/>
                    <input type="button" value="Просмотр" class="okbut" onclick="okFunk2();"/> <br/><br/><br/>


                </fieldset>
            </div>
        </td>

    </tr>
</table>


</form>

</div>

<div id="result_dialog" title="Результат">
    <div id="result"/>
</div>