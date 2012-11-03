<%@ page import="ua.com.manometer.model.modeldescription.ModelDescription" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Редактор таблицы совместимости.</title>
<link href="css/test.css" rel="stylesheet" type="text/css"/>
<script src="js/jquery.js" type="text/javascript"></script>


<script type="text/javascript">
$().ready(
        function() {


            $("#limits").hide();
            $("#limButton").click(function() {
                $('#limits').show(1000);
                $('#materials').hide();
                $('#error').hide();
                $('#ispolnenie').hide();

                $('#output').hide();

                return false;
            });

            $("#materials").hide();
            $("#matButton").click(function() {
                $('#materials').show(1000);
                $('#limits').hide();
                $('#error').hide();
                $('#ispolnenie').hide();

                $('#output').hide();

                return false;
            });

            $("#error").hide();
            $("#pogButton").click(function() {
                $('#error').show(1000);
                $('#limits').hide();
                $('#materials').hide();
                $('#ispolnenie').hide();

                $('#output').hide();

                return false;
            });
            $("#ispolnenie").hide();
            $("#ispButton").click(function() {

                $('#ispolnenie').show(1000);
                $('#error').hide();
                $('#limits').hide();
                $('#materials').hide();

                $('#output').hide();

                return false;
            });


            $("#output").hide();
            $("#vsButton").click(function() {

                $('#output').show(1000);
                $('#ispolnenie').hide();
                $('#error').hide();
                $('#limits').hide();
                $('#stat').hide();
                return false;
            });
        });


var xmlHttp;
var url = "testAction.do?method=redactModelDescription";
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
            queryString += frm.elements[i].id + "|";
    }
    return queryString;
}

function setLimits() {
    createXMLHttpRequest();


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
    it = $("input[@name='loLim']:checked").val();
    if (it == undefined)
    {
        alert("Не выбран нижний предел.");
        return;
    } else {
        queryString += it;
    }


    queryString += "&hiLim=";

    it = $("input[@name='hiLim']:checked").val();
    if (it == undefined)
    {
        alert("Не выбран верхний предел.");
        return;
    } else {
        queryString += it;
    }


    /* alert(queryString);*/
    xmlHttp.open("POST", url, true);
    // xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}
function setIsp() {
    createXMLHttpRequest();
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

    xmlHttp.open("POST", url, true);
    //    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}


function setMat() {
    createXMLHttpRequest();
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
    //    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}

function setErr() {
    createXMLHttpRequest();
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
    //    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}


function setOut() {
    createXMLHttpRequest();
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
    var frm = document.ou;
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
    //    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
    xmlHttp.send(queryString);
}


function clearTable() {
    var frm = document.forms[0];
    var numberElements = frm.elements.length;
    for (var i = 0; i < numberElements; i++)
        frm.elements[i].checked = false;
}
</script>

</head>
<body>

<div id="parent">
    <form action="#">
        <%

            Long[] ar1 = {30l, 31l, 40l, 41l, 50l, 51l, 60l, 61l, 70l, 71l};
            Long[] ar2 = {30l, 31l, 32l, 33l};
            out.println("<table class=\"models\"  cellspacing=\"0\" > <tr> <td  class=\"silver_rb\" >  <img src=\"images/delete.gif\" width=\"18\" height=\"18\" onclick=\"clearTable();\" hspace=\"4\"\n" +
                    "             border=\"0\"/></td>");
            for (Long it1 : ar1) {
//            String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_rb" : "silver_b";
                String style = "silver_b";
                out.println(" <td class=\"" + style + "\" >.." + it1 + "</td>");
            }
            out.println("</tr>");
            for (Long it2 : ar2) {
                out.println("<tr>");
                out.println(" <td class=\"silver_r\">" + it2 + "..</td>");
                for (Long it1 : ar1) {
                    String style = "silver";
                    ModelDescription result = dao.findById(it2 * 100 + it1);
                    if (result != null) {
                        out.println(" <td  class=\"" + style + " \" ><input type=\"checkbox\" id=\"" + it2 + it1 + "\"/></td>");
                    } else {
                        out.println(" <td  class=\"" + style + "\" >&nbsp;</td>");
                    }

                }
                out.println("</tr>");
            }
            out.println("</table>");
        %>


    </form>


    <div id="buttons">
        <input type="button" id="limButton" value="Пределы" class="but"/>
        <input type="button" id="ispButton" value="Исп." class="but"/>
        <input type="button" id="matButton" value="Мат." class="but"/>
        <input type="button" id="pogButton" value="Погр." class="but"/>

        <input type="button" id="vsButton" value="Вых." class="but"/>

    </div>


    <div id="limits">

        <fieldset>
            <legend>
                Пределы
            </legend>

            <%
                String[] ar3 = {"0,04", "0,063", "0,10", "0,16", "0,25", "0,40", "0,63", "1,0", "1,6", "2,5", "4,0", "6,3", "10", "16", "25", "40", "63", "100", "160", "250", "400", "630", "1,0", "1,6", "2,5", "4,0", "6,3", "10", "16", "25", "40", "63", "100"};
                out.print("<table class=\"lim\" border=\"0\" cellspacing=\"0\"   ><tr> <td class=\"limits\">&nbsp;</td>");
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                StringBuilder sb3 = new StringBuilder();
                for (int i = 0; i < ar3.length; i++) {
                    if ((i % 2) == 0) {
                        out.print(" <td class=\"limits\"   bgcolor=\"#C5C5C5\">" + ar3[i] + "</td>");
                        sb1.append("<td class=\"limits\"  bgcolor=\"#C5C5C5\"><input type=\"radio\" name=\"loLim\" value=\"" + (i + 1) + "\"/></td>");
                        sb2.append("<td class=\"limits\"  bgcolor=\"#C5C5C5\"><input type=\"radio\" name=\"hiLim\" value=\"").append(i + 1).append("\"/></td>");
                        sb3.append(" <td class=\"limits\" bgcolor=\"#C5C5C5\">&nbsp;</td>");

                    } else {
                        out.print(" <td class=\"limits\">&nbsp;</td>");
                        sb1.append("<td class=\"limits\"><input type=\"radio\" name=\"loLim\" value=\"" + (i + 1) + "\"/></td>");
                        sb2.append("<td class=\"limits\"><input type=\"radio\" name=\"hiLim\" value=\"" + (i + 1) + "\"/></td>");
                        sb3.append(" <td class=\"limits\"  >" + ar3[i] + "</td>");

                    }
                }
                out.print("</tr><tr> <td class=\"limits\">мин</td>" + sb1.toString());
                out.print("</tr><tr> <td class=\"limits\">макс</td>" + sb2.toString());
                out.print("</tr><tr> <td class=\"limits\">&nbsp;</td>" + sb3.toString());
                out.println("</tr></table>");
            %>


            <input type="button" id="okLimits" value="Применить" class="okbut" onclick="setLimits();"/>

        </fieldset>


    </div>

    <div id="ispolnenie">
        <form name="isp" action="#">
            <fieldset>
                <legend>
                    Исполнения
                </legend>
                <input type="checkbox" id="isp01" value="0"> &nbsp; - общ <br/>

                <input type="checkbox" id="isp03" value="2"> &nbsp; - Ex <br/>


                <input type="button" id="okIspolnenie" value="Применить" class="okbut" onclick="setIsp();"/>

            </fieldset>
        </form>
    </div>


    <div id="materials">
        <form name="Mat" action="#">
            <fieldset>
                <legend>
                    Материалы
                </legend>
                <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/>
                <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>
                <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>
                <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>
                <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/>
                <input type="button" id="okMaterials" value="Применить" class="okbut" onclick="setMat();"/>

            </fieldset>
        </form>
    </div>

    <div id="error">
        <form name="err" action="#">
            <fieldset>
                <legend>
                    Погрешность
                </legend>

                <input type="checkbox" id="error025" value="2"> &nbsp; - 0,25 <br/>
                <input type="checkbox" id="error05" value="3"> &nbsp; - 0,5 <br/>
                <input type="checkbox" id="error10" value="4"> &nbsp; - 1,0 <br/>
                <input type="button" id="okError" value="Применить" class="okbut" onclick="setErr();"/>

            </fieldset>
        </form>
    </div>


    <div id="output">
        <form name="ou" action="#">
            <fieldset>
                <legend>
                    Выходной сигнал
                </legend>
                <input type="checkbox" id="ou42" value="0"> &nbsp; - 42 <br/>

                <input type="checkbox" id="ou05" value="3"> &nbsp; - 05 <br/>


                <input type="button" id="okOutput" value="Применить" class="okbut" onclick="setOut();"/>

            </fieldset>
        </form>
    </div>


</div>
<div id="downButtons">
    <input type="button" value="Черновик" onclick="window.open('./pages/redactMD/compatibilityTable.jsp')">

</div>
</body>
