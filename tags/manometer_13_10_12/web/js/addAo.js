var xmlHttp;
var LoLimit;
var HiLimit;
var limits = [0.00004, 0.000063, 0.00010, 0.00016,  0.00025 ,  0.00040 ,  0.00063 ,  0.001 ,  0.0016 ,  0.0025 ,  0.004 ,  0.0063 ,  0.01 ,  0.016 ,  0.025 ,  0.04 ,  0.063 ,  0.100 ,  0.160 ,  0.250 ,  0.400 ,0.630 ,1 ,1.6 ,2.5 ,4 ,6.3,10 ,  16 ,  25 ,  40 ,  63 ,  100 ];
var koef = [1000,1,10,100000,1000,1,10,100000,10,10000];
var ed_iz = ["кПа","МПа","кгс/см &sup2;","кгс/м &sup2;","kPa","MPa","kgf/sm &sup2;","kgf/m &sup2;","bar","mbar"];


$().ready(
        function() {
              $("input[name='typeTxt'][value='0']")[0].checked = true;
            var invoiceItemId = $("#invoiceItemId").val().replace(/\s+/, "");
            if (invoiceItemId != "0")
            {
                $.post("testAction.do?method=getPressureSensor", {"invoiceItemId":invoiceItemId},
                        function(data) {
                            if (data.length > 0) {
                                var response = eval("(" + data + ")");
                                setupFields(response.model, response.item.ed_izm);
                                $("input[name='typeTxt'][value='" + response.item.type + "']")[0].checked = true;
                                $("input:radio[value='" + response.item.model + "']")[0].checked = true;
                                $("input[name='isp'][value='" + response.item.isp + "']")[0].checked = true;
                                $("input[name='mat'][value='" + response.item.mat + "']")[0].checked = true;
                                $("input[name='klim'][value='" + response.item.klim + "']")[0].checked = true;
                                $("input[name='err'][value='" + response.item.error + "']")[0].checked = true;
                                $("input[name='out'][value='" + response.item.outType + "']")[0].checked = true;
                                $("#beforeSpec").val(response.item.preamble);
                                $("#afterSpec").val(response.item.afterSpec);

                                var obj = $("input[name='stat'][value='" + response.item.stat + "']");
                                if (obj.length > 0)
                                    obj[0].checked = true;
                               
                                if (!!response.item.HIM)
                                    $("#him").attr('checked', 'checked');
                                if (!!response.item.r)
                                    $("#r").attr('checked', 'checked');
                                if (!!response.item.p)
                                    $("#p").attr('checked', 'checked');
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


    //################################################
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
    //################################################


    objSel = document.mainForm.kmch;
    objSel.options.length = 0;
    for (i = 0; i < model.kmch.length; i++)
        objSel.options[objSel.options.length] = new Option(model.kmch[i].text, model.kmch[i].type, false, false);
    //################################################

    LoLimit = Number(model.lolimit);
    HiLimit = Number(model.hilimit);
    //   var n = (LoLimit < 21) ? 0 : 1;
    document.mainForm.ed_izm[n].checked = true;

    objSel = document.mainForm.hid;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (var count = LoLimit - 1; count < HiLimit; count++) {
        objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
    }


}


function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
}


function handleStateChange() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            parseResults();
        }
    }
}
function parseResults() {

    //    alert(xmlHttp.responseText);
    var response = eval("(" + xmlHttp.responseText + ")");
    var txt = "";
    var s = "";
    txt = " <fieldset> <legend>Исп.</legend> ";
    for (i = 0; i < response.isp.length; i++)
    {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + s + ' name="isp' + '" value="' + response.isp[i].type + '">&nbsp;' + response.isp[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("ispolnenie").innerHTML = txt;


    txt = " <fieldset> <legend>Мат.</legend> ";
    for (i = 0; i < response.mat.length; i++)
    {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + s + ' name="mat" value="' + response.mat[i].type + '">&nbsp;' + response.mat[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("materials").innerHTML = txt;


    document.getElementById("klimat").style.visibility = "visible";


    txt = " <fieldset> <legend>Погр.</legend> ";
    for (i = 0; i < response.err.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio" ' + s + ' name="err" value="' + response.err[i].type + '">&nbsp;' + response.err[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("errors").innerHTML = txt;
    document.getElementById("ed_izm").style.visibility = "visible";
    if (response.stat == "") {
        document.getElementById("stat").innerHTML = "";
    }
    else {
        txt = " <fieldset> <legend>Стат.</legend> ";
        for (i = 0; i < response.stat.length; i++) {
            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio" ' + s + ' name="stat" value="' + response.stat[i].type + '">&nbsp;' + response.stat[i].text + '<br/>';
        }
        txt += "  </fieldset> ";
        document.getElementById("stat").innerHTML = txt;
    }

    txt = " <fieldset> <legend>вых</legend> ";
    for (i = 0; i < response.out.length; i++) {
        s = (i == 0) ? "checked" : "";
        txt += '<input type="radio"  ' + s + ' name="out" value="' + response.out[i].type + '">&nbsp;' + response.out[i].text + '<br/>';
    }
    txt += "  </fieldset> ";
    document.getElementById("otput").innerHTML = txt;
    // alert("finish");

    if (response.du == "") {
        document.getElementById("du").innerHTML = "";
    }
    else {
        txt = " <fieldset> <legend>Ду</legend> ";
        for (i = 0; i < response.du.length; i++) {
            s = (i == 0) ? "checked" : "";
            txt += '<input type="radio" ' + s + ' name="du" value="' + response.du[i].type + '">&nbsp;' + response.du[i].text + '<br/>';
        }
        txt += "  </fieldset> ";
        document.getElementById("du").innerHTML = txt;
    }

    LoLimit = Number(response.lolimit);
    HiLimit = Number(response.hilimit);
    var n = (LoLimit < 21) ? 0 : 1;
    document.mainForm.ed_izm[n].checked = true;

    objSel = document.mainForm.hid;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (var count = LoLimit - 1; count < HiLimit; count++) {
        objSel.options[objSel.options.length] = new Option(limits[count] * koef[n], limits[count] * koef[n], false, false);
    }

    objSel = document.mainForm.kmch;
    objSel.options.length = 0;
    objSel.options[objSel.options.length] = new Option("", "0", true, false);
    for (i = 0; i < response.kmch.length; i++)
        objSel.options[objSel.options.length] = new Option(response.kmch[i].text, response.kmch[i].type, false, false);

}

function test()
{
    createXMLHttpRequest();
    var url = "testAction.do?method=getModelDescription";
    var queryString = "model=" + $("input[@name='model']:checked").val();


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);


}
//function validate()
//{
//    var model = $("input[@name='model']:checked").val();
//    if (model == undefined)
//    {
//        alert("Выберите модель.");
//        return false;
//    }
//
//    return true;
//
//}


function validate()
{
    var model = $("input[@name='model']:checked").val();
    var isp = $("input[@name='isp']:checked").val();
    var mat = $("input[@name='mat']:checked").val();
    if (model == undefined)
    {
        alert("Выберите модель.");
        return false;
    }
     // это если выбран нестандартный предел
    if (document.mainForm.hid.selectedIndex == 0)
    {
        var isDiv = model.charAt(1) == '3';
        var lowTxt = document.mainForm.low.value.replace(",", ".");
        var hiTxt = document.mainForm.hi.value.replace(",", ".");
        var low = Number(lowTxt);
        var hi = Number(hiTxt);
        var n = $("input[@name='ed_izm']:checked").val();  //единицы измерения.


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

    if (($("input[@name='out']:checked").val() > 2) && (isp == 2))
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

//    if ((document.mainForm.pi.checked) && (document.mainForm.i.checked))
//    {
//        alert("\"И\" и \"ПИ\" несовместимо.");
//        return false;
//    }

    var strUrl = "testAction.do?method=checkPressureSensor" +
                 "&model=" + model + "&klim=" + $("input[@name='klim']:checked").val() + "&isp=" + isp + "&mat=" + mat
            + "&err=" + $("input[@name='err']:checked").val();
    var strReturn = "";

    jQuery.ajax({
        url:strUrl, success:function(html) {
            strReturn = html;
        }, async:false
    });

    if (strReturn == "true")
        return true; else
    {
        alert("Эта позиция временно не доступна. (Цена равно нулю)");
        //    ;
        return false;

    }
}














function changeEdIzm()
{

    var n = $("input[@name='ed_izm']:checked").val();
    var i = document.mainForm.hid.selectedIndex;
    objSel = document.mainForm.hid;
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

function cancel()
{
    var str = "invoiceAction.do?method=viewInvoice&id=" + $('#invoiceId').val();
    location.replace(str);
}