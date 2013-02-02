<%@ page import="ua.com.manometer.model.modeldescription.ModelDescription" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Simple jsp page


    </title>
    <STYLE type="text/css">
        body {
            font-size: .75em;
            font-family: Arial, Helvetica, sans-serif;
        }

        table.ct {
            border: 1px solid black;
        }

        TD {
            border: 1px solid silver;
        }

        TD.b {
            border-color: black;
            text-align: center;
            font-size: 12px;
        }

        TD.fliped {
            writing-mode: tb-rl;
            filter: flipv fliph;
        }

        TD.no {
            background-color: lightcoral;

        }

        TD.no_l {
            background-color: lightcoral;
            border-left-color: black;
        }

        TD.no_r {
            background-color: lightcoral;
            border-right-color: black;
        }

        TD.yes {

        }

        TD.yes_l {
            border-left-color: black;
        }

        TD.yes_r {
            border-right-color: black;

        }

        TD.np {
            background-color: aquamarine;
        }

        TD.np_l {
            background-color: aquamarine;
            border-left-color: black;

        }

        TD.np_r {
            background-color: aquamarine;
            border-right-color: black;
        }

        td.vertical {
            width: 13px;
            height: 30px;
            writing-mode: tb-rl;
            filter: flipH flipV;
            background: #fff; /* для устранения бага с отображением текста в IE6 и ниже */
            text-align: center;
            font-size: 12px;
            font-family: Arial;
            border-bottom-color: black;
            border-top-color: black;
        }

        td.vertical_r {
            width: 13px;
            height: 30px;
            writing-mode: tb-rl;
            filter: flipH flipV;
            background: #fff; /* для устранения бага с отображением текста в IE6 и ниже */
            text-align: center;
            font-size: 12px;
            font-family: Arial;
            border-right-color: black;
            border-bottom-color: black;
            border-top-color: black;
        }

        td.vertical_l {
            width: 13px;
            height: 30px;
            writing-mode: tb-rl;
            filter: flipH flipV;
            background: #fff; /* для устранения бага с отображением текста в IE6 и ниже */
            text-align: center;
            font-size: 12px;
            font-family: Arial;
            border-left-color: black;
            border-bottom-color: black;
            border-top-color: black;
        }


    </style>
    <script language="JavaScript">
        function GetVerticalLayout()
        {
            /* Параметры */
            var fontFamily = "Arial";
            /* задаем шрифт */
            var fontSize = 12;
            /* задаем размер шрифта */

            var notIE = !(navigator.appVersion.indexOf("MSIE") != -1 && navigator.systemLanguage);
            var supportSVG = document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#SVG", "1.1");

            if (notIE)
            {
                /* Собираем все ячейки */
                var td = document.getElementsByTagName("td");

                /* Находим ячейки с классом vertical и заменяем в них текст svg-изображением */
                var objElement = document.createElement("object");

                for (i = 0; i < td.length; i++)
                {
                    if (td[i].className.match(/vertical/i))
                    {
                        var text = td[i].innerHTML;
                        var h = td[i].clientHeight;
                        var w = td[i].clientWidth;

                        var obj = objElement.cloneNode(true);
                        obj.height = 30;
                        obj.type = "image/svg+xml";
                        obj.width = fontSize + 2;
                        obj.data = "data:image/svg+xml;charset=utf-8,<svg xmlns='http://www.w3.org/2000/svg'><text x='" + (- obj.height / 2) + "' y='" + fontSize + "' style='font-family:" + fontFamily + "; font-size:" + fontSize + "px; text-anchor:middle' transform='rotate(-90)'>" + text + "</text></svg>";
                        td[i].replaceChild(obj, td[i].firstChild);
                    }
                }
            }
        }
    </script>
</head>
<body onload="GetVerticalLayout()">


<table cellspacing="0" class="ct">
<tr>
    <td class="b" rowspan="3">модель</td>
    <td class="b" colspan="5" rowspan="2">исполнение</td>
    <td class="b" colspan="7" rowspan="2">материалы</td>
    <td class="b" colspan="5" rowspan="2">погрешность</td>
    <td class="b" colspan="33">пределы</td>
    <td class="b" colspan="10" rowspan="2">статика</td>
    <td class="b" colspan="6" rowspan="2">выход</td>
    <td class="b" colspan="2" rowspan="2">ДУ</td>
    <td class="b" colspan="33" rowspan="2">КМЧ</td>
</tr>
<tr>

    <td class="b" colspan="22">кПа</td>
    <td class="b" colspan="11">МПа</td>
</tr>
<tr>
    <% String lr;
        String[] ar1 = {"общ", "Вн", "Ex", "AC", "K"};
        for (int i = 0; i < ar1.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar1.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar1[i] + "</td>");
        }
        String[] ar2 = {"01", "02", "05", "07", "09", "11", "12"};

        for (int i = 0; i < ar2.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar2.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar2[i] + "</td>");
        }


        String[] ar3 = {"0,1", "0,15", "0,25", "0,5", "1"};


        for (int i = 0; i < ar3.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar3.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar3[i] + "</td>");
        }

        String[] ar0 = {"0.04", "0.063", "0.10", "0.16", "0.25"
                , "0.40", "0.63", "1.0", "1.6", "2.5", "4.0", "6.3", "10", "16", "25", "40", "63", "100"
                , "160", "250", "400", "630", "1.0", "1.6", "2.5", "4.0", "6.3", "10", "16", "25", "40", "63", "100"};

        for (int i = 0; i < ar0.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar0.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar0[i] + "</td>");
        }


        String[] ar4 = {"0.16", "0.25", "1.6", "2.5", "4.0", "10", "16", "25", "32", "40"};

        for (int i = 0; i < ar4.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar4.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar4[i] + "</td>");
        }
        String[] ar5 = {"42", "24", "&#8730 42", "05", "50", "&#8730 05"};

        for (int i = 0; i < ar5.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar5.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar5[i] + "</td>");
        }


        String[] ar6 = {"50", "80"};

        for (int i = 0; i < ar6.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar6.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar6[i] + "</td>");
        }
        String[] ar7 = {"H1"
                , "H2"
                , "H3"
                , "H4"
                , "H5"
                , "H6"
                , "H7"
                , "H8"
                , "H9"
                , "H10"
                , "H11"
                , "H12"
                , "H13"
                , "H14"
                , "H15"
                , "H16"
                , "H17"
                , "H18"
                , "H19"
                , "H20"
                , "H21"
                , "H31"
                , "H32"
                , "H33"
                , "H34"
                , "H35"
                , "H36"
                , "H37"
                , "H38"
                , "H39"
                , "H40"
                , "H41"
                , "H42"};

        for (int i = 0; i < ar7.length; i++) {
            lr = "";
            lr += (i == 0) ? "_l" : "";
            lr += (i == ar7.length - 1) ? "_r" : "";
            out.println("   <td class=\"vertical" + lr + "\">" + ar7[i] + "</td>");
        }
        out.println("<td class=\"vertical_l\"> ВМ </td>");

    %>


</tr>


<%
  //  GenericHibernateDAO<ModelDescription> dao = Factory.getModelDescriptionDAO();
    List<ModelDescription> result = (List<ModelDescription>) request.getAttribute("models");
//    System.out.println("result.size() = " + result.size());
//    //int k=0;
//    for (int k = 0; k < result.size(); k++) {
//        //
//         System.out.println(k);
//        ModelDescription md = result.get(k);
//    }
    for ( ModelDescription md : result) {
        out.println("<tr> <td >" + md.getId() + "</td>");
//        Boolean[] isp = new Boolean[6];
//        Boolean[] mat = new Boolean[7];
//        Boolean[] err = new Boolean[5];
//        Boolean[] stat = new Boolean[12];
//        Boolean[] output = new Boolean[6];
//        Boolean[] du = new Boolean[4];
//        Boolean[] kmch = new Boolean[43];
//
//        Arrays.fill(isp, false);
//        Arrays.fill(mat, false);
//        Arrays.fill(err, false);
//        Arrays.fill(stat, false);
//        Arrays.fill(output, false);
//        Arrays.fill(du, false);
//        Arrays.fill(kmch, false);



//            for (Integer i : md.getIsp()) {
//                isp[i] = true;
//            }
//
//
//
//        tokenizer = new StringTokenizer(md.getMaterials(), "|");
//        for (; tokenizer.hasMoreTokens();)
//            mat[new Integer(tokenizer.nextToken())] = true;
//
//        tokenizer = new StringTokenizer(md.getErrors(), "|");
//        for (; tokenizer.hasMoreTokens();)
//            err[new Integer(tokenizer.nextToken())] = true;
//
//        tokenizer = new StringTokenizer(md.getStaticPressures(), "|");
//        for (; tokenizer.hasMoreTokens();)
//            stat[new Integer(tokenizer.nextToken())] = true;
//
//        tokenizer = new StringTokenizer(md.getKMCH(), "|");
//        for (; tokenizer.hasMoreTokens();)
//            kmch[new Integer(tokenizer.nextToken())] = true;
//
//        tokenizer = new StringTokenizer(md.getDU(), "|");
//        for (; tokenizer.hasMoreTokens();)
//            du[new Integer(tokenizer.nextToken())] = true;
//
//        tokenizer = new StringTokenizer(md.getOutputSygnals(), "|");
//        for (; tokenizer.hasMoreTokens();)
//            output[new Integer(tokenizer.nextToken())] = true;

        for (Integer i = 0; i < 5; i++)
            out.println("<td class=\"" + (md.getIsp().contains(i) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 4) ? "_r" : "") + "\">&nbsp;</td>");

        for (int i = 0; i < 7; i++)
            out.println("<td class=\"" + (md.getMaterials().contains(i) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 6) ? "_r" : "") + "\">&nbsp;</td>");

        for (int i = 0; i < 5; i++)
            out.println("<td class=\"" + (md.getErrors().contains(i) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 4) ? "_r" : "") + "\">&nbsp;</td>");

        for (int i = 1; i < 34; i++)
            out.println("<td class=\"" + ((i >= md.getLoLimit() && i <= md.getHiLimit()) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 4) ? "_r" : "") + "\">&nbsp;</td>");


        for (int i = 0; i < 10; i++) {


            lr = (i == 0) ? "_l" : "";
            lr += (i == 9) ? "_r" : "";
            if
                    (md.getStaticPressures().isEmpty()) {   out.println("<td class=\"np" + lr + "\">Н</td>");
            } else {
                if (md.getStaticPressures().contains(i))
                    out.println("<td class=\"yes" + lr + "\">&nbsp;</td>");
                else
                    out.println("<td class=\"no" + lr + "\">&nbsp;</td>");
            }
        }

        for (int i = 0; i < 6; i++)
            out.println("<td class=\"" + (md.getOutputs().contains(i) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 5) ? "_r" : "") + "\">&nbsp;</td>");


        for (int i = 0; i < 2; i++)
            out.println("<td class=\"" + (md.getDU().contains(i) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 1) ? "_r" : "") + "\">&nbsp;</td>");


        for (int i = 1; i < 43; i++)

        {
            if ((i > 21) & (i < 31)) continue;
            out.println("<td class=\"" + (md.getKMCH().contains(i) ? "yes" : "no") + ((i == 0) ? "_l" : "") + ((i == 42) ? "_r" : "") + "\">&nbsp;</td>");
        }

        out.println("<td class=\"" + (md.isVM() ? "yes" : "no") + "_l\">&nbsp;</td>");

        out.println("</tr>");

    }


%>
</table>
<a href="../../invoiceAction.do?method=viewInvoices">Назад</a>

<br>
</body>
</html>