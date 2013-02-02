$(function () {

    $("#tabs").tabs({
        cookie:{ expires:1, name:"tabcookie" }
    });

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
        setTimeout(function () {
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
        autoOpen:false,
        height:500,
        width:400,
        modal:true,
        resizable:false,
        buttons:{
            'Применить':function () {
                var bValid = true;
                allFields.removeClass('ui-state-error');
                bValid = bValid && checkLength(name, "username", 5, 500);
                bValid = bValid && checkRegexp(cost, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                bValid = bValid && checkRegexp(cost, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                bValid = bValid && checkRegexp(price, /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/, "неверное число");
                bValid = bValid && checkRegexp(price, /^[0-9]+[\.,]?[0-9]?[0-9]?$/, "много знаков после зпт");
                if (bValid) {
                    tttt = type.val();
                    $.post("add", {"id":id.val(), "type":type.val(), "name":name.val(), "cost":cost.val(), "price":price.val()},
                        function (data) {
                            $("#id" + tttt).html(data);
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

function del(id, type) {


    if (confirm("Удалить позицию?"))
    $.post("delete", {"id":id, "type":type},
        function (data) {
            $("#id" + type).html(data);
        });

//    self.location.href = 'priceAction.do?method=deleteListItem&id=' + id;

}

function view(type, id, name, cost, price) {
    var str;
    $("#type").val(type);
    if (id != undefined) {
        str = "Редактировать";
        $("#id").val(id);
        $("#name").val(name);
        $("#price").val(price);
        $("#cost").val(cost);
    } else {
        str = "Добавить";
    }

    var myArray = new Array();
    myArray[0] = "д.д. специальный";
    myArray[1] = "Блок питания";
    myArray[2] = "измерительный блок";
    myArray[3] = "Диаф. кам.";
    myArray[4] = "прочую продукцию";
    myArray[5] = "вичислитель";
    myArray[6] = "прод. стор. производителя";
    $('#dialog-form').dialog({title:str + " " + myArray[type - 3]});
    $('#dialog-form').dialog('open');
}

