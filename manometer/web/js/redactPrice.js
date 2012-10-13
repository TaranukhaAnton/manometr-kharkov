var xmlHttp;

function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
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


function handleStateChange() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            parseResults();
        }
    }
}


function toggleQuery()
{
    if ($("#query").is(":hidden")) {
    $("#query").show();
     $("#result").css('height', "205px");

  } else {
 $("#result").css('height', "657px");
   $("#query").hide(); 

  }



    //  alert( $("#query").toggle());

//    $("#result").css('height', "657px");
  //
//    $("#upDown").onclick = function() {
//        return function() {showQuery();}
//    };
}


function parseResults() {
    var response = eval("(" + xmlHttp.responseText + ")");
    var txt = "";
    var s = "";
    txt = "     <table class=\"result\"   width=\"100%\"><tr> <th>Модель</th>       <th>Исполнение</th>" +
          "<th>Материал</th>              <th>Климатика</th> <th>Погрешность</th>     <th>С.с черн.</th> <th>Цена черн.</th> <th>С.с.</th> <th>Цена</th> </tr> ";
    for (i = 0; i < response.items.length; i++)
        txt += "<tr><td>" + response.items[i].model + "</td> <td>" + response.items[i].isp + "</td><td>" + response.items[i].mat + "</td><td>" + response.items[i].klim + "</td><td>" + response.items[i].err + "</td><td class=\"money\">" + response.items[i].costTmp + "</td><td class=\"money\">" + response.items[i].priceTmp + "</td><td class=\"money\">" + response.items[i].cost + "</td><td class=\"money\">" + response.items[i].price + "</td></tr>";
    txt += "</table>";

    document.getElementById("result").innerHTML = txt;
    $("#query").hide();
    $("#result").css('height', "657px");
    $("#result").css('visibility', "visible");



}

function okFunk() {
    createXMLHttpRequest();
    var url = "priceAction.do?method=redactPrice";
    //  var tmp;
    var item;
    var numberElements;
    var queryString = "models=";
    var it = createQueryString();
    if (it == "")
    {
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
    if (it == "")
    {
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

    if (it == "")
    {
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

    if (it == "")
    {
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

    if (it == "")
    {
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

    if (('' == price) && ('' == cost))
    {
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


    // alert(queryString);
    xmlHttp.open("POST", url, true);
    //   xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}


function okFunk2() {

    createXMLHttpRequest();
    var url = "priceAction.do?method=getItemsPrice";
    //  var tmp;
    var item;
    var numberElements;
    var queryString = "models=";
    var it = createQueryString();
    if (it == "")
    {
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
    if (it == "")
    {
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

    if (it == "")
    {
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

    if (it == "")
    {
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

    if (it == "")
    {
        alert("Не выбрана климатика.");
        return;
    } else {
        queryString += "&klim=" + it;
    }


    // alert(queryString);
    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}

function selectAll(name) {
    var checkboxes = $("input:checkbox", $("#" + name));
    var img = $("img", $("#" + name));
    img[0].onclick = function(y) {
        return function() {
            deselectAll(y);
        }
    }(name);
    var numberElements = checkboxes.length;
    img[0].src = "images/delete.gif";
    for (var i = 0; i < numberElements; i++)
        checkboxes[i].checked = true;
}

function deselectAll(name) {
    var checkboxes = $("input:checkbox", $("#" + name));
    var img = $("img", $("#" + name));
    img[0].onclick = function(y) {
        return function() {
            selectAll(y);
        }
    }(name);
    var numberElements = checkboxes.length;
    for (var i = 0; i < numberElements; i++)
        checkboxes[i].checked = false;
    img[0].src = "images/field.png";

}


function clearTable() {

    var queryString = "";
    var frm = document.forms[0];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++)
        frm.elements[i].checked = false;
}


function applyTmpValues() {

    createXMLHttpRequest();
    if (confirm("Накатить черновик?"))
    {
        xmlHttp.open("POST", "priceAction.do?method=applyTmpValues", true);

        xmlHttp.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;");

        xmlHttp.send(" ");


    }
}


function resetTmpValues() {
    createXMLHttpRequest();
    if (confirm("Обнулить черновик?"))
    {
        xmlHttp.open("POST", "priceAction.do?method=resetTmpValues", true);

        xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");

        xmlHttp.send(" ");


    }
}


function priceValuesToTmp() {
    createXMLHttpRequest();
    if (confirm("Прайс в черновик?"))
    {
        xmlHttp.open("POST", "priceAction.do?method=priceValuesToTmp", true);
        xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
        xmlHttp.send(" ");
    }
}