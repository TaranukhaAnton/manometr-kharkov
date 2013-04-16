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
                var mes = "param=" + param + "&value=" + val + "&invoice_id=" + $("#invoice_id").val();
                $.post("editBookingParams", mes, func);
            }
        }, 2000);

    }


}

function printBooking(type, id) {
    location.replace("./export_report?invoice_id=" + id + "&type=" + type);
    $('#print-dialog').dialog('close');
}

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

function banChanges() {
    $("#bunChanges-dialog").dialog('open');
}