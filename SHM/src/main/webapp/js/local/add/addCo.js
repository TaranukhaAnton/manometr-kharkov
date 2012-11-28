var LoLimit;
var HiLimit;
var limits = [0.00004, 0.000063, 0.00010, 0.00016,  0.00025 ,  0.00040 ,  0.00063 ,  0.001 ,  0.0016 ,  0.0025 ,  0.004 ,  0.0063 ,  0.01 ,  0.016 ,  0.025 ,  0.04 ,  0.063 ,  0.100 ,  0.160 ,  0.250 ,  0.400 ,0.630 ,1 ,1.6 ,2.5 ,4 ,6.3,10 ,  16 ,  25 ,  40 ,  63 ,  100 ];
var koef = [1000,1,10,100000,1000,1,10,100000,10,10000];
var ed_iz = ["кПа","МПа","кгс/см &sup2;","кгс/м &sup2;","kPa","MPa","kgf/sm &sup2;","kgf/m &sup2;","bar","mbar"];


$().ready(
        function() {
            var invoiceItemId = $("#invoice_item_id").val().replace(/\s+/, "");
            if (invoiceItemId != 'null')
            {
                $.post("get_ii", {"invoice_item_id":invoiceItemId},
                        function(data) {
                            parseResults(data.modelDescription);
                            var  model = data.modelDescription;
                            var  item = data.pressureSensor;
                            $("input:radio[value='" + item.model + "']")[0].checked = true;
                            $("#beforeSpec").val(item.preamble);
                            $("#afterSpec").val(item.afterSpec);
                            $("input[name='typeTxt'][value='" + item.type + "']")[0].checked = true;
                            $("input[name='klim'][value='" + item.klim + "']")[0].checked = true;
                            $("input[name='isp'][value='" + item.isp + "']")[0].checked = true;
                            $("input[name='mat'][value='" + item.mat + "']")[0].checked = true;
                            $("input[name='err'][value='" + item.error + "']")[0].checked = true;
                            $('input[name="ed_izm"]')[item.ed_izm].checked = true;
                            if (model.staticPressures.length != 0) {
                                $('input[name="stat"]')[item.stat].checked = true;
                            }
                            $('input[name="out"]')[item.outType].checked = true;
                            if (model.du.length != 0) {
                                $('input[name="du"]')[item.du].checked = true;
                            }
                            $("#kmch").find("option[value='" + item.kmch + "']").attr("selected", "selected");

                            if (item.HIM)
                                $("#him").attr('checked', 'checked');
                            if (item.i)
                                $("#i").attr('checked', 'checked');
                            if (item.r)
                                $("#r").attr('checked', 'checked');
                            if (item.p)
                                $("#p").attr('checked', 'checked');
                            if (item.PI)
                                $("#PI").attr('checked', 'checked');
                            if (item.VM)
                                $("#vm").attr('checked', 'checked');
                            if (item.TU)
                                $("#tu").attr('checked', 'checked');

                            LoLimit = Number(model.loLimit);
                            HiLimit = Number(model.hiLimit);
                           var n = item.ed_izm; //(LoLimit < 21) ? 0 : 1;
                            //    document.mainForm.ed_izm[n].checked = true;

                            var objSel = document.mainForm.hid;
                            objSel.options.length = 0;
                            objSel.options[objSel.options.length] = new Option("", "0", true, false);
                            for (var count = LoLimit - 1; count < HiLimit; count++) {
                                objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
                            }


                            if (item.lowLimit == null)
                            {

                                var elem = $("#hid").find("option[value='" + item.hiLimit + "']");
                                if (elem.length == 0) {
                                    $("#hi").val(item.hiLimit);
                                } else {
                                    elem.attr("selected", "selected");
                                }


                            } else
                            {
                                $("#hi").val(item.hiLimit);
                                $("#low").val(item.lowLimit);
                            }

//                            }
                        });
            }
        });




function setupFields(model, item)
{
    var txt = "";
    var s = "";

    txt = " <fieldset> <legend>Исп.</legend> ";
    for (i = 0; i < model.isp.length; i++)
        txt += '<input type="radio" ' + ((item.isp == model.isp[i].type) ? "checked" : "" ) + ' name="isp' + '" value="' + model.isp[i].type + '">&nbsp;' + model.isp[i].text + '<br/>';
    txt += "  </fieldset> ";
    //    document.getElementById("ispolnenie").innerHTML = txt;
    $("#ispolnenie").html(txt);

    txt = " <fieldset> <legend>Мат.</legend> ";
    for (i = 0; i < model.mat.length; i++)
        txt += '<input type="radio" ' + ((item.mat == model.mat[i].type) ? "checked" : "" ) + ' name="mat" value="' + model.mat[i].type + '">&nbsp;' + model.mat[i].text + '<br/>';
    txt += "  </fieldset> ";
    $("#materials").html(txt);


    document.getElementById("klimat").style.visibility = "visible";
    //################################################
    txt = " <fieldset> <legend>Погр.</legend> ";

    for (i = 0; i < model.err.length; i++) {
        //        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + ((item.error == model.err[i].type) ? "checked" : "" ) + ' name="err" value="' + model.err[i].type + '">&nbsp;' + model.err[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    $("#errors").html(txt);
    //################################################
    document.getElementById("ed_izm").style.visibility = "visible";

    $('input[name="ed_izm"]')[item.ed_izm].checked = true;


    //################################################
    if (model.stat == "") {
        document.getElementById("stat").innerHTML = "";
    }
    else {
        txt = " <fieldset> <legend>Стат.</legend> ";
        for (i = 0; i < model.stat.length; i++) {
            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio" ' + ((item.stat == model.stat[i].type) ? "checked" : "" ) + ' name="stat" value="' + model.stat[i].type + '">&nbsp;' + model.stat[i].text + '<br/>';
        }
        txt += "  </fieldset> ";
        $("#stat").html(txt);
    }
    //################################################
    txt = " <fieldset> <legend>вых</legend> ";
    for (i = 0; i < model.out.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio"  ' + ((item.outType == model.out[i].type) ? "checked" : "" ) + ' name="out" value="' + model.out[i].type + '">&nbsp;' + model.out[i].text + '<br/>';
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
            txt += '<input type="radio" ' + ((item.du == model.du[i].type) ? "checked" : "" ) + ' name="du" value="' + model.du[i].type + '">&nbsp;' + model.du[i].text + '<br/>';
        }
        txt += "  </fieldset> ";
        $("#du").html(txt);
    }
    //################################################


    objSel = document.mainForm.kmch;
    objSel.options.length = 0;
    for (i = 0; i < model.kmch.length; i++)
        objSel.options[objSel.options.length] = new Option(model.kmch[i].text, model.kmch[i].type, false, model.kmch[i].type == item.kmch);
    //################################################

    LoLimit = Number(model.lolimit);
    HiLimit = Number(model.hilimit);
    var n = item.ed_izm; //(LoLimit < 21) ? 0 : 1;
    //    document.mainForm.ed_izm[n].checked = true;

    objSel = document.mainForm.hid;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (var count = LoLimit - 1; count < HiLimit; count++) {
        objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
    }


    if (item.lowLimit == null)
    {

        var elem = $("#hid").find("option[value='" + item.hiLimit + "']"); //  $("option[@value=10]").length) ;
        if (elem.length == 0) {
            $("#hi").val(item.hiLimit);
        } else {
            elem.attr("selected", "selected");
        }


    } else
    {
        $("#hi").val(item.hiLimit);
        $("#low").val(item.lowLimit);
    }


    document.mainForm.vm.disabled = model.vm != "true";

    if (!!item.HIM)
        $("#him").attr('checked', 'checked');
    if (!!item.i)
        $("#i").attr('checked', 'checked');
    if (!!item.r)
        $("#r").attr('checked', 'checked');
    if (!!item.p)
        $("#p").attr('checked', 'checked');
    if (!!item.PI)
        $("#PI").attr('checked', 'checked');
    if (!!item.VM)
        $("#vm").attr('checked', 'checked');
    if (!!item.TU)
        $("#tu").attr('checked', 'checked');

}


function parseResults(data) {
   var MAT = new Array("01", "02", "05", "07", "09", "11", "12");
   var ERR = new Array("0.1", "0.15", "0.25", "0.5", "1");
   var STAT = new Array("", "0.16", "0.25", "1.6", "2.5", "4.0", "10", "16", "25", "32", "40");
   var DU = new  Array("50", "80");
   var OUT = new Array("42", "24", "&#8730 42", "05", "50", "&#8730 05");
   var ISP = new Array("общ.", "вн", "Ex", "AC", "К");
   var KLIM3 = new Array("УХЛ3.1*", "У2*", "Т3*");


    //   alert(xmlHttp.responseText);
   //   var response = eval("(" + xmlHttp.responseText + ")");
   // var txt = "";
    var s = "";
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    txt = " <fieldset> <legend>Исп.</legend> ";
    for (i = 0; i < data.isp.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + s + ' name="isp' + '" value="' + data.isp[i] + '">&nbsp;' + ISP[data.isp[i]] + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("ispolnenie").innerHTML = txt;
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    txt = " <fieldset> <legend>Мат.</legend> ";
    for (i = 0; i < data.materials.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + s + ' name="mat" value="' + data.materials[i] + '">&nbsp;' + MAT[data.materials[i]] + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("materials").innerHTML = txt;
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    document.getElementById("klimat").style.visibility = "visible";
    document.getElementById("ed_izm").style.visibility = "visible";


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    txt = " <fieldset> <legend>Погр.</legend> ";
    for (i = 0; i < data.errors.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + s + ' name="err" value="' + data.errors[i] + '">&nbsp;' + ERR[data.errors[i]] + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("errors").innerHTML = txt;
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    if (data.staticPressures == "") {
        document.getElementById("stat").innerHTML = "";
    }
    else {
        txt = " <fieldset> <legend>Стат.</legend> ";
        for (i = 0; i < data.staticPressures.length; i++) {
            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio" ' + s + ' name="stat" value="' + data.staticPressures[i] + '">&nbsp;' + STAT[data.staticPressures[i]] + '<br/>';
        }
        txt += "  </fieldset> ";
        document.getElementById("stat").innerHTML = txt;
    }
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    txt = " <fieldset> <legend>вых</legend> ";
    for (i = 0; i < data.outputs.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio"  ' + s + ' name="out" value="' + data.outputs[i] + '">&nbsp;' + OUT[data.outputs[i]] + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("output").innerHTML = txt;
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    if (data.du == "") {
        document.getElementById("du").innerHTML = "";
    }
    else {
        txt = " <fieldset> <legend>Ду</legend> ";
        for (i = 0; i < data.du.length; i++) {
            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio" ' + s + ' name="du" value="' + data.du[i] + '">&nbsp;' + DU[data.du[i]] + '<br/>';
        }
        txt += "  </fieldset> ";
        document.getElementById("du").innerHTML = txt;
    }
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    objSel = document.mainForm.kmch;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option('','', false, false); // todo
    for (i = 0; i < data.kmch.length; i++)
        objSel.options[objSel.options.length] = new Option('H'+data.kmch[i],data.kmch[i], false, false); // todo


    var  LoLimit = Number(data.loLimit);
    var  HiLimit = Number(data.hiLimit);
    var n = (LoLimit < 21) ? 0 : 1;
    document.mainForm.ed_izm[n].checked = true;

    objSel = document.mainForm.hid;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (var count = LoLimit - 1; count < HiLimit; count++) {
        objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
    }

    document.mainForm.vm.disabled = data.vm != "true";


}

function changeEdIzm()
{

    var n = $("input[name='ed_izm']:checked").val();
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


function test()
{
    var queryString = "model=" + $('input[name=model]:checked').val();
    $.post("../compatibility/get_md", queryString, parseResults);
}

function validate()
{
    var model = $("input[name='model']:checked").val();
    var isp = $("input[name='isp']:checked").val();
    var mat = $("input[name='mat']:checked").val();
    if (model == undefined)
    {
        alert("Выберите модель.");
        return false;
    }


    if (document.mainForm.hid.selectedIndex == 0)
    {
        var isDiv = model.charAt(1) == '3';
        var lowTxt = document.mainForm.low.value.replace(",", ".");
        var hiTxt = document.mainForm.hi.value.replace(",", ".");
        var low = Number(lowTxt);
        var hi = Number(hiTxt);
        var n = $("input[name='ed_izm']:checked").val();  //единицы измерения.


        if (isDiv) {
            if ((lowTxt.length == 0) || (isNaN(Number(low))) || (hiTxt.length == 0) || (isNaN(hi)))
            {
                alert("ошибка ввода пределов");
                return false;
            }
            if ((hi < 0) || (low > 0))
            {
                alert("Недопустимый предел измерений");
                return false;
            }
            if (low < (-0.1 * koef[n])) {
                alert("Недопустимый предел измерений.\n" +
                      "Нижний предел должен быть больше -100кПа.");
                return false;
            }

            if (limits[HiLimit - 1] * koef[n] < (hi - low)) {
                alert("Недопустимый предел измерений. \n" +
                      "Слишком большой диапазон");
                return false;
            }
            if (limits[LoLimit - 1] * koef[n] > (hi - low)) {
                alert("Недопустимый предел измерений. \n" +
                      "Слишком маленький диапазон");
                return false;
            }
        }
        else
        {
            if ((hiTxt.length == 0) || (isNaN(Number(lowTxt))) || (isNaN(Number(hiTxt))))
            {
                alert("ошибка ввода пределов");
                return false;
            }
            if ((model.charAt(1) != '4') && (low < 0))
            {
                alert("Недопустимый предел измерений.\n" +
                      "Нижний предел должен быть больше нуля.");
                return false;
            }
            if ((hi <= 0) || (hi < low)) {
                alert("Недопустимый предел измерений.");
                return false;
            }
            if ((hi - low) < (limits[LoLimit - 1] * koef[n])) {
                alert("Недопустимый предел измерений. \n" +
                      "Слишком маленький диапазон");
                return false;
            }

            if ((hi - low) > (limits[HiLimit - 1] * koef[n])) {
                alert("Недопустимый предел измерений.");
                return false;
            }
            if (hi > (limits[HiLimit - 1] * koef[n])) {
                alert("Недопустимый предел измерений.");
                return false;
            }
        }


    }

    if (($("input[name='out']:checked").val() > 2) && (isp == 2))
    {
        alert("Исполнение Ex несовместимо с \n" +
              "выбраным выходным сигналом  ");
        return false;
    }
    if ((document.mainForm.r.checked) && (isp == 1))
    {
        alert("\"Вн\" несовместимо с \"Р\"");
        return false;
    }
    if ((mat != 1) && (isp == 4))
    {
        alert("Исполнение \"К\" совместимо только \n" +
              "с \"02\" материалом.");
        return false;
    }
    if (((mat < 1) || (mat > 4 )) && (document.mainForm.him.checked))
    {
        alert("\"Хим\" совместимо только \n" +
              "с \"02\",\"05\",\"07\",\"09\" материалами.");
        return false;
    }
    if ((document.mainForm.pi.checked) && (document.mainForm.i.checked))
    {
        alert("\"И\" и \"ПИ\" несовместимо.");
        return false;
    }

    var strUrl = "../matrix_price/isAvailable?" +
                 "model=" + model + "&klim=" + $("input[name='klim']:checked").val() + "&isp=" + isp + "&mat=" + mat
            + "&err=" + $("input[name='err']:checked").val();
    var retObj;

    jQuery.ajax({
        url:strUrl, success:function(obj) {
            retObj = obj;
        }, async:false
    });

    if (retObj.available) {
        return true;
    }
         else
    {
        alert("Эта позиция временно не доступна. (Цена равно нулю)");
        //    ;
        return false;

    }
}


function cancel()
{
    var str = "../invoices/view?id=" + $('#invoice_id').val();
    location.replace(str);
}

