$(function () {

    $("#tabs").tabs();
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
        setTimeout(function () {
            tips.removeClass('ui-state-highlight', 1500);
        }, 500);
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
        autoOpen:false,
        height:400,
        width:350,
        modal:true,
        resizable:false,
        buttons:{
            'Применить':function () {
                var bValid = true;
                allFields.removeClass('ui-state-error');
                bValid = bValid && checkRegexp(cost, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                bValid = bValid && checkRegexp(cost, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                bValid = bValid && checkRegexp(price, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                bValid = bValid && checkRegexp(price, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                if (bValid) {


                    $.post("editOption", {"isp":isp.val(), "type":type.val(), "param":param.val(), "cost":cost.val(), "price":price.val()},
                        function (data) {

                            var p = "p" + data.type + "-" + data.isp + "-" + data.param;
                            var c = "c" + data.type + "-" + data.isp + "-" + data.param;


                            var cost =  data.cost+"";
                            if (cost.indexOf(".") == -1) {
                                cost += ".00";
                            }
                            var price = data.price+"";
                            if (price.indexOf(".") == -1) {
                                price += ".00";
                            }
                            $("#" + p).html(price);
                            $("#" + c).html(cost);
                        });
                    $(this).dialog('close');
                }
            },
            'Отмена':function () {
                $(this).dialog('close');
            }
        },
        close:function () {
            allFields.val('').removeClass('ui-state-error');
        }
    });

});


function view(type, isp, paramLab, param, price, cost) {
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