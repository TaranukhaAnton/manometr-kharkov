<%@ page import="application.data.ModelDescription.ModelDescription" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="css/modelsTable.css" rel="stylesheet" type="text/css"/>
    <link href="css/redactMatrixPrice.css" rel="stylesheet" type="text/css"/>
    <link href="css/tabs.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/ui/jquery-1.4.1.js"></script>
    <script src="js/redactPrice.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            var tabContainers = $('div.tabs > div');
            tabContainers.hide();
            //.filter(':first').show();

            $('div.tabs ul.tabNavigation a').click(function () {
                tabContainers.hide();
                tabContainers.filter(this.hash).show();
                //alert(this.hash);
                $('div.tabs ul.tabNavigation a').removeClass('selected');
                $(this).addClass('selected');
                //clearTable();
                deselectAll("first");
                deselectAll("second");
                deselectAll("third");

                if (this.hash == "#first")
                {
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
                if (this.hash == "#second")
                {
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
                if (this.hash == "#third")
                {
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


                return false;
            }).filter(':first').click();
            tabContainers.css("visibility", "visible");
        });

    </script>
    <style type="text/css">
        fieldset {
            height: 160px;
        }

        div.tabs div {
        /*overflow-y: scroll; *//* Добавляем полосы прокрутки */
            height: 180px;
        }


    </style>


</head>
<body>

<div id="header">
    <table class="ttt" style="border-collapse:collapse; padding:0px;">
        <tr>
            <td style="padding-top:0px;">
                <img src="images/elem_vmaximise.png" onclick="toggleQuery();"    id="upDown"/>
            </td>
            <td style="padding-top:0px;">
                <a href="#" onclick="applyTmpValues();"> Накатить черновик</a>
            </td>
            <td style="padding-top:0px;">
                 <a href="#" onclick="resetTmpValues()"> Обнулить черновик</a>
            </td>
            <td style="padding-top:0px;">
                 <a href="#" onclick="priceValuesToTmp()"> Прайс в черновик</a>
            </td>
        </tr>
    </table>

        <%--<img src="images/elem_vmaximise.png"  onclick="toggleQuery();"   id="upDown" /> </li>--%>
        <%--<a href="#" onclick="applyTmpValues();" style=" display: inline;" > Накатить черновик</a>--%>
        <%--<a href="#" onclick="resetTmpValues()" style=" display: inline;"> Обнулить черновик</a>--%>
<%--style="top:0px; left:100px;  position: absolute;"--%>

</div>

<div id="query">

<form action="#" name="myForm">
    <div class="tabs">
        <ul class="tabNavigation">
            <li><a class="" href="#first">Цифровые</a></li>
            <li><a class="" href="#second">Аналоговые</a></li>
            <li><a class="" href="#third">Однопредельные</a></li>
        </ul>

        <div id="first">
            <%
                GenericHibernateDAO<ModelDescription> dao = Factory.getModelDescriptionDAO();

                String[] CO1 = {"01", "10", "15", "20", "24", "30", "33", "34", "35", "40", "41", "42", "43", "44", "45", "50", "51", "52", "53", "54", "55", "60", "61", "62", "63", "64", "70", "71"};
                String[] CO2 = {"50", "51", "52", "53", "54", "55"};
                out.println("<table class=\"models\"  cellspacing=\"0\" > <tr> <td  class=\"silver_rb\" >  <img src=\"images/field.png\" width=\"18\" height=\"18\" onclick=\"selectAll('first');\" hspace=\"4\"\n" +
                        "             border=\"0\"/></td>");
                for (String it1 : CO1) {
                    String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_rb" : "silver_b";
                    out.println(" <td class=\"" + style + "\" >.." + it1 + "</td>");
                }
                out.println("</tr>");
                for (String it2 : CO2) {
                    out.println("<tr>");
                    out.println(" <td class=\"silver_r\">" + it2 + "..</td>");
                    for (String it1 : CO1) {

                        String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_r" : "silver";

                        ModelDescription result = dao.findById(new Long(it2 + it1));
                        if (result != null) {
                            out.println(" <td  class=\"" + style + " \" ><input type=\"checkbox\" name = \"models\" id=\"" + it2 + it1 + "\"/></td>");
                        } else {
                            out.println(" <td  class=\"" + style + "\" >&nbsp;</td>");
                        }

                    }
                    out.println("</tr>");
                }

            %>
            </table>
        </div>
        <div id="second">
            <%
//                GenericHibernateDAO<ModelAoDescription> AOdao = Factory.getSINGLETON().getModelAoDescriptionDAO();

                String[] AO1 = {"01", "10", "15", "20", "24", "30", "34", "40", "41", "44", "50", "51", "54", "60", "61", "64", "70", "71"};
                String[] AO2 = {"20", "21", "22", "23", "24", "25"};
                out.println("<table class=\"models\"  cellspacing=\"0\" > <tr> <td  class=\"silver_rb\" >  <img src=\"images/field.png\" width=\"18\" height=\"18\" onclick=\"selectAll('second');\" hspace=\"4\"\n" +
                        "             border=\"0\"/></td>");
                for (String it1 : AO1) {
                    //String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_rb" : "silver_b";
                    String style = "silver_b";
                    out.println(" <td class=\"" + style + "\" >.." + it1 + "</td>");
                }
                out.println("</tr>");
                for (String it2 : AO2) {
                    out.println("<tr>");
                    out.println(" <td class=\"silver_r\">" + it2 + "..</td>");
                    for (String it1 : AO1) {

//                String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_r" : "silver";
                        String style = "silver";

                        ModelDescription result = dao.findById(new Long(it2 + it1));
                        if (result != null) {
                            out.println(" <td  class=\"" + style + " \" ><input type=\"checkbox\" name = \"models\" id=\"" + it2 + it1 + "\"/></td>");
                        } else {
                            out.println(" <td  class=\"" + style + "\" >&nbsp;</td>");
                        }

                    }
                    out.println("</tr>");
                }

            %>
            </table>
        </div>
        <div id="third">
            <%
//                GenericHibernateDAO<ModelOpDescription> OPdao = Factory.getSINGLETON().getModelOpDescriptionDAO();

                Long[] OP1 = {30l, 31l, 40l, 41l, 50l, 51l, 60l, 61l, 70l, 71l};
                Long[] OP2 = {30l, 31l, 32l, 33l};
                out.println("<table class=\"models\"  cellspacing=\"0\" > <tr> <td  class=\"silver_rb\" >  <img src=\"images/field.png\" width=\"18\" height=\"18\" onclick=\"selectAll('third');\" hspace=\"4\"\n" +
                        "             border=\"0\"/></td>");
                for (Long it1 : OP1) {
//            String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_rb" : "silver_b";
                    String style = "silver_b";
                    out.println(" <td class=\"" + style + "\" >.." + it1 + "</td>");
                }
                out.println("</tr>");
                for (Long it2 : OP2) {
                    out.println("<tr>");
                    out.println(" <td class=\"silver_r\">" + it2 + "..</td>");
                    for (Long it1 : OP1) {

//                String style = ((it1 == "15") || (it1 == "24") || (it1 == "35") || (it1 == "45") || (it1 == "55") || (it1 == "64")) ? "silver_r" : "silver";
                        String style = "silver";

                        ModelDescription result = dao.findById(it2 * 100 + it1);

                        if (result != null) {
                            out.println(" <td  class=\"" + style + " \" ><input type=\"checkbox\" name = \"models\" id=\"" + it2 + it1 + "\"/></td>");
                        } else {
                            out.println(" <td  class=\"" + style + "\" >&nbsp;</td>");
                        }

                    }
                    out.println("</tr>");
                }
                out.println("</table>");
            %>
        </div>
    </div>


    <table>
        <tr>
            <td style="vertical-align:top"><div id="ispolnenie">

        <fieldset>
            <legend>
                Исполнения
            </legend>
            <input type="checkbox" name="isp" value="0"> &nbsp; - общ <br/>
            <input type="checkbox" name="isp" value="1"> &nbsp; - Вн <br/>
            <input type="checkbox" name="isp" value="2"> &nbsp; - Ex <br/>
            <input type="checkbox" name="isp" value="3"> &nbsp; - AC <br/>
            <input type="checkbox" name="isp" value="4"> &nbsp; - K <br/> <br/><br/>


        </fieldset>

    </div></td>
            <td style="vertical-align:top">  <div id="materials">

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

        </fieldset>

    </div></td>
            <td style="vertical-align:top"> <div id="error">

        <fieldset>
            <legend>
                Погрешность
            </legend>
            <input type="checkbox" name="err" value="0"> &nbsp; - 0,1 <br/>
            <input type="checkbox" name="err" value="1"> &nbsp; - 0,15 <br/>
            <input type="checkbox" name="err" value="2"> &nbsp; - 0,25 <br/>
            <input type="checkbox" name="err" value="3"> &nbsp; - 0,5 <br/>
            <input type="checkbox" name="err" value="4"> &nbsp; - 1,0 <br/><br/><br/>

        </fieldset>

    </div></td>
            <td style="vertical-align:top"> <div id="klimat">
        <fieldset>
            <legend>Климатика</legend>


            <input type="checkbox" name="klim" value="0"> &nbsp;УХЛ3.1*<br>
            <input type="checkbox" name="klim" value="1"> &nbsp;У2*<br>
            <input type="checkbox" name="klim" value="2"> &nbsp;Т3* <br> <br/><br/><br/><br/>


        </fieldset>
    </div></td>
            <td style="vertical-align:top">  <div id="inpCena">
        <fieldset>
            <legend></legend>


            <input type="text" name="price" maxlength="10" size="10"> &nbsp; Цена<br>
            <input type="text" name="cost" maxlength="10" size="10"> &nbsp; Себест.<br>
            <input type="button" value="Применить" class="okbut" onclick="okFunk();"/> <br/><br/><br/>
            <input type="button" value="Просмотр" class="okbut" onclick="okFunk2();"/> <br/><br/><br/>


        </fieldset>
    </div></td>

        </tr>
    </table>















</form>

</div>


<div id="result"/>

</body>
