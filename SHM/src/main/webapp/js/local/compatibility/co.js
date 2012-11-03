$().ready(
    function() {
//        $("#loLimTR").buttonset( { icons: {primary:'ui-icon-gear',secondary:'ui-icon-triangle-1-s'} } );
//        $("#hiLimTR").buttonset( { icons: {primary:'ui-icon-gear',secondary:'ui-icon-triangle-1-s'} } );

        $("input:radio[name=loLim]").click(function() {
            var value = $(this).val();
            alert(value);
        });

        $("#limits").hide();
        $("#limButton").click(function() {
            $('#limits').show(1000);
            $('#materials').hide();
            $('#error').hide();
            $('#ispolnenie').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#DU').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });

        $("#materials").hide();
        $("#matButton").click(function() {
            $('#materials').show(1000);
            $('#limits').hide();
            $('#error').hide();
            $('#ispolnenie').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#DU').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });

        $("#error").hide();
        $("#pogButton").click(function() {
            $('#error').show(1000);
            $('#limits').hide();
            $('#materials').hide();
            $('#ispolnenie').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#DU').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });
        $("#ispolnenie").hide();
        $("#ispButton").click(function() {

            $('#ispolnenie').show(1000);
            $('#error').hide();
            $('#limits').hide();
            $('#materials').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#DU').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });


        $("#stat").hide();
        $("#statButton").click(function() {

            $('#stat').show(1000);
            $('#ispolnenie').hide();
            $('#error').hide();
            $('#limits').hide();
            $('#materials').hide();
            $('#output').hide();
            $('#DU').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });

        $("#output").hide();
        $("#vsButton").click(function() {

            $('#output').show(1000);
            $('#ispolnenie').hide();
            $('#error').hide();
            $('#limits').hide();
            $('#materials').hide();
            $('#stat').hide();
            $('#DU').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });

        $("#DU").hide();
        $("#duButton").click(function() {

            $('#DU').show(1000);
            $('#ispolnenie').hide();
            $('#error').hide();
            $('#limits').hide();
            $('#materials').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#KMCH').hide();
            $('#VM').hide();
            return false;
        });

        $("#KMCH").hide();
        $("#kmchButton").click(function() {

            $('#KMCH').show(1000);
            $('#ispolnenie').hide();
            $('#error').hide();
            $('#limits').hide();
            $('#materials').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#DU').hide();
            $('#VM').hide();
            return false;
        });


        $("#VM").hide();
        $("#vmButton").click(function() {

            $('#VM').show(1000);
            $('#KMCH').hide();
            $('#ispolnenie').hide();
            $('#error').hide();
            $('#limits').hide();
            $('#materials').hide();
            $('#stat').hide();
            $('#output').hide();
            $('#DU').hide();

            return false;
        });


    });


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

    var queryString = "";
    var frm = document.forms[0];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++) {


        if (frm.elements[i].checked)
        //            if (queryString == "") {

            queryString += frm.elements[i].id + "|";

        //            } else {
        //                queryString += "&" + frm.elements[i].id + "|";
        //            }


    }


    //            var firstName = document.getElementById("firstName").value;
    //            var middleName = document.getElementById("middleName").value;
    //            var birthday = document.getElementById("birthday").value;
    //             queryString = "firstName=" + firstName + "&middleName=" + middleName
    //                    + "&birthday=" + birthday;
    return queryString;
}


function createAttrString() {

    var queryString = "qwer";
    var ar = new Array("first", "second", "therd");


    var numberElements = ar.length;
    for (var i = 0; i < numberElements; i++) {
        queryString += ar[i] + "@";


    }


    //            var firstName = document.getElementById("firstName").value;
    //            var middleName = document.getElementById("middleName").value;
    //            var birthday = document.getElementById("birthday").value;
    //             queryString = "firstName=" + firstName + "&middleName=" + middleName
    //                    + "&birthday=" + birthday;
    return queryString;
}


function doRequestUsingGET() {
    createXMLHttpRequest();
    var queryString = "testAction.do?method=redactModelDescription&";
    queryString = queryString + createQueryString();

    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("GET", queryString, true);
    xmlHttp.send(null);
}


//function doRequestUsingPOST() {
//    createXMLHttpRequest();
//    var url = "testAction.do?method=redactModelDescription";
//    var queryString = createQueryString();
//    alert(queryString);
//    //            xmlHttp.open("POST", url, true);
//    //            xmlHttp.onreadystatechange = handleStateChange;
//    //            xmlHttp.setRequestHeader("Content-Type",
//    //                    "application/x-www-form-urlencoded;");
//    //            xmlHttp.send(queryString);
//}


function handleStateChange() {
    //    if (xmlHttp.readyState == 4) {
    //        if (xmlHttp.status == 200) {
    //            parseResults();
    //        }
    //    }
}
function parseResults() {
    var responseDiv = document.getElementById("serverResponse");
    if (responseDiv.hasChildNodes()) {
        responseDiv.removeChild(responseDiv.childNodes[0]);
    }
    var responseText = document.createTextNode(xmlHttp.responseText);
    responseDiv.appendChild(responseText);
}


function setLimits() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";

    var queryString = "param=limits&elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }


    queryString += "&loLim=";
//    $("input:radio[name=loLim]")
    it = $("input[name=loLim]:checked").val();
    if (it == undefined)
    {
        alert("Не выбран нижний предел.");
        return;
    } else {
        queryString += it;
    }


    queryString += "&hiLim=";

    it = $("input[name=hiLim]:checked").val();
    if (it == undefined)
    {
        alert("Не выбран верхний предел.");
        return;
    } else {
        queryString += it;
    }

    $.post("edit",queryString);
//    /* alert(queryString);*/
//    xmlHttp.open("POST", url, true);
//    xmlHttp.onreadystatechange = handleStateChange;
//    xmlHttp.setRequestHeader("Content-Type",
//        "application/x-www-form-urlencoded;");
//    xmlHttp.send(queryString);
}
function setIsp() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=isp&val=";
    it = "";
    var frm = document.forms[1];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            it += frm.elements[i].value + "|";
    }

    if (it == "")
    {
        alert("Должно быть выбрано хотя бы одно исполнение.");
        return;
    } else {
        queryString += it;
    }


    //  alert(queryString);
    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}


function setMat() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=mat&val=";
    it = "";
    var frm = document.Mat;
    var numberElements = frm.mat.length;
    for (var i = 0; i < numberElements; i++) {
        if (frm.mat[i].checked)
            it += frm.mat[i].value + "|";
    }

    if (it == "")
    {
        alert("Должно быть выбран хотя бы один материал.");
        return;
    } else {
        queryString += it;
    }


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}

function setErr() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=err&val=";
    it = "";
    var frm = document.forms[3];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            it += frm.elements[i].value + "|";
    }

    if (it == "")
    {
        alert("Должно быть выбрана хотя бы одна погрешность.");
        return;
    } else {
        queryString += it;
    }


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}


function setStat() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=stat&val=";
    it = "";
    var frm = document.forms[4];


    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            it += frm.elements[i].value + "|";
    }

    if (it == "")
    {
        alert("Должно быть выбрано хотя бы одно давление или НП.");
        return;
    } else {
        queryString += it;
    }


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}

function setOut() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=Out&val=";
    it = "";
    var frm = document.forms[5];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            it += frm.elements[i].value + "|";
    }

    if (it == "")
    {
        alert("Должно быть выбрано хотя бы одно давление или НП.");
        return;
    } else {
        queryString += it;
    }


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}
function setDU() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=DU&val=";
    it = "";
    var frm = document.forms[6];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            it += frm.elements[i].value + "|";
    }

    if (it == "")
    {
        alert("Должно быть выбрано хотя бы одно давление или НП.");
        return;
    } else {
        queryString += it;
    }


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}
function setKMCH() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=KMCH&val=";
    it = "";
    var frm = document.forms[7];
    var numberElements = frm.elements.length;
    queryString += "0|";
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            queryString += frm.elements[i].value + "|";
    }
    //  queryString += it;
    //    if (it == "")
    //    {
    //        alert("Должно быть выбрано хотя бы одно давление или НП.");
    //        return;
    //    } else {
    //        queryString += it;
    //    }


    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}

function setVM() {
    createXMLHttpRequest();
    var url = "testAction.do?method=redactModelDescription";
    var queryString = "elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }

    queryString += "&param=VM&val=";
    queryString += document.VmForm.vm.checked;

    xmlHttp.open("POST", url, true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}

function clearTable() {

    var queryString = "";
    var frm = document.forms[0];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++)
        frm.elements[i].checked = false;
}