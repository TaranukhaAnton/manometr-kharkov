$(function() {
    var row = $("#invoice tr:eq(0) ");
    $(row).clone().insertAfter($(row));

    $('#invoice tr:eq(0) th:eq(8)').remove();
    $('#invoice tr:eq(0) th:eq(8)').remove();
    $('#invoice tr:eq(0) th:eq(8)').remove();
    $('#invoice tr:eq(0) th:eq(8)').remove();
    $('#invoice tr:eq(0) th:eq(8)').remove();
    //        $('#invoice tr:eq(0) th:eq(8)').remove();
    $('#invoice tr:eq(0) th:eq(7)').attr("colspan", 6);
    $('#invoice tr:eq(0) th:eq(7)').text("продукция");

    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(0)').remove();
    $('#invoice tr:eq(1) th:eq(6)').remove();
    $('#invoice tr:eq(1) th:eq(6)').remove();
    $('#invoice tr:eq(1) th:eq(6)').remove();
    $('#invoice tr:eq(1) th:eq(6)').remove();
    $('#invoice tr:eq(1) th:eq(6)').remove();
    $('#invoice tr:eq(1) th:eq(6)').remove();

    $('#invoice tr:eq(0) th:eq(0)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(1)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(2)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(3)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(4)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(5)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(6)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(8)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(9)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(10)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(11)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(12)').attr("rowspan", 2);
    $('#invoice tr:eq(0) th:eq(13)').attr("rowspan", 2);


    $("#date").datepicker({
//        showOn: 'button',
//        buttonImage: '../images/datepicker.jpg',
//        buttonImageOnly: true
    });
    var dates = $("#f2_from, #f2_to").datepicker({
        showOn: 'button',
        buttonImage: '../images/datepicker.jpg',
        buttonImageOnly: true,
        onSelect: function(selectedDate) {
            var option = this.id == "f2_from" ? "minDate" : "maxDate";
            var instance = $(this).data("datepicker");
            var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });


    $("#consumer").autocomplete(
         {
             source:"../customers/listCustomers",
            width:260,
            selectFirst:false
        });

    $("#employer").autocomplete(
         {
            source:"../customers/listCustomers",
            width:260,
            selectFirst:false
        });


    $("#ui-datepicker-div").css("z-index", 1000000); //задаем z-index
//    $("#ui-id-1").css("z-index", 1000000); //задаем z-index
//    $("#ui-id-2").css("z-index", 1000000); //задаем z-index
//    $("#newInvoice-div").css("overflow", "hidden");
    $("#newInvoice-div").dialog({
        autoOpen: false,
        height: 400,
        width: 450,
        modal: true,
        resizable:false,
        buttons: {
            'Создать': function() {
                $('#number').removeClass("error");
                $('#numberModifier').removeClass("error");
                $('#date').removeClass("error");
                $('#consumer').removeClass("error");
                $('#employer').removeClass("error");

                         $.post("verifyInvoicePresence", $('#newInvoice_form').serialize(),
                             function(data) {
//                $.post("add", $('#newInvoice_form').serialize(), function(data) {
                  //  if (data.length > 0) {
                   //     var response = eval("(" + data + ")");
                        if (data.correct) {
                            location.replace("add?" + $('#newInvoice_form').serialize());
                        } else {
                            alert(data.mes);
                            if (data.presence) {
                                $('#number').addClass("error");
                                $('#numberModifier').addClass("error");
                                $('#date').addClass("error");
                            }
                            if (!data.employer) {
                                $('#employer').addClass("error");
                            }
                            if (!data.consumer) {
                                $('#consumer').addClass("error");
                            }
                        }
                   // }
                });


            },
            'Отмена': function() {
                $(this).dialog('close');
            }
        },
        open: function(event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close: function(event, ui) {
            $('body').css('overflow', 'auto');
        }
    });
    $("#filter-form").dialog({
        autoOpen: false,
        height: 550,
        width: 600,
        modal: true,
        resizable:false,
        buttons: {
            'Применить': function() {
                location.replace("invoiceAction.do?method=setupFilter&" + $('#filterForm').serialize());
            },
            'Очистить': function() {
                // alert($('#filterForm').serialize());

                $(this).dialog('close');
            },
            'Отмена': function() {
                $(this).dialog('close');
            }
        },
        open: function(event, ui) {
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close: function(event, ui) {
            $('body').css('overflow', 'auto');
        }
    });
    $("#tabs").tabs();

});
function openFilterWindow() {
    $.post("invoiceAction.do?method=getFilter", "", function(data) {
        var response = eval("(" + data + ")");
        $('#filterForm').deserialize(response);
    });
    $('#filter-form').dialog('open');
}

function removeErrorClass()
{
    $('#number').removeClass("error");
    $('#numberModifier').removeClass("error");
    $('#date').removeClass("error");
    $('#emploer').removeClass("error");
    $('#consumer').removeClass("error");
}