$(function () {

    $("#result_dialog").dialog({
        autoOpen:false,
        height:800,
        width:900,
        modal:true,
        resizable:false
    });

    $("#tabs").tabs({
        select:function (event, ui) {
            if (ui.index == 0) {
                firstTabSelected();
            } else if (ui.index == 1) {
                secondTabSelected();
            } else if (ui.index == 2) {
                thirdTabSelected();
            }
        }
    });


});

function firstTabSelected() {
    document.getElementById("materials").innerHTML = '<fieldset> <legend> Материалы  </legend>' +
        ' <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/> ' +
        ' <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>   ' +
        ' <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>  ' +
        ' <input type="checkbox" name="mat" value="3"> &nbsp; - 07 <br/>  ' +
        ' <input type="checkbox" name="mat" value="4"> &nbsp; - 09 <br/>  ' +
        ' <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>  ' +
        ' <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/> ' +
        ' </fieldset>';

    document.getElementById("ispolnenie").innerHTML = '<fieldset><legend>Исполнения</legend>' +
        '<input type="checkbox" name="isp" value="0"> &nbsp; - общ <br/>' +
        '<input type="checkbox" name="isp" value="1"> &nbsp; - Вн <br/>' +
        '<input type="checkbox" name="isp" value="2"> &nbsp; - Ex <br/>' +
        '<input type="checkbox" name="isp" value="3"> &nbsp; - AC <br/>' +
        '<input type="checkbox" name="isp" value="4"> &nbsp; - K <br/> <br/><br/>' +
        '</fieldset>';


    document.getElementById("error").innerHTML = '<fieldset> <legend> Погрешность</legend> ' +
        '<input type="checkbox" name="err" value="0"> &nbsp; - 0,1 <br/> ' +
        '<input type="checkbox" name="err" value="1"> &nbsp; - 0,15 <br/> ' +
        '<input type="checkbox" name="err" value="2"> &nbsp; - 0,25 <br/>' +
        '<input type="checkbox" name="err" value="3"> &nbsp; - 0,5 <br/> ' +
        '<input type="checkbox" name="err" value="4"> &nbsp; - 1,0 <br/><br/><br/>' +
        '</fieldset>';
}
function secondTabSelected() {
    document.getElementById("materials").innerHTML = '<fieldset> <legend> Материалы  </legend>' +
        ' <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/> ' +
        ' <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>   ' +
        ' <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>  ' +
        ' <input type="checkbox" name="mat" value="3"> &nbsp; - 07 <br/>  ' +
        ' <input type="checkbox" name="mat" value="4"> &nbsp; - 09 <br/>  ' +
        ' <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>  ' +
        ' <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/> ' +
        ' </fieldset>';
    document.getElementById("ispolnenie").innerHTML = '<fieldset><legend>Исполнения</legend>' +
        '<input type="checkbox" name="isp" value="0"> &nbsp; - общ <br/>' +
        '<input type="checkbox" name="isp" value="1"> &nbsp; - Вн <br/>' +
        '<input type="checkbox" name="isp" value="2"> &nbsp; - Ex <br/>' +
        '<input type="checkbox" name="isp" value="3"> &nbsp; - AC <br/>' +
        '<input type="checkbox" name="isp" value="4"> &nbsp; - K <br/> <br/><br/>' +
        '</fieldset>';


    document.getElementById("error").innerHTML = '<fieldset> <legend> Погрешность</legend> ' +
        '<input type="checkbox" name="err" value="0"> &nbsp; - 0,1 <br/> ' +
        '<input type="checkbox" name="err" value="1"> &nbsp; - 0,15 <br/> ' +
        '<input type="checkbox" name="err" value="2"> &nbsp; - 0,25 <br/>' +
        '<input type="checkbox" name="err" value="3"> &nbsp; - 0,5 <br/> ' +
        '<input type="checkbox" name="err" value="4"> &nbsp; - 1,0 <br/><br/><br/>' +
        '</fieldset>';
}
function thirdTabSelected() {
    document.getElementById("materials").innerHTML = '<fieldset> <legend> Материалы  </legend>' +
        ' <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/> ' +
        ' <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>   ' +
        ' <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>  ' +
        ' <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>  ' +
        ' <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/> ' +
        ' </fieldset>';
    document.getElementById("ispolnenie").innerHTML = '<fieldset><legend>Исполнения</legend>' +
        '<input type="checkbox" name="isp" value="0"> &nbsp; - общ <br/>' +
        '<input type="checkbox" name="isp" value="2"> &nbsp; - Ex <br/>' +
        '</fieldset>';


    document.getElementById("error").innerHTML = '<fieldset> <legend> Погрешность</legend> ' +
        '<input type="checkbox" name="err" value="2"> &nbsp; - 0,25 <br/>' +
        '<input type="checkbox" name="err" value="3"> &nbsp; - 0,5 <br/> ' +
        '<input type="checkbox" name="err" value="4"> &nbsp; - 1,0 <br/><br/><br/>' +
        '</fieldset>';
}


function createQueryString() {

    var result = "";
    var models = document.myForm.models;
    var numberElements = models.length;
    for (var i = 0; i < numberElements; i++) {
        if (models[i].checked)
            result += models[i].id + "|";
    }
    return result;
}

function parseResults(data) {
    $("#result_dialog").dialog('open');

    var MAT = ["01", "02", "05", "07", "09", "11", "12"];
    var ERR = ["0.1", "0.15", "0.25", "0.5", "1"];
    var ISP = ["общ.", "вн", "Ex", "AC", "К"];
    var CLIME = ["УХЛ3.1*", "У2*", "Т3*"];
    var txt = "     <table class=\"result\"   width=\"100%\"><tr>" +
        " <th>Модель</th>" +
        "<th>Исполнение</th>" +
        "<th>Материал</th>" +
        "<th>Климатика</th>" +
        "<th>Погрешность</th>" +
        "<th>С.с черн.</th>" +
        "<th>Цена черн.</th>" +
        "<th>С.с.</th>" +
        "<th>Цена</th> </tr> ";

    for (i = 0; i < data.length; i++) {

        txt += "<tr><td>" + data[i].id + "</td> " +
            "<td>" + ISP[data[i].isp] + "</td>" +
            "<td>" + MAT[data[i].mat] + "</td>" +
            "<td>" + CLIME[data[i].klim] + "</td>" +
            "<td>" + ERR[data[i].err] + "</td>" +
            "<td class=\"money\">" + data[i].costTmp + "</td>" +
            "<td class=\"money\">" + data[i].priceTmp + "</td>" +
            "<td class=\"money\">" + data[i].cost + "</td>" +
            "<td class=\"money\">" + data[i].price + "</td></tr>";
    }
    txt += "</table>";

    $("#result_dialog").html(txt);
//
//    document.getElementById("result").innerHTML = txt;
//    $("#query").hide();
//    $("#result").css('height', "657px");
//    $("#result").css('visibility', "visible");


}

function okFunk() {

    var url = "redact_matrix_price";
    //  var tmp;
    var item;
    var numberElements;
    var queryString = "models=";
    var it = createQueryString();
    if (it == "") {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }


    //  tmp = "";
    it = "";
    item = document.myForm.isp;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }
    if (it == "") {
        alert("Не выбрано исполнение.");
        return;
    } else {
        queryString += "&isp=" + it;
    }

    it = "";
    item = document.myForm.mat;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }

    if (it == "") {
        alert("Не выбраны материалы.");
        return;
    } else {
        queryString += "&mat=" + it;
    }

    it = "";
    item = document.myForm.err;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }

    if (it == "") {
        alert("Не выбраны погрешности.");
        return;
    } else {
        queryString += "&err=" + it;
    }

    it = "";
    item = document.myForm.klim;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }

    if (it == "") {
        alert("Не выбрана климатика.");
        return;
    } else {
        queryString += "&klim=" + it;
    }

//    var reg = /^\d+(\.\d+)?$/
    //  var reg = /^(?:[+\-](?:[ ])?)?\d+(?:[\.,]\d+)?$/
    var reg = /^[0-9]+[\.,]?[0-9]?[0-9]?$/


    var price = document.myForm.price.value.replace(",", ".");
    var cost = document.myForm.cost.value.replace(",", ".");

    if (('' == price) && ('' == cost)) {
        alert('Должен быть указан параметр \n цена и\или себестоимость.');
        return;
    }


    if ((reg.test(cost) || '' == cost) &&
        (reg.test(price) || '' == price)) {
        document.myForm.price.style.backgroundColor = 'white';
        document.myForm.cost.style.backgroundColor = 'white';
        if ('' != price) queryString += "&price=" + price;
        if ('' != cost) queryString += "&cost=" + cost;


    }
    else {
        alert('Допустимы только числовые данные. \n 2 числа после запятой.');
        if (!reg.test(price) && '' != price) {
            document.myForm.price.style.backgroundColor = 'red'
        }
        if (!reg.test(cost) && '' != cost) {
            document.myForm.cost.style.backgroundColor = 'red'
        }
        return;
    }

    $.post("redact", queryString);
}

function okFunk2() {

    var item;
    var numberElements;
    var queryString = "models=";
    var it = createQueryString();
    if (it == "") {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }


    //  tmp = "";
    it = "";
    item = document.myForm.isp;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }
    if (it == "") {
        alert("Не выбрано исполнение.");
        return;
    } else {
        queryString += "&isp=" + it;
    }

    it = "";
    item = document.myForm.mat;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }

    if (it == "") {
        alert("Не выбраны материалы.");
        return;
    } else {
        queryString += "&mat=" + it;
    }

    it = "";
    item = document.myForm.err;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }

    if (it == "") {
        alert("Не выбраны погрешности.");
        return;
    } else {
        queryString += "&err=" + it;
    }

    it = "";
    item = document.myForm.klim;
    numberElements = item.length;
    for (var i = 0; i < numberElements; i++) {
        if (item[i].checked)
            it += item[i].value + "|";
    }

    if (it == "") {
        alert("Не выбрана климатика.");
        return;
    } else {
        queryString += "&klim=" + it;
    }


    $.post("getItems", queryString, parseResults);

}

function selectAll(name) {
    var checkboxes = $("input:checkbox", $("#" + name));
    var img = $("img", $("#" + name));
    img[0].onclick = function (y) {
        return function () {
            deselectAll(y);
        }
    }(name);
    var numberElements = checkboxes.length;
    img[0].src = "../images/delete.gif";
    for (var i = 0; i < numberElements; i++)
        checkboxes[i].checked = true;
}

function deselectAll(name) {
    var checkboxes = $("input:checkbox", $("#" + name));
    var img = $("img", $("#" + name));
    img[0].onclick = function (y) {
        return function () {
            selectAll(y);
        }
    }(name);
    var numberElements = checkboxes.length;
    for (var i = 0; i < numberElements; i++)
        checkboxes[i].checked = false;
    img[0].src = "../images/field.png";

}

function applyTmpValues() {
    if (confirm("Накатить черновик?")) {
        $.post("applyTmp");
    }
}

function resetTmpValues() {
    if (confirm("Обнулить черновик?")) {
        $.post("resetTmp");
    }
}

function priceValuesToTmp() {

    if (confirm("Прайс в черновик?")) {
        $.post("priceToTmp");
    }
}
