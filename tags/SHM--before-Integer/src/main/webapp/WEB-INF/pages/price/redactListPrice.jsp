<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="../js/local/listPrice.js"></script>


<div id="tabs">
    <ul class="tabNavigation">
        <li><a href="#id3">д.д. спец.</a></li>
        <li><a href="#id4">БП</a></li>
        <li><a href="#id5">ИБ</a></li>
        <li><a href="#id6">Диаф. кам.</a></li>
        <li><a href="#id7">Проч. прод</a></li>
        <li><a href="#id8">Вычисл.</a></li>
        <li><a href="#id9">Стор. произв.</a></li>
    </ul>


    <div id="id3">
        <jsp:include page="//list_price/page?type=3"/>
    </div>
    <div id="id4">
        <jsp:include page="//list_price/page?type=4"/>
    </div>
    <div id="id5">
        <jsp:include page="//list_price/page?type=5"/>
    </div>
    <div id="id6">
        <jsp:include page="//list_price/page?type=6"/>
    </div>
    <div id="id7">
        <jsp:include page="//list_price/page?type=7"/>
    </div>
    <div id="id8">
        <jsp:include page="//list_price/page?type=8"/>
    </div>
    <div id="id9">
        <jsp:include page="//list_price/page?type=9"/>
    </div>


</div>


<div id="dialog-form">
    <p class="validateTips">Все поля должны быть заполненны.</p>


    <fieldset>
        <input type="hidden" name="type" id="type"/>
        <input type="hidden" name="id" id="id"/>
        <label for="name">Название</label>
        <textarea rows="4" cols="50" name="name" id="name" style="width: 315px;"></textarea><br><br>
        <label for="cost">Себестоимость</label> <br>
        <input type="text" name="cost" id="cost" class="text ui-widget-content ui-corner-all"/>  <br>
        <label for="price">Цена</label>   <br>
        <input type="text" name="price" id="price" class="text ui-widget-content ui-corner-all"/>
    </fieldset>

</div>