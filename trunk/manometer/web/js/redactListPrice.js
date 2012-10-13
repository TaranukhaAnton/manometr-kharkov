$(function () {
    var tabContainers = $('div.tabs > div');
    var saved_tab = parseInt($.cookie('saved_tab')) || 0;

    tabContainers.hide().filter(':eq(' + saved_tab + ')').show();
    $('div.tabs ul.tabNavigation a').click(function () {

        var index = $('div.tabs ul.tabNavigation a').index(this);
        $.cookie('saved_tab', index);
        tabContainers.hide();
        tabContainers.filter(':eq(' + index + ')').show();
        $('div.tabs ul.tabNavigation a').removeClass('selected');
        $(this).addClass('selected');
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
                    $.post("priceAction.do?method=addListItem", {"id":id.val(),"type":type.val(),"name":name.val(),"cost" : cost.val(),"price":price.val()}, function(data) {
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


});


function view(type, id)
{
    if (id)
    {
        $.post("priceAction.do?method=getListItem", {"id" : id}, function(data) {
            if (data.length > 0) {
                var response = eval("(" + data + ")");
                var name = $("#name"),
                        cost = $("#cost"),
                        price = $("#price"),
                        id = $("#id"),
                        type = $("#type");
                id.val(response.id);
                type.val(response.type);
                name.text(decodeURI(response.name));
                price.val(response.price);
                cost.val(response.cost);


                //alert(data);
            }
        });


    } else
    {
        $("#type").val(type);
    }
    $('#dialog-form').dialog('open');

}