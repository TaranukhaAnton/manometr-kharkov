<%@ page import="application.data.ModelDescription.ModelDescription" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Редактор таблицы совместимости.</title>
    <link href="css/test.css" rel="stylesheet" type="text/css"/>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/test.js" type="text/javascript"></script>



</head>
<body>

 <div id="parent">
<form action="#">
    <%
        GenericHibernateDAO<ModelDescription> dao = Factory.getModelDescriptionDAO();

        String[] ar1 = {"01", "10", "15", "20", "24", "30", "33", "34", "35", "40", "41", "42", "43", "44", "45", "50", "51", "52", "53", "54", "55", "60", "61", "62", "63", "64", "70", "71"};
        String[] ar2 = {"50", "51", "52", "53", "54", "55"};
        out.println("<table class=\"models\"  cellspacing=\"0\" > <tr> <td  class=\"silver_rb\" >  <img src=\"images/delete.gif\" width=\"18\" height=\"18\" onclick=\"clearTable();\" hspace=\"4\"\n" +
                "             border=\"0\"/></td>");
        for (String it1 : ar1) {
            String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_rb" : "silver_b";
            out.println(" <td class=\"" + style + "\" >.." + it1 + "</td>");
        }
        out.println("</tr>");
        for (String it2 : ar2) {
            out.println("<tr>");
            out.println(" <td class=\"silver_r\">" + it2 + "..</td>");
            for (String it1 : ar1) {

                String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_r" : "silver";

                ModelDescription result = dao.findById(new Long(it2 + it1));
                if (result != null) {
                    out.println(" <td  class=\"" + style + " \" ><input type=\"checkbox\" id=\"" + it2 + it1 + "\"/></td>");
                } else {
                    out.println(" <td  class=\"" + style + "\" >&nbsp;</td>");
                }

            }
            out.println("</tr>");
        }

    %>
    </table>

</form>


<div id="buttons">
    <input type="button" id="limButton" value="Пределы" class="but"/>
    <input type="button" id="ispButton" value="Исп." class="but"/>
    <input type="button" id="matButton" value="Мат." class="but"/>
    <input type="button" id="pogButton" value="Погр." class="but"/>
    <input type="button" id="statButton" value="Стат." class="but"/>
    <input type="button" id="vsButton" value="Вых." class="but"/>
    <input type="button" id="duButton" value="ДУ" class="but"/>
    <input type="button" id="kmchButton" value="КМЧ" class="but"/>
    <input type="button" id="vmButton" value="ВМ" class="but"/>
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
                    sb2.append("<td class=\"limits\"  bgcolor=\"#C5C5C5\"><input type=\"radio\" name=\"hiLim\" value=\"" + (i + 1) + "\"/></td>");
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
    <form name="isp">
        <fieldset>
            <legend>
                Исполнения
            </legend>
            <input type="checkbox" id="isp01" value="0"> &nbsp; - общ <br/>
            <input type="checkbox" id="isp02" value="1"> &nbsp; - Вн <br/>
            <input type="checkbox" id="isp03" value="2"> &nbsp; - Ex <br/>
            <input type="checkbox" id="isp04" value="3"> &nbsp; - AC <br/>
            <input type="checkbox" id="isp05" value="4"> &nbsp; - K <br/>

            <input type="button" id="okIspolnenie" value="Применить" class="okbut" onclick="setIsp();"/>

        </fieldset>
    </form>
</div>


<div id="materials">
    <form name="Mat">
        <fieldset>
            <legend>
                Материалы
            </legend>
            <input type="checkbox" name="mat" value="0"> &nbsp; - 01 <br/>
            <input type="checkbox" name="mat" value="1"> &nbsp; - 02 <br/>
            <input type="checkbox" name="mat" value="2"> &nbsp; - 05 <br/>
            <input type="checkbox" name="mat" value="3"> &nbsp; - 07 <br/>
            <input type="checkbox" name="mat" value="4"> &nbsp; - 09 <br/>
            <input type="checkbox" name="mat" value="5"> &nbsp; - 11 <br/>
            <input type="checkbox" name="mat" value="6"> &nbsp; - 12 <br/>
            <input type="button" id="okMaterials" value="Применить" class="okbut" onclick="setMat();"/>

        </fieldset>
    </form>
</div>

<div id="error">
    <form name="err">
        <fieldset>
            <legend>
                Погрешность
            </legend>
            <input type="checkbox" id="error01" value="0"> &nbsp; - 0,1 <br/>
            <input type="checkbox" id="error015" value="1"> &nbsp; - 0,15 <br/>
            <input type="checkbox" id="error025" value="2"> &nbsp; - 0,25 <br/>
            <input type="checkbox" id="error05" value="3"> &nbsp; - 0,5 <br/>
            <input type="checkbox" id="error10" value="4"> &nbsp; - 1,0 <br/>
            <input type="button" id="okError" value="Применить" class="okbut" onclick="setErr();"/>

        </fieldset>
    </form>
</div>


<div id="stat">
    <form name="st">
        <fieldset>
            <legend>
                Статика
            </legend>
            <input type="checkbox" id="STnp"  value="0"> &nbsp; - НП <br/>
            <input type="checkbox" id="s0.16" value="1"> &nbsp; - 0,16 <br/>
            <input type="checkbox" id="s0.25" value="2"> &nbsp; - 0,25 <br/>
            <input type="checkbox" id="s1.6" value="3"> &nbsp; - 1,6 <br/>
            <input type="checkbox" id="s2.5" value="4"> &nbsp; - 2,5 <br/>
            <input type="checkbox" id="s4" value="5"> &nbsp; - 4 <br/>
            <input type="checkbox" id="s10" value="6"> &nbsp; - 10 <br/>
            <input type="checkbox" id="s16" value="7"> &nbsp; - 16 <br/>
            <input type="checkbox" id="s25" value="8"> &nbsp; - 25 <br/>
            <input type="checkbox" id="s32" value="9"> &nbsp; - 32 <br/>
            <input type="checkbox" id="s40" value="10"> &nbsp; - 40 <br/>

            <input type="button" id="okStat" value="Применить" class="okbut" onclick="setStat();"/>

        </fieldset>
    </form>
</div>


<div id="output">
    <form name="ou">
        <fieldset>
            <legend>
                Выходной сигнал
            </legend>
            <input type="checkbox" id="ou42" value="0"> &nbsp; - 42 <br/>
            <input type="checkbox" id="ou24" value="1"> &nbsp; - 24 <br/>
            <input type="checkbox" id="ous42" value="2"> &nbsp; - &#8730 42 <br/>
            <input type="checkbox" id="ou05" value="3"> &nbsp; - 05 <br/>
            <input type="checkbox" id="ou50" value="4"> &nbsp; - 50 <br/>
            <input type="checkbox" id="ous05" value="5"> &nbsp; - &#8730 05 <br/>


            <input type="button" id="okOutput" value="Применить" class="okbut" onclick="setOut();"/>

        </fieldset>
    </form>
</div>


<div id="DU">
    <form name="du">
        <fieldset>
            <legend>
                ДУ
            </legend>
            <input type="checkbox" id="DUnp"  value="0"> &nbsp; - НП <br/>
            <input type="checkbox" id="DU50" value="1"> &nbsp; - 50 <br/>
            <input type="checkbox" id="DU80" value="2"> &nbsp; - 80 <br/>
            <input type="button" id="okDU" value="Применить" class="okbut" onclick="setDU();"/>
        </fieldset>
    </form>
</div>

<div id="KMCH">
    <form>
        <fieldset>
            <legend>
                КМЧ
            </legend>
            <%
                sb1 = new StringBuilder();
                out.print("<table class=\"lim\" border=\"0\" cellspacing=\"0\"   ><tr> ");

                for (int i = 1; i < 43; i++) {
                    if ((i < 22) || (i > 30)) {
                        out.print(" <td > <p align=\"center\">" + i + "</p></td>");
                        sb1.append("<td ><input type=\"checkbox\" id=\" kmch" + i + "\"value=" + i + " /></td>");
                    }

                }
                out.print("</tr><tr> " + sb1.toString());
                out.println("</tr></table>");
            %>
            <input type="button" id="okKMCH" value="Применить" class="okbut" onclick="setKMCH();"/>
        </fieldset>
    </form>
</div>


<div id="VM">
    <form name="VmForm">
        <fieldset>
            <legend>
                ВМ
            </legend>
            <input type="checkbox" id="V" name="vm">&nbsp;-Выносная мембрана <br/>

            <input type="button" id="okVM" value="Применить" class="okbut" onclick="setVM();"/>
        </fieldset>
    </form>
</div>




 </div>

<div id="downButtons">
    <%--<a href="./pages/redactMD/compatibilityTable.jsp">Черновик</a>--%>
    <input type="button" value="Черновик"  onclick="window.open('./pages/redactMD/compatibilityTable.jsp')">
    <%--<a href="#">применить черновик</a>--%>
    <%--<a href="./pages/compatibilityTable.jsp">Таблица</a>--%>

</div>


</body>
