var limits = [0.00004, 0.000063, 0.00010, 0.00016,  0.00025 ,  0.00040 ,  0.00063 ,  0.001 ,  0.0016 ,  0.0025 ,  0.004 ,  0.0063 ,  0.01 ,  0.016 ,  0.025 ,  0.04 ,  0.063 ,  0.100 ,  0.160 ,  0.250 ,  0.400 ,0.630 ,1 ,1.6 ,2.5 ,4 ,6.3,10 ,  16 ,  25 ,  40 ,  63 ,  100 ];
var koef = [1000,1,10,100000,1000,1,10,100000,10,10000];
var LoLimit;
var HiLimit;


$().ready(
        function() {
            var invoiceItemId = $("#invoiceItemId").val().replace(/\s+/, "");
            if (invoiceItemId != "0")
            {
                $.post("testAction.do?method=getPressureSensor", {"invoiceItemId":invoiceItemId},
                        function(data) {

                            if (data.length > 0) {
                                var response = eval("(" + data + ")");
                                setupFields(response.model, response.item.ed_izm);

                                $("input:radio[value='" + response.item.model + "']")[0].checked = true;
                                $("input[name='isp'][value='" + response.item.isp + "']")[0].checked = true;
                                $("input[name='mat'][value='" + response.item.mat + "']")[0].checked = true;
                                $("input[name='klim'][value='" + response.item.klim + "']")[0].checked = true;
                                $("input[name='err'][value='" + response.item.error + "']")[0].checked = true;
                                $("input[name='out'][value='" + response.item.outType + "']")[0].checked = true;

                                //
                                if (!!response.item.HIM)
                                    $("#him").attr('checked', 'checked');
                                if (!!response.item.VM)
                                    $("#vm").attr('checked', 'checked');

                                if (!!response.item.i)
                                    $("#i").attr('checked', 'checked');

                                if (!!response.item.PI)
                                    $("#pi").attr('checked', 'checked');

                                if (!!response.item.TU)
                                    $("#tu").attr('checked', 'checked');

                                if (response.item.lowLimit == null)
                                {
                                    var elem = $("#hid").find("option[value='" + response.item.hiLimit + "']");
                                    if (elem.length == 0) {

                                        $("#hi").val(response.item.hiLimit);
                                    } else {
                                        elem.attr("selected", "selected");
                                    }


                                } else
                                {
                                    $("#hi").val(response.item.hiLimit);
                                    $("#low").val(response.item.lowLimit);
                                }
                                $("#kmch [value='" + response.item.kmch + "']").attr("selected", "selected");
                                //                                alert($("#kmch [value='"+response.item.kmch+"']").val());


                            }
                        });
            }


        });


function test()
{
    $.post("testAction.do?method=getModelDescription",
    {"model":$("input[@name='model']:checked").val()},
            function(data) {
                if (data.length > 0) {
                    //      alert(data);
                    var response = eval("(" + data + ")");
                    //   $("input:radio[value='" + response.item.model + "']")[0].checked = true;
                    LoLimit = Number(response.lolimit);
                    var n = (LoLimit < 21) ? 0 : 1;
                    setupFields(response, n);

                    //$("form :radio[name=some]")[0].attr("checked", "checked");

                    $("input[name='isp']")[0].checked = true;
                    $("input[name='mat']")[0].checked = true;
                    $("input[name='klim']")[0].checked = true;
                    $("input[name='err']")[0].checked = true;
                    $("input[name='out']")[0].checked = true;
                    $("input[name='stat']")[0].checked = true;


                }
            });
}


function validate()
{
    var model = $("input[@name='model']:checked").val();
    if (model == undefined)
    {
        alert("Выберите модель.");
        return false;
    }

    return true;


}


function setupFields(model, n)
{
    var txt = "";


    txt = " <fieldset> <legend>Исп.</legend> ";
    for (i = 0; i < model.isp.length; i++)
        txt += '<input type="radio"  name="isp' + '" value="' + model.isp[i].type + '">&nbsp;' + model.isp[i].text + '<br/>';
    txt += "  </fieldset> ";
    $("#ispolnenie").html(txt);

    txt = " <fieldset> <legend>Мат.</legend> ";
    for (i = 0; i < model.mat.length; i++)
        txt += '<input type="radio"  name="mat" value="' + model.mat[i].type + '">&nbsp;' + model.mat[i].text + '<br/>';
    txt += "  </fieldset> ";
    $("#materials").html(txt);


    document.getElementById("klimat").style.visibility = "visible";
    //################################################
    txt = " <fieldset> <legend>Погр.</legend> ";

    for (i = 0; i < model.err.length; i++) {
        //        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio"  name="err" value="' + model.err[i].type + '">&nbsp;' + model.err[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    $("#errors").html(txt);
    //################################################
    document.getElementById("ed_izm").style.visibility = "visible";
    if (model.stat == "") {
        document.getElementById("stat").innerHTML = "";
    }
    else {
        txt = " <fieldset> <legend>Стат.</legend> ";
        for (i = 0; i < model.stat.length; i++) {
            //            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio"  name="stat" value="' + model.stat[i].type + '">&nbsp;' + model.stat[i].text + '<br/>';
        }
        txt += "  </fieldset> ";
        $("#stat").html(txt);
    }
    //################################################
    txt = " <fieldset> <legend>вых</legend> ";
    for (i = 0; i < model.out.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio"   name="out" value="' + model.out[i].type + '">&nbsp;' + model.out[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    $("#otput").html(txt);
    //################################################
    if (model.du == "") {
        //        document.getElementById("du").innerHTML = "";
        $("#du").html("");
    }
    else {
        txt = " <fieldset> <legend>Ду</legend> ";
        for (i = 0; i < model.du.length; i++) {
            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio"  name="du" value="' + model.du[i].type + '">&nbsp;' + model.du[i].text + '<br/>';
        }
        txt += "  </fieldset> ";
        $("#du").html(txt);
    }
    LoLimit = Number(model.lolimit);
    HiLimit = Number(model.hilimit);
    document.mainForm.ed_izm[n].checked = true;
    objSel = document.mainForm.hid;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (var count = LoLimit - 1; count < HiLimit; count++) {
        objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
    }


}

function cancel()
{
    var str = "invoiceAction.do?method=viewInvoice&id=" + $('#invoiceId').val();
    location.replace(str);
}

function changeEdIzm()
{

    var n = $("input[@name='ed_izm']:checked").val();
    var i = document.mainForm.hid.selectedIndex;
    var objSel = document.mainForm.hid;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (var count = LoLimit - 1; count < HiLimit; count++) {
        objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
    }
    document.mainForm.hid.options[i].selected = true;

}

function changeLimit()
{
    document.mainForm.low.value = "";
    document.mainForm.hi.value = "";
}

function changeNsLimit()
{
    document.mainForm.hid.options[0].selected = true;
}