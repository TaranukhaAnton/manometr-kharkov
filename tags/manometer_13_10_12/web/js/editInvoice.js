new function($) {
    $.fn.getCursorPosition = function() {
        var pos = 0;
        var input = $(this).get(0);
        // IE Support
        if (document.selection) {
            input.focus();
            var sel = document.selection.createRange();
            var selLen = document.selection.createRange().text.length;
            sel.moveStart('character', -input.value.length);
            pos = sel.text.length - selLen;
        }
        // Firefox support
        else if (input.selectionStart || input.selectionStart == '0')
            pos = input.selectionStart;

        return pos;
    }
}(jQuery);

$(function() {

    bodyResize();
    $("#controlDate, #awaitingPayment,#awaitingDelivery").datepicker({
        showOn: 'button',
        buttonImage: 'images/datepicker.jpg',
        buttonImageOnly: true,
        onSelect: function(selectedDate) {
            var id = this.id;
            paramChange(id, 'any');
        }
    });
    $("#date").datepicker({
        showOn: 'button',
        buttonImage: 'images/datepicker.jpg',
        buttonImageOnly: true
    });
    $("#invDate").datepicker({
        showOn: 'button',
        buttonImage: 'images/datepicker.jpg',
        minDate: new Date("01-01-" + $("#year").val()),
        maxDate: new Date("12-31-" + $("#year").val()),
        buttonImageOnly: true
    });
    $("#ui-datepicker-div").css("z-index", 1000000);
    $("#emploer").autocomplete("invoiceAction.do?method=findCustomers", {
        width :260,
        selectFirst :false
    });
    $("#consumer").autocomplete("invoiceAction.do?method=findCustomers", {
        width :260,
        selectFirst :false
    });
    $("#copyInvoice_form input[name='consumer']").autocomplete("invoiceAction.do?method=findCustomers", {
        width :260,
        selectFirst :false
    });
    $("#copyInvoice_form input[name='emploer']").autocomplete("invoiceAction.do?method=findCustomers", {
        width :260,
        selectFirst :false
    });
    $("#copyInvoice_form input[name='date']").datepicker({
        showOn: 'button',
        buttonImage: 'images/datepicker.jpg',
        buttonImageOnly: true
    });
    $("#consumer").result(function(event, data, formatted) {
        $("#consumer").removeClass('ui-state-error');
        $.post("invoiceAction.do?method=editInvoiceParams", { "param":"consumer","value" : formatted, "id":$("#id").val()}, function(data) {

        });

    });
    $("#emploer").result(function(event, data, formatted) {
        $("#emploer").removeClass('ui-state-error');
        $.post("invoiceAction.do?method=editInvoiceParams", {"param":"emploer","value"  : formatted, "id":$("#id").val()}, function(data) {

        });

    });
    $("#invFromKP").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        resizable:false,
        buttons: {

            'Создать': function() {
                $('#number').removeClass("error");
                $('#numberModifier').removeClass("error");
                $('#date').removeClass("error");
                $('#consumer_invFromKP').val($('#consumer').val());
                $('#emploer_invFromKP').val($('#emploer').val());

                //                        $.post("invoiceAction.do?method=verifyInvoicePresence", {"isInvoice" : $('[name=isInvoice]:checked').val(), "number":$('#number').val(), "numberModifier":$('#numberModifier').val(), "date":$('#date').val() }, function(data) {
                $.post("invoiceAction.do?method=verifyInvoicePresence", $('#invFromKP_form').serialize(), function(data) {
                    //  alert(data);

                    if (data.length > 0) {

                        var response = eval("(" + data + ")");
                        if (response.correct) {
                            // alert("invoiceAction.do?method=copyInvoice&" + $('#invFromKP_form').serialize());
                            location.replace("invoiceAction.do?method=invFromKp&" + $('#invFromKP_form').serialize());
                        } else {
                            alert(response.mes);
                            if (!response.presence) {

                                $('#number_invFromKP').addClass("error");
                                $('#numberModifier_invFromKP').addClass("error");
                                $('#date_invFromKP').addClass("error");
                            }

                        }
                    }
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
    $("#addProduction-dialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        resizable:false,
        buttons: {

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
    $("#commonPercent-dialog").dialog({
        autoOpen: false,
        height: 200,
        width: 300,
        modal: true,
        resizable:false,
        buttons: {
            'Применить': function() {

                if (checkRegexp($("#commonPercent"), 'float', true)) {

                   // alert(this);
                    $.post("invoiceAction.do?method=editInvoiceParams", {"param":"commonPercent","value"  :$("#commonPercent").val() , "id":$("#id").val()}, function(data) {
                        if (data.length > 0) {
                            var response = eval("(" + data + ")");
                            for (I = 0; I < response.items.length; I++) {
                                $("#" + response.items[I].id).val(response.items[I].value);
                            }
                            $("#sum").text(response.sum);
                            $("#NDSPayment").text(response.nds);
                            $("#total").text(response.total);
                        }
                        $("#commonPercent-dialog").dialog('close');
                    });
                }
            },
            'Отмена': function() {
                $(this).dialog('close');
            }
        },
        open: function(event, ui) {

            document.getElementById("commonPercent").focus();
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close: function(event, ui) {
            $('body').css('overflow', 'auto');
        }
    });
    $("#commonTransportCost-dialog").dialog({
        autoOpen: false,
        height: 200,
        width: 300,
        modal: true,
        resizable:false,
        buttons: {
            'Применить': function() {
                if (checkRegexp($("#commonTransportCost"), 'float', true)) {


                    $.post("invoiceAction.do?method=editInvoiceParams", {"param":"commonTransportCost","value"  :$("#commonTransportCost").val() , "id":$("#id").val()}, function(data) {
                        if (data.length > 0) {
                            var response = eval("(" + data + ")");
                            for (I = 0; I < response.items.length; I++) {
                                $("#" + response.items[I].id).val(response.items[I].value);
                            }
                            $("#sum").text(response.sum);
                            $("#NDSPayment").text(response.nds);
                            $("#total").text(response.total);
                        }
                        $("#commonTransportCost-dialog").dialog('close');
                    });
                }
            },
            'Отмена': function() {
                $(this).dialog('close');
            }
        },
        open: function(event, ui) {
            document.getElementById("commonTransportCost").focus();
            $('body').css('overflow', 'hidden');
            $('.ui-widget-overlay').css('width', '100%');
        },
        close: function(event, ui) {
            $('body').css('overflow', 'auto');
        }
    });
    $("#roundPrice-dialog").dialog({
        autoOpen: false,
        height: 300,
        width: 200,
        modal: true,
        resizable:false,
        buttons: {

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
    $("#print-dialog").dialog({
        autoOpen: false,
        height: 300,
        width: 200,
        modal: true,
        resizable:false,
        buttons: {

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
    $("#bunChanges-dialog").dialog({
        autoOpen: false,
        height: 200,
        width: 350,
        modal: true,
        resizable:false,
        buttons: {
            'Запрет изменений': function() {
                $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "date", "value":$("#invDate").val(), "id":$("#id").val()}, function(data) {
                    $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "currentState", "value":1, "id":$("#id").val()}, function(data) {
                        window.location.reload(true);
                    });
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
            $("#invDate").datepicker("hide");
            $('body').css('overflow', 'auto');
        }
    });
    $("#order-dialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        resizable:false,
        buttons: {
            'Создать': function() {

                $.post("invoiceAction.do?method=verifyOrderPresence", {"isInvoice" : $('[name=isInvoice]:checked').val(), "number":$('#number').val(), "numberModifier":$('#numberModifier').val(), "date":$('#date').val() }, function(data) {
                    if (data.length > 0) {


                        if (data == 'true') {
                            var str = "invoiceAction.do?method=openOrder";
                            str += "&invoceId=" + $('#id').val();
                            str += "&number=" + $('#number').val();
                            str += "&numberModifier=" + $('#numberModifier').val();
                            str += "&date=" + $('#date').val();

                            location.replace(str);
                        } else {
                            alert("Такой заказ-наряд уже существует");
                        }
                    }

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
    $("#copyInvoice-dialog").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        resizable:false,
        buttons: {
            'Создать': function() {
                $("#copyInvoice_form input[name='number']").removeClass("error");
                $("#copyInvoice_form input[name='numberModifier']").removeClass("error");
                $("#copyInvoice_form input[name='date']").removeClass("error");
                $("#copyInvoice_form input[name='consumer']").removeClass("error");
                $("#copyInvoice_form input[name='emploer']").removeClass("error");

                //                        $.post("invoiceAction.do?method=verifyInvoicePresence", {"isInvoice" : $('[name=isInvoice]:checked').val(), "number":$('#number').val(), "numberModifier":$('#numberModifier').val(), "date":$('#date').val() }, function(data) {
                $.post("invoiceAction.do?method=verifyInvoicePresence", $('#copyInvoice_form').serialize(), function(data) {
                    if (data.length > 0) {
                        var response = eval("(" + data + ")");
                        if (response.correct) {
                          //  alert($('#copyInvoice_form').serialize());
                            location.replace("invoiceAction.do?method=copyInvoice&" + $('#copyInvoice_form').serialize());
                        } else {
                            alert(response.mes);
                            if (!response.presence) {
                                $("#copyInvoice_form input[name='number']").addClass("error");
                                $("#copyInvoice_form input[name='numberModifier']").addClass("error");
                                $("#copyInvoice_form input[name='date']").addClass("error");
                            }
                            if (!response.emploer) {
                                $("#copyInvoice_form input[name='emploer']").addClass("error");
                            }
                            if (!response.consumer) {
                                $("#copyInvoice_form input[name='consumer']").addClass("error");
                            }
                        }
                    }
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


});


function checkRegexp(o, regexp, empty) {
    var exp = {float:/^\s*\d+(?:[\.,]\d+)?\s*$/, dig:/^\s*\d+\s*$/, any:/^/, currency:/^[0-9\s]+[\.,]?[0-9]{0,2}$/};
    o.removeClass('ui-state-error');

    //   alert(exp[regexp]);
    if ((empty) & (o.val().length == 0))  return false;

    var value = o.val().replace(/\s+/g, "").replace(String.fromCharCode(160), "");

    if (exp[regexp].test(value)) {
        //    if ( exp[regexp].test(o.val())) {
        return true;
    } else {
        o.addClass('ui-state-error');
        return false;

    }

}


function customerSetup(data) {

    if (data.length > 0) {
        var response = eval("(" + data + ")");


        if (response.res == 'error') {
            $("#" + response.param).addClass('ui-state-error');


        }


    }
}


function paramChange(param, type, func) {
    if ($("#" + param).attr("onkeypressEn") == 'true') {
        $("#" + param).attr("onkeypressEn", "false");
        setTimeout(function() {
            $("#" + param).attr("onkeypressEn", "true");
            if (checkRegexp($("#" + param), type)) {
                var val = $("#" + param).val();

                if (type != "any") {
                    val = val.replace(/\s+/, "").replace(String.fromCharCode(160), "");
                }
                var mes = "param=" + param + "&value=" + val + "&id=" + $("#id").val();
                $.post("invoiceAction.do?method=editInvoiceParams", mes, func);
            }
        }, 2000);

    }


}


function printIvoice(type, id) {

    location.replace("invoiceAction.do?method=downloadArch&invoiceId=" + id + "&type=" + type);
    $('#print-dialog').dialog('close');
}

function exchangeRateChange(data) {
    if (data.length > 0) {
        var response = eval("(" + data + ")");
        for (I = 0; I < response.items.length; I++) {
            $("#" + response.items[I].id).val(response.items[I].value);
        }
        $("#sum").text(response.sum);
        $("#NDSPayment").text(response.nds);
        $("#total").text(response.total);
    }
}

function supplierChange(data) {
    if (data.length > 0) {
        var response = eval("(" + data + ")");
        $("#currency").text(response.currency);
        $("#currencyText").text("грн. за 1 " + response.currency);
        $("#exchangeRate").val(response.exchangeRate);

        if (response.isNative == 'true') {
            document.getElementById("currencyText").style.visibility = "hidden";
            document.getElementById("exchangeRate").style.visibility = "hidden";

            //         $("#currencyText").style.val
        } else {
            document.getElementById("currencyText").style.visibility = "visible";
            document.getElementById("exchangeRate").style.visibility = "visible";
        }

        //


        for (I = 0; I < response.items.length; I++) {
            $("#" + response.items[I].id).val(response.items[I].value);
        }
        $("#sum").text(response.sum);
        $("#NDSPayment").text(response.nds);
        $("#total").text(response.total);
    }
}


function invoiceItemChange(param, itemId, type, invoiceId) {

    if ($("#" + param + itemId).attr("onkeypressEn") == 'true') {
        //      alert("qq")
        $("#" + param + itemId).attr("onkeypressEn", "false");
        setTimeout(function() {
            var element = document.getElementById(param + itemId);
            var caretPos = $("#" + param + itemId).getCursorPosition();

            $("#" + param + itemId).attr("onkeypressEn", "true");
            var val = $("#" + param + itemId).val().replace(/\s+/g, "").replace(String.fromCharCode(160), "");
            var it = $("#" + param + itemId);
            var exchangeRate = $("#exchangeRate").val().replace(/\s+/g, "").replace(String.fromCharCode(160), "");

            if (checkRegexp(it, type))

                $.post("invoiceAction.do?method=editInvoiceItem", {"param" : param, "value":val, "id":itemId}, function(data) {
                    if (data.length > 0) {
                        //  alert(data);
                        var response = eval("(" + data + ")");
                        $("#quantity" + itemId).val(response.quantity);
                        $("#percent" + itemId).val(response.percent);
                        $("#sellingPrice" + itemId).val(response.sellingPrice);
                        $("#sum" + itemId).val(response.sum);
                        $("#deliveryTime" + itemId).val(response.deliveryTime);
                        //   alert(response.additionalCost)
                        $("#transportationCost" + itemId).val(response.transportationCost);
                        $("#additionalCost" + itemId).val(response.additionalCost);


                        $("#sum").text(response.sumTot);
                        $("#NDSPayment").text(response.nds);
                        $("#total").text(response.total);
                        //element.setSelectionRange(caretPos, caretPos);
                        doSetCaretPosition(element, caretPos);
                    }
                });
        }
                , 2000);

    }

}


function banChanges() {

    if ($("#emploer").val() == "") {
        $("#emploer").addClass('ui-state-error');
        alert("поле должно быть заполнено");
        $("#emploer").removeClass('ui-state-error');
        return;
    }

    if ($("#consumer").val() == "") {
        $("#consumer").addClass('ui-state-error');
        if (confirm('Поле должно быть заполнено. \nКопировать данные из поля "Заказчик"?')) {
            var mes = "param=consumer&value=" + $("#emploer").val() + "&id=" + $("#id").val();
            $.post("invoiceAction.do?method=editInvoiceParams", mes);
            $("#consumer").val($("#emploer").val());
            $("#consumer").removeClass('ui-state-error');
        } else {
            $("#consumer").removeClass('ui-state-error');
            return;
        }


    }


    var prepayment = Number($("#prepayment").val().replace(",", "."));
    var paymentOnTheNitice = Number($("#paymentOnTheNitice").val().replace(",", "."));
    var postpay = Number($("#postpay").val().replace(",", "."));
    var daysAfterDelivery = Number($("#daysAfterDelivery").val().replace(",", "."));


    if ((prepayment + paymentOnTheNitice + postpay) != 100) {
        $("#prepayment").addClass('ui-state-error');
        $("#paymentOnTheNitice").addClass('ui-state-error');
        $("#postpay").addClass('ui-state-error');
        alert("Сумма полей должна быть 100");
        $("#prepayment").removeClass('ui-state-error');
        $("#paymentOnTheNitice").removeClass('ui-state-error');
        $("#postpay").removeClass('ui-state-error');
        return;
    }
    if ((postpay != 0) && (daysAfterDelivery == 0)) {
        $("#postpay").addClass('ui-state-error');
        $("#daysAfterDelivery").addClass('ui-state-error');
        alert("необходимо указать количество дней");
        $("#daysAfterDelivery").removeClass('ui-state-error');
        $("#postpay").removeClass('ui-state-error');
        return;
    }
    $("#bunChanges-dialog").dialog('open');


}

function allowChanges() {
    //    var mes = "invoiceAction.do?method=editInvoiceParams&param=currentState&value=0&id=" + $("#id").val();
    //    alert("1");
    //    //    $.post("invoiceAction.do?method=editInvoiceParams", mes);
    //    jQuery.ajax({url:mes, async:false});
    //    alert("2");
    //    window.location.reload(true);
    //    alert("3");

    $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "currentState", "value":0, "id":$("#id").val()}, function(data) {

        window.location.reload(true);
    });

}
//перевести счет в состояние активный
function setActive() {
    $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "currentState", "value":1, "id":$("#id").val()},
          function(data) {
              window.location.reload(true);
          });

}
//перевести счет в состояние активный
function setAnn() {
    $.post("invoiceAction.do?method=checkPossibilityOf", {"param" : "ann", "id":$("#id").val()},
          function(data) {
              var response = eval("(" + data + ")");
              if (response.response == "ok") {
                  $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "currentState", "value":5, "id":$("#id").val()},
                        function(data) {
                            window.location.reload(true);
                        });

              } else {
                  alert(response.response);
              }
          });

}
//перевести счет в состояние отложен
function setOtl() {
    $.post("invoiceAction.do?method=checkPossibilityOf", {"param" : "ann", "id":$("#id").val()},
          function(data) {

              var response = eval("(" + data + ")");
              if (response.response == "ok") {
                  $.post("invoiceAction.do?method=checkPossibilityOf", {"param" : "otl", "id":$("#id").val()},
                        function(data) {

                            var response = eval("(" + data + ")");
                            //  alert(response.response);
                            if (response.response == "ok") {
                                $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "currentState", "value":3, "id":$("#id").val()},
                                      function(data) {
                                          window.location.reload(true);
                                      });
                            } else {
                                alert(response.response);
                            }
                        });

              } else {
                  alert(response.response);
              }
          });

}
//перевести счет в состояние отложен
function setIsp() {

    $.post("invoiceAction.do?method=editInvoiceParams", {"param" : "currentState", "value":7, "id":$("#id").val()},
          function(data) {
              window.location.reload(true);
          });

}

function setRoundPrice(value) {
    $.post("invoiceAction.do?method=editInvoiceParams", {"param":"roundPrice","value"  :value , "id":$("#id").val()}, function(data) {
        if (data.length > 0) {
            var response = eval("(" + data + ")");
            for (I = 0; I < response.items.length; I++) {
                $("#" + response.items[I].id).val(response.items[I].value);
            }
            $("#sum").text(response.sum);
            $("#NDSPayment").text(response.nds);
            $("#total").text(response.total);
        }

    });
    $("#roundPrice-dialog").dialog('close');

}


function sendOnEnter(evt, input) {
    //         alert($("#"+input.id).val())

    var theEvent = evt || window.event;
    var key = theEvent.keyCode || theEvent.which;
    if (key == 13) {

        if (checkRegexp($("#" + input.id), 'float', true)) {

            //
            $.post("invoiceAction.do?method=editInvoiceParams", {"param":input.id,"value"  :$("#" + input.id).val().replace(/\s+/g, "").replace(String.fromCharCode(160), "") , "id":$("#id").val()}, function(data) {
                if (data.length > 0) {
                    var response = eval("(" + data + ")");
                    for (I = 0; I < response.items.length; I++) {
                        $("#" + response.items[I].id).val(response.items[I].value);
                    }
                    $("#sum").text(response.sum);
                    $("#NDSPayment").text(response.nds);
                    $("#total").text(response.total);
                }
                //                $("#percent-dialog")
                $("#" + input.id + "-dialog").dialog('close');
            });
        }
    }
}

//function getCaretPos(obj)
//{
//    obj.focus();
//
//    if (obj.selectionStart) return obj.selectionStart;//Gecko
//    else if (document.selection)//IE
//    {
//        var sel = document.selection.createRange();
//        var clone = sel.duplicate();
//        sel.collapse(true);
//        clone.moveToElementText(obj);
//        clone.setEndPoint('EndToEnd', sel);
//        return clone.text.length;
//    }
//
//    return 0;
//}

//function doGetCaretPosition(oField) {
//
//    // Initialize
//    var iCaretPos = 0;
//
//    // IE Support
//    if (document.selection) {
//
//        // Set focus on the element
//        oField.focus();
//
//        // To get cursor position, get empty selection range
//        var oSel = document.selection.createRange();
//
//        // Move selection start to 0 position
//        oSel.moveStart('character', -oField.value.length);
//
//        // The caret position is selection length
//        iCaretPos = oSel.text.length;
//    }
//
//    // Firefox support
//    else if (oField.selectionStart || oField.selectionStart == '0')
//        iCaretPos = oField.selectionStart;
//
//    // Return results
//    return (iCaretPos);
//}


function doSetCaretPosition(obj, position) {
    if (obj.setSelectionRange) {
        obj.focus();
        obj.setSelectionRange(position, position);
    } else if (obj.createTextRange) {
        var range = obj.createTextRange();
        range.move("character", position);
        range.select();
    } else if (window.getSelection) {

        s = window.getSelection();
        var r1 = document.createRange();


        var walker = document.createTreeWalker(obj, NodeFilter.SHOW_ELEMENT, null, false);
        var p = position;
        var n = obj;

        while (walker.nextNode()) {
            n = walker.currentNode;
            if (p > n.value.length) {
                p -= n.value.length;
            }
            else break;
        }
        n = n.firstChild;
        r1.setStart(n, p);
        r1.setEnd(n, p);

        s.removeAllRanges();
        s.addRange(r1);

    } else if (document.selection) {
        var r1 = document.body.createTextRange();
        r1.moveToElementText(obj);
        r1.setEndPoint("EndToEnd", r1);
        r1.moveStart('character', position);
        r1.moveEnd('character', position - obj.innerText.length);
        r1.select();
    }
}


function digInput(evt) {
    var theEvent = evt || window.event;
    var key = theEvent.keyCode || theEvent.which;
    if ((key == 8) | (key == 37) | (key == 39) | (key == 46)) return true;
    key = String.fromCharCode(key);
    return /\d|\.|,|\s/.test(key);
}

function changePurpose(input) {
    $.post("invoiceAction.do?method=editInvoiceParams",
    {"param":input.id,"value"  :$("#" + input.id).val() , "id":$("#id").val()},
          function(data) {
              window.location.reload(true);
          });
}

function bodyResize() {
    //    var winHeight = window.innerHeight;
    var winHeight = $("body").height();
    var IICheight = (winHeight < 600) ? 50 : winHeight - 389 - $("#toptable").height();
    var sc = (winHeight < 600) ? "visible" : "hidden";
    $("body").css("overflow-y", sc);
    //    var text = "winHeihgt = " + winHeight;
    //    text += "   topTableHeight = " + $("#toptable").height();
    //    text += "   contentTDHeight = " + $("#contentTD").height();
    //    text += "  bodyHeight = " + $("body").height();
    //    text += "  htmlHeight = " + $("html").height();
    //
    //
    //    $("#console").text(text);
    $("#invItemsContent").css("height", IICheight + "px");

}
