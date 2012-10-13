$(function() {

    var tabContainers = $('div.tabs > div'); // получаем массив контейнеров
    tabContainers.hide();

    // $("#co").show(); // прячем все, кроме первого
    // далее обрабатывается клик по вкладке
    $('div.tabs ul.tabNavigation a').click(function () {
        tabContainers.hide(); // прячем все табы
        tabContainers.filter(this.hash).show(); // показываем содержимое текущего
        $('div.tabs ul.tabNavigation a').removeClass('selected'); // у всех убираем класс 'selected'
        $(this).addClass('selected'); // текушей вкладке добавляем класс 'selected'
        return false;
    }).filter(':first').click();


    //    var tab_cookie_id = parseInt(getCookie("the_tab_cookie"));
    //    $("#tabs").tabs({
    //        selected: tab_cookie_id,
    //        show:     function(e, ui) {
    //            setCookie("the_tab_cookie", ui.index);
    //        }
    //    });
    $("#dialog").dialog("destroy");
    var
            isp = $("#isp"),
            type = $("#type"),
            param = $("#param"),
            cost = $("#cost"),
            price = $("#price"),
            allFields = $([]).add(param).add(cost).add(price).add(isp).add(type),
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
                bValid = bValid && checkRegexp(cost, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                bValid = bValid && checkRegexp(cost, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                bValid = bValid && checkRegexp(price, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                bValid = bValid && checkRegexp(price, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                if (bValid) {
                    $.post("priceAction.do?method=redactOption", {"isp":isp.val(),"type":type.val(),"param":param.val(),"cost" : cost.val(),"price":price.val()},
                            function(data) {

                                if (data.length > 0) {
                                    var response = eval("(" + data + ")");
                                    var p = "p" + response.type + "-" + response.isp + "-" + response.param;
                                    var c = "c" + response.type + "-" + response.isp + "-" + response.param;
                                    $("#" + p).html(response.price);
                                    $("#" + c).html(response.cost);
                                }
                                //                                document.location.reload()
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


function view(type, isp, paramLab, param, price, cost)
{
    var myArray = new Array();
    myArray[0] = "общ";
    myArray[1] = "Вн.";
    myArray[2] = "Ex";
    myArray[3] = "Ac";
    myArray[4] = "K";

    var typeArr = new Array();
    typeArr[0] = "ЦО";
    typeArr[1] = "АО";
    typeArr[2] = "ОП";


    $("#TypeLab").text("Датчик давления " + typeArr[type]);
    $("#IspLab").text("Исполнение " + myArray[isp]);
    $("#ParamLab").text("Редактируемая опция " + paramLab);

    $("#param").val(param);
    $("#isp").val(isp);
    $("#type").val(type);

    var p = "p" + type + "-" + isp + "-" + param;
    var c = "c" + type + "-" + isp + "-" + param;
 //   $("#price").val(price);
  //  $("#cost").val(cost);
    $("#price").val($("#" + p).html().trim());
    $("#cost").val($("#" + c).html().trim());

    $('#dialog-form').dialog('open');

}