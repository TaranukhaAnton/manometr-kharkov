<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="../js/local/optionPrice.js"></script>
<link rel="stylesheet" type="text/css" href="../css/redactOptionPrice.css"/>




<div id="tabs" class="tabs">
    <!-- Это сами вкладки -->
    <ul class="tabNavigation">
        <li><a class="" href="#co">Цифровые</a></li>
        <li><a class="" href="#ao">Аналоговые</a></li>
        <li><a class="" href="#op">Однопредельные</a></li>
    </ul>
    <div id="co">
        <jsp:include page="oneOptionPage.jsp?type=0"/>
    </div>
    <div id="ao">
        <jsp:include page="oneOptionPage.jsp?type=1"/>
    </div>
    <div id="op">
        <jsp:include page="oneOptionPage.jsp?type=2"/>
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