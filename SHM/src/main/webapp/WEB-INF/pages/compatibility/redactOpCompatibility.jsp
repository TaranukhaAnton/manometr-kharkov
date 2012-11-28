
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>


<div id="parent">

<form action="#">
    <table cellspacing="0" class="models"> <tbody><tr> <td class="silver_rb">  <img hspace="4" height="18" border="0" width="18" onclick="clearTable();" src="images/delete.gif"></td>
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
        <td class="silver "><input type="checkbox" id="3030"></td>
        <td class="silver">&nbsp;</td>
        <td class="silver "><input type="checkbox" id="3040"></td>
        <td class="silver "><input type="checkbox" id="3041"></td>
        <td class="silver "><input type="checkbox" id="3050"></td>
        <td class="silver "><input type="checkbox" id="3051"></td>
        <td class="silver">&nbsp;</td>
        <td class="silver">&nbsp;</td>
        <td class="silver">&nbsp;</td>
        <td class="silver">&nbsp;</td>
    </tr>
    <tr>
        <td class="silver_r">31..</td>
        <td class="silver "><input type="checkbox" id="3130"></td>
        <td class="silver "><input type="checkbox" id="3131"></td>
        <td class="silver "><input type="checkbox" id="3140"></td>
        <td class="silver "><input type="checkbox" id="3141"></td>
        <td class="silver "><input type="checkbox" id="3150"></td>
        <td class="silver "><input type="checkbox" id="3151"></td>
        <td class="silver "><input type="checkbox" id="3160"></td>
        <td class="silver "><input type="checkbox" id="3161"></td>
        <td class="silver "><input type="checkbox" id="3170"></td>
        <td class="silver "><input type="checkbox" id="3171"></td>
    </tr>
    <tr>
        <td class="silver_r">32..</td>
        <td class="silver "><input type="checkbox" id="3230"></td>
        <td class="silver "><input type="checkbox" id="3231"></td>
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
        <td class="silver "><input type="checkbox" id="3330"></td>
        <td class="silver "><input type="checkbox" id="3331"></td>
        <td class="silver "><input type="checkbox" id="3340"></td>
        <td class="silver "><input type="checkbox" id="3341"></td>
        <td class="silver "><input type="checkbox" id="3350"></td>
        <td class="silver "><input type="checkbox" id="3351"></td>
        <td class="silver">&nbsp;</td>
        <td class="silver">&nbsp;</td>
        <td class="silver">&nbsp;</td>
        <td class="silver">&nbsp;</td>
    </tr>
    </tbody></table>



</form>






    <div  id="tabs">
    <ul>
        <li><a href="#limits">Пределы</a></li>
        <li><a href="#ispolnenie">Исполнения</a></li>
        <li><a href="#materials">Материалы</a></li>
        <li><a href="#error">Погрешность</a></li>
        <li><a href="#output">Выходы</a></li>
    </ul>


        <div class="height" id="limits" >
            <fieldset>
                <label for="loLim">От:</label>
                <select name="loLim" id="loLim">
                    <optgroup label="КПа">
                        <option value="1">0,04КПа</option>
                        <option value="2">0,063КПа</option>
                        <option value="3">0,10КПа</option>
                        <option value="4">0,16КПа</option>
                        <option value="5">0,25КПа</option>
                        <option value="6" selected="selected">0,40КПа</option>
                        <option value="7">0,63КПа</option>
                        <option value="8" >1,0КПа</option>
                        <option value="9">1,6КПа</option>
                        <option value="10">2,5КПа</option>
                        <option value="11">4,0КПа</option>
                        <option value="12">6,3КПа</option>
                        <option value="13">10КПа</option>
                        <option value="14">16КПа</option>
                        <option value="15">25КПа</option>
                        <option value="16">40КПа</option>
                        <option value="17">63КПа</option>
                        <option value="18">100КПа</option>
                        <option value="19">160КПа</option>
                        <option value="20">250КПа</option>
                        <option value="21">400КПа</option>
                        <option value="22">630КПа</option>
                    </optgroup>
                    <optgroup label="МПа">
                        <option value="23" >1,0MПа</option>
                        <option value="24">1,6MПа</option>
                        <option value="25">2,5MПа</option>
                        <option value="26">4,0MПа</option>
                        <option value="27">6,3MПа</option>
                        <option value="28">10MПа</option>
                        <option value="29">16MПа</option>
                        <option value="30">25MПа</option>
                        <option value="31">40MПа</option>
                        <option value="32">63MПа</option>
                        <option value="33">100MПа</option>

                    </optgroup>

                </select>

                <label for="hiLim">До:</label>
                <select name="hiLim" id="hiLim">
                    <optgroup label="КПа">
                        <option value="1">0,04КПа</option>
                        <option value="2">0,063КПа</option>
                        <option value="3">0,10КПа</option>
                        <option value="4">0,16КПа</option>
                        <option value="5">0,25КПа</option>
                        <option value="6" >0,40КПа</option>
                        <option value="7">0,63КПа</option>
                        <option value="8" >1,0КПа</option>
                        <option value="9">1,6КПа</option>
                        <option value="10">2,5КПа</option>
                        <option value="11">4,0КПа</option>
                        <option value="12">6,3КПа</option>
                        <option value="13">10КПа</option>
                        <option value="14">16КПа</option>
                        <option value="15">25КПа</option>
                        <option value="16">40КПа</option>
                        <option value="17" selected="selected">63КПа</option>
                        <option value="18">100КПа</option>
                        <option value="19">160КПа</option>
                        <option value="20">250КПа</option>
                        <option value="21">400КПа</option>
                        <option value="22">630КПа</option>
                    </optgroup>
                    <optgroup label="МПа">
                        <option value="23" >1,0MПа</option>
                        <option value="24">1,6MПа</option>
                        <option value="25">2,5MПа</option>
                        <option value="26">4,0MПа</option>
                        <option value="27">6,3MПа</option>
                        <option value="28">10MПа</option>
                        <option value="29">16MПа</option>
                        <option value="30">25MПа</option>
                        <option value="31">40MПа</option>
                        <option value="32">63MПа</option>
                        <option value="33">100MПа</option>
                    </optgroup>
                </select>
            </fieldset>
            <input type="button" id="okLimits" value="Применить" class="okbut"  style="margin-top: 60px;" onclick="setLimits();"/>
        </div>

        <div class="height"  id="ispolnenie">
            <form name="isp" action="#">
                <fieldset>
                    <legend>
                        Исполнения
                    </legend>
                    <input type="checkbox" id="isp01" value="0"> &nbsp; - общ <br/>

                    <input type="checkbox" id="isp03" value="2"> &nbsp; - Ex <br/>


                    <input type="button" id="okIspolnenie" value="Применить" class="okbut" onclick="setIsp();"/>

                </fieldset>
            </form>
        </div>


        <div class="height"  id="materials">
            <form name="Mat" action="#">
                <fieldset>
                    <legend>
                        Материалы
                    </legend>
                    <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/>
                    <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>
                    <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>
                    <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>
                    <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/>
                    <input type="button" id="okMaterials" value="Применить" class="okbut" onclick="setMat();"/>

                </fieldset>
            </form>
        </div>

        <div class="height"  id="error">
            <form name="err" action="#">
                <fieldset>
                    <legend>
                        Погрешность
                    </legend>

                    <input type="checkbox" id="error025" value="2"> &nbsp; - 0,25 <br/>
                    <input type="checkbox" id="error05" value="3"> &nbsp; - 0,5 <br/>
                    <input type="checkbox" id="error10" value="4"> &nbsp; - 1,0 <br/>
                    <input type="button" id="okError" value="Применить" class="okbut" onclick="setErr();"/>

                </fieldset>
            </form>
        </div>


        <div class="height"  id="output">
            <form name="ou" action="#">
                <fieldset>
                    <legend>
                        Выходной сигнал
                    </legend>
                    <input type="checkbox" id="ou42" value="0"> &nbsp; - 42 <br/>

                    <input type="checkbox" id="ou05" value="3"> &nbsp; - 05 <br/>


                    <input type="button" id="okOutput" value="Применить" class="okbut" onclick="setOut();"/>

                </fieldset>
            </form>
        </div>


    </div>
</div>


