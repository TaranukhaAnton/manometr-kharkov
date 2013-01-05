$().ready(
    function() {


        $("#tabs").tabs();
        $('select').selectToUISlider({
            labels: 7,
            labelSrc: 'text'
        });


    });



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









function setLimits() {
    var queryString = "param=limits&elements=";
    var it = createQueryString();
    if (it == "")
    {
        alert("Не выбрана модель.");
        return;
    } else {
        queryString += it;
    }
    queryString += "&loLim="+$("#loLim").val();
    queryString += "&hiLim="+$("#hiLim").val();
    $.post("edit",queryString);
}
function setIsp() {

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
    $.post("edit",queryString);
}


function setMat() {
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
    $.post("edit",queryString);
}

function setErr() {
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

    $.post("edit",queryString);
}


function setStat() {
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
    $.post("edit",queryString);
}

function setOut() {
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

    $.post("edit",queryString);
}
function setDU() {
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
    $.post("edit",queryString);
}
function setKMCH() {
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
    queryString += "";
    for (var i = 0; i < numberElements; i++) {
        if (frm.elements[i].checked)
            queryString += frm.elements[i].value + "|";
    }
    $.post("edit",queryString);
}

function setVM() {
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
    $.post("edit",queryString);
}

function clearTable() {

    var queryString = "";
    var frm = document.forms[0];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++)
        frm.elements[i].checked = false;
}