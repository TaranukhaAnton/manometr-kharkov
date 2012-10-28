<%@ page import="java.util.List" %>
<%@ page import="application.data.price.ProductionPrice" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@ page import="application.hibernate.Factory" %>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<%--<link type="text/css" href="css/tst/ui.all.css" rel="stylesheet"/>--%>
<link type="text/css" href=" css/tst/jquery.ui.all.css" rel="stylesheet"/>
<link type="text/css" href="css/tabs.css" rel="stylesheet"/>
<link type="text/css" href="css/smartTable.css" rel="stylesheet"/>


<style type="text/css">
    #dialog-form {
        font-size: 62.5%;
    }

    label, input {
        display: block;
    }

    input.text {
        margin-bottom: 12px;
        width: 40%;
        padding: .4em;
    }

    fieldset {
        padding: 0;
        border: 0;
        margin-top: 25px;
    }

    table.simple td.col1 {
        width: 70%;
    }

    table.simple td.col2 {
        width: 13%;
    }

    table.simple td.col3 {
        width: 4%;
    }

    div.tabs div {
    /*overflow-y: scroll; *//* Добавляем полосы прокрутки */
        height: 620px;
    }

    div.tabs {
    /*background: #333;*/
    /*padding: 20px;*/
        height: 651px;
    }



</style>
<script type="text/javascript">


    $(function() {
        var tabContainers = $('div.tabs > div'); // получаем массив контейнеров
        var saved_tab = parseInt(getCookie("the_tab_cookie")) || 0;
        tabContainers.hide().filter(':eq(' + saved_tab + ')').show();
        $('div.tabs ul.tabNavigation a').click(function () {
            var index = $('div.tabs ul.tabNavigation a').index(this);
            setCookie("the_tab_cookie", index);
            tabContainers.hide(); // прячем все табы
            tabContainers.filter(this.hash).show(); // показываем содержимое текущего
            $('div.tabs ul.tabNavigation a').removeClass('selected'); // у всех убираем класс 'selected'
            $(this).addClass('selected'); // текушей вкладке добавляем класс 'selected'
            return false;
        }).filter(':eq(' + saved_tab + ')').click();

        $("#dialog").dialog("destroy");
        var
                id = $("#id"),
                type = $("#type"),
                name = $("#name"),
                cost = $("#cost"),
                price = $("#price"),
                allFields = $([]).add(name).add(cost).add(price).add(id).add(type),
                tips = $(".validateTips");

        function updateTips(t) {
            tips
                    .text(t)
                    .addClass('ui-state-highlight');
            setTimeout(function() {
                tips.removeClass('ui-state-highlight', 1500);
            }, 500);
        }

        function checkLength(o, n, min, max) {

            if (o.val().length > max || o.val().length < min) {
                o.addClass('ui-state-error');
                updateTips("Длина поля должна быть больше " + min + " и меньше " + max + " символов.");
                return false;
            } else {
                return true;
            }

        }

        function checkRegexp(o, regexp, n) {

            if (!( regexp.test(o.val()) )) {
                o.addClass('ui-state-error');
                updateTips(n);
                return false;
            } else {
                return true;
            }

        }

        $("#dialog-form").dialog({
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            resizable:false,
            buttons: {
                'Применить': function() {
                    var bValid = true;
                    allFields.removeClass('ui-state-error');
                    bValid = bValid && checkLength(name, "username", 5, 500);
                    bValid = bValid && checkRegexp(cost, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                    bValid = bValid && checkRegexp(cost, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                    bValid = bValid && checkRegexp(price, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                    bValid = bValid && checkRegexp(price, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                    if (bValid) {
                        $.post("priceAction.do?method=addListItem", {"id":id.val(),"type":type.val(),"name":name.val(),"cost" : cost.val(),"price":price.val()},
                                function(data) {
                                    document.location.reload()
                                });
                        $(this).dialog('close');
                    }
                },
                'Отмена': function() {
                    $(this).dialog('close');
                }
            },
            close: function() {
                allFields.val('').removeClass('ui-state-error');
            }
        });
        tabContainers.css("visibility", "visible");
    });

    function del(id) {
        if (confirm("Удалить позицию?"))
            self.location.href = 'priceAction.do?method=deleteListItem&id=' + id;

    }

    function view(type, id)
    {
        var str;
        if (id)
        {
            str = "Редактировать";
            $.post("priceAction.do?method=getListItem", {"id" : id}, function(data) {
              //  alert(data)
                if (data.length > 0) {
                    var response = eval("(" + data + ")");
                    
                    $("#id").val(response.id);
                    $("#type").val(response.type);
                    $("#name").val(decodeURI(response.name));
                    $("#price").val(response.price);
                    $("#cost").val(response.cost);
                }
            });
        } else
        {
            str = "Добавить";
            $("#type").val(type);
        }

        var myArray = new Array();
        myArray[0] = "д.д. специальный";
        myArray[1] = "Блок питания";
        myArray[2] = "измерительный блок";
        myArray[3] = "Диаф. кам.";
        myArray[4] = "прочую продукцию";
        myArray[5] = "вичислитель";
        myArray[6] = "прод. стор. производителя";

        $('#dialog-form').dialog({title:str + " " + myArray[type-3]});
        $('#dialog-form').dialog('open');

    }
</script>

</head>
<body>


<%
    GenericHibernateDAO<ProductionPrice> dao = Factory.getProductionDAO();
    request.setAttribute("list0", dao.findListByParameter("type", 3));
    request.setAttribute("list1", dao.findListByParameter("type", 4));
    request.setAttribute("list2", dao.findListByParameter("type", 5));
    request.setAttribute("list3", dao.findListByParameter("type", 6));
    request.setAttribute("list4", dao.findListByParameter("type", 7));
    request.setAttribute("list5", dao.findListByParameter("type", 8));
    request.setAttribute("list6", dao.findListByParameter("type", 9));
%>


<div class="tabs">
    <ul class="tabNavigation">
        <li><a class="" href="#id0">д.д. спец.</a></li>
        <li><a class="" href="#id1">БП</a></li>
        <li><a class="" href="#id2">ИБ</a></li>
        <li><a class="" href="#id3">Диаф. кам.</a></li>
        <li><a class="" href="#id4">Проч. прод</a></li>
        <li><a class="" href="#id5">Вычисл.</a></li>
        <li><a class="" href="#id6">Стор. произв.</a></li>
    </ul>


    <div id="id0">

        <a href="javascript:void(view(3,''))">Добавить</a><br>
        <display:table name="list0" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table0">
            <display:column title="Обозначение" class="col1">
                <a href="javascript:void(view(3,'${table0.id}'))">${table0.name}</a>
            </display:column>
            <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
            <display:column property="price" title="Цена" class="col2" maxLength="8"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table0.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>

    </div>
    <div id="id1">
        <a href="javascript:void(view(4,''))">Добавить</a> <br>
        <display:table name="list1" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table1">
            <display:column title="обозначение" class="col1">
                <a href="javascript:void(view(4,'${table1.id}'))">${table1.name}</a>
            </display:column>
            <display:column property="cost" title="себестоимость" maxLength="12" class="col2"/>
            <display:column property="price" title="цена" maxLength="12" class="col2"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table1.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>
    </div>
    <div id="id2">
        <a href="javascript:void(view(5,''))">Добавить</a><br>
        <display:table name="list2" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table2">
            <display:column title="Обозначение" class="col1">
                <a href="javascript:void(view(5,'${table2.id}'))">${table2.name}</a>
            </display:column>
            <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
            <display:column property="price" title="Цена" class="col2" maxLength="8"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table2.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>
    </div>
    <div id="id3">
        <a href="javascript:void(view(6,''))">Добавить</a> <br>
        <display:table name="list3" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table3">
            <display:column title="Обозначение" class="col1">
                <a href="javascript:void(view(6,'${table3.id}'))">${table3.name}</a>
            </display:column>
            <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
            <display:column property="price" title="Цена" class="col2" maxLength="8"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table3.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>
    </div>
    <div id="id4">
        <a href="javascript:void(view(7,''))">Добавить</a><br>
        <display:table name="list4" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table4">
            <display:column title="Обозначение" class="col1">
                <a href="javascript:void(view(7,'${table4.id}'))">${table4.name}</a>
            </display:column>
            <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
            <display:column property="price" title="Цена" class="col2" maxLength="8"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table4.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>
    </div>
    <div id="id5">
        <a href="javascript:void(view(8,''))">Добавить</a> <br>
        <display:table name="list5" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table5">
            <display:column title="Обозначение" class="col1">
                <a href="javascript:void(view(8,'${table5.id}'))">${table5.name}</a>
            </display:column>
            <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
            <display:column property="price" title="Цена" class="col2" maxLength="8"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table5.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>
    </div>
    <div id="id6">
        <a href="javascript:void(view(9,''))">Добавить</a> <br>
        <display:table name="list6" requestURI="priceAction.do?method=redactListPriceForward"
                       excludedParams="method"
                       requestURIcontext="false" pagesize="20" sort="list" class="simple" id="table6">
            <display:column title="Обозначение" class="col1">
                <a href="javascript:void(view(9,'${table6.id}'))">${table6.name}</a>
            </display:column>
            <display:column property="cost" title="Себестоимость" maxLength="8" class="col2"/>
            <display:column property="price" title="Цена" class="col2" maxLength="8"/>
            <display:column title="" class="col13">
                <a href="javascript:  if (confirm('Удалить позицию?'))  self.location.href='priceAction.do?method=deleteListItem&id='+'${table6.id}'">
                    <img src="/Manometr/images/delete.gif" width="18" height="18" hspace="4" border="0"/>
                </a>
            </display:column>
        </display:table>
    </div>


</div>


<div id="dialog-form">
    <p class="validateTips">Все поля должны быть заполненны.</p>


    <fieldset>
        <input type="hidden" name="type" id="type"/>
        <input type="hidden" name="id" id="id"/>
        <label for="name">Название</label>
        <textarea rows="4" cols="50" name="name" id="name"></textarea><br><br>
        <label for="cost">Себестоимость</label>
        <input type="text" name="cost" id="cost" class="text ui-widget-content ui-corner-all"/>
        <label for="price">Цена</label>
        <input type="text" name="price" id="price" class="text ui-widget-content ui-corner-all"/>
    </fieldset>

</div>


</body>
</html>