<%@ page import="application.data.ModelDescription.ModelDescription" %>
<%@ page import="application.hibernate.Factory" %>
<%@ page import="application.hibernate.generic.GenericHibernateDAO" %>
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

        TD.fliped {
            writing-mode: tb-rl;
            filter: flipv fliph;
        }

        TD.no {
            background-color: lightcoral;
        }

        TD.yes {

        }

        TD.np {
            background-color: aquamarine;
        }

        .vertical {
            overflow: hidden;
            font-size: 12px;
            float: left;
            margin-right: 20px;
            line-height: 30px;
            position: relative;
            white-space: nowrap;
            width: 30px;
            height: 200px;
            border: 1px solid #999;
        }

        .vertical object {
            width: 30px;
            height: 200px;
            display: block;
        }

        .vertical span {
            display: none;
        }


    </STYLE>
    <!--[if IE]>
         <style type="text/css">
             .vertical span { filter:flipv() fliph(); writing-mode:tb-rl; display:block; position:absolute; left:0; bottom:0; height:200px; width:30px; }
             .vertical object { display:none; }

         </style>
     <![endif]-->


</head>
<body>


<table cellspacing="0" class="ct">
<tr>
    <td rowspan="3">модель</td>
    <td colspan="5" rowspan="2">исполнение</td>
    <td colspan="7" rowspan="2">материалы</td>
    <td colspan="5" rowspan="2">погрешность</td>
    <td colspan="33">пределы</td>
    <td colspan="10" rowspan="2">статика</td>
    <td colspan="6" rowspan="2">выход</td>
    <td colspan="2" rowspan="2">ДУ</td>
    <td colspan="33" rowspan="2">КМЧ</td>
</tr>
<tr>

    <td colspan="22">кПа</td>
    <td colspan="11">МПа</td>
</tr>
<tr>
    <td>
        <DIV class="vertical">
            <SPAN>общ</SPAN>
            <OBJECT type="image/svg+xml" data="data:image/svg+xml;charset=utf-8,
<svg xmlns='http://www.w3.org/2000/svg'><text x='-200' y='18' font-family='Arial' font-size='12' fill='#000000' transform='rotate(-90)' text-rendering='optimizeSpeed'>общ</text></svg>">
            </OBJECT>
        </DIV>
    </td>
    <td class="fliped">Вн</td>
    <td class="fliped">Ex</td>
    <td class="fliped">AC</td>
    <td class="fliped">K</td>

    <td class="fliped">01</td>
    <td class="fliped">02</td>
    <td class="fliped">05</td>
    <td class="fliped">07</td>
    <td class="fliped">09</td>
    <td class="fliped">11</td>
    <td class="fliped">12</td>

    <td class="fliped">0.1</td>
    <td class="fliped">0.15</td>
    <td class="fliped">0.25</td>
    <td class="fliped">0.5</td>
    <td class="fliped">1</td>

    <td class="fliped">0.04</td>
    <td class="fliped">0.063</td>
    <td class="fliped">0.10</td>
    <td class="fliped">0.16</td>
    <td class="fliped">0.25</td>
    <td class="fliped">0.40</td>
    <td class="fliped">0.63</td>
    <td class="fliped">1.0</td>
    <td class="fliped">1.6</td>
    <td class="fliped">2.5</td>
    <td class="fliped">4.0</td>
    <td class="fliped">6.3</td>
    <td class="fliped">10</td>
    <td class="fliped">16</td>
    <td class="fliped">25</td>
    <td class="fliped">40</td>
    <td class="fliped">63</td>
    <td class="fliped">100</td>
    <td class="fliped">160</td>
    <td class="fliped">250</td>
    <td class="fliped">400</td>
    <td class="fliped">630</td>
    <td class="fliped">1.0</td>
    <td class="fliped">1.6</td>
    <td class="fliped">2.5</td>
    <td class="fliped">4.0</td>
    <td class="fliped">6.3</td>
    <td class="fliped">10</td>
    <td class="fliped">16</td>
    <td class="fliped">25</td>
    <td class="fliped">40</td>
    <td class="fliped">63</td>
    <td class="fliped">100</td>

    <td class="fliped">0.16</td>
    <td class="fliped">0.25</td>
    <td class="fliped">1.6</td>
    <td class="fliped">2.5</td>
    <td class="fliped">4.0</td>
    <td class="fliped">10</td>
    <td class="fliped">16</td>
    <td class="fliped">25</td>
    <td class="fliped">32</td>
    <td class="fliped">40</td>


    <td class="fliped">42</td>
    <td class="fliped">24</td>
    <td class="fliped">&#8730 42</td>
    <td class="fliped">05</td>
    <td class="fliped">50</td>
    <td class="fliped">&#8730 05</td>

    <td class="fliped">50</td>
    <td class="fliped">80</td>

    <td class="fliped">H1</td>
    <td class="fliped">H2</td>
    <td class="fliped">H3</td>
    <td class="fliped">H4</td>
    <td class="fliped">H5</td>
    <td class="fliped">H6</td>
    <td class="fliped">H7</td>
    <td class="fliped">H8</td>
    <td class="fliped">H9</td>
    <td class="fliped">H10</td>
    <td class="fliped">H11</td>
    <td class="fliped">H12</td>
    <td class="fliped">H13</td>
    <td class="fliped">H14</td>
    <td class="fliped">H15</td>
    <td class="fliped">H16</td>
    <td class="fliped">H17</td>
    <td class="fliped">H18</td>
    <td class="fliped">H19</td>
    <td class="fliped">H20</td>
    <td class="fliped">H21</td>
    <td class="fliped">H31</td>
    <td class="fliped">H32</td>
    <td class="fliped">H33</td>
    <td class="fliped">H34</td>
    <td class="fliped">H35</td>
    <td class="fliped">H36</td>
    <td class="fliped">H37</td>
    <td class="fliped">H38</td>
    <td class="fliped">H39</td>
    <td class="fliped">H40</td>
    <td class="fliped">H41</td>
    <td class="fliped">H42</td>
</tr>


<%
    GenericHibernateDAO dao = Factory.getModelDescriptionDAO();
    List<ModelDescription> result = dao.findAll();
    // out.println("<table cellspacing=\"0\" class=\"ct\" >");
    // out.println(" <tr> <td rowspan=\"2\">модель</td><td colspan=\"5\">исполнение</td></tr>");
// out.println(" <tr> <td class=\"fliped\">общ</td> <td class=\"fliped\">Вн</td> <td class=\"fliped\">Ex</td> <td class=\"fliped\">AC</td ><td class=\"fliped\">K</td></tr>");
    for (ModelDescription md : result) {
        out.println("<tr> <td >" + md.getId() + "</td>");
        Boolean[] isp = new Boolean[6];
        Boolean[] mat = new Boolean[8];
        Boolean[] err = new Boolean[7];
        Boolean[] stat = new Boolean[12];
        Boolean[] output = new Boolean[7];
        Boolean[] du = new Boolean[4];
        Boolean[] kmch = new Boolean[34];

        Arrays.fill(isp, false);
        Arrays.fill(mat, false);
        Arrays.fill(err, false);
        Arrays.fill(stat, false);
        Arrays.fill(output, false);
        Arrays.fill(du, false);
        Arrays.fill(kmch, false);


        StringTokenizer tokenizer = new StringTokenizer(md.getIsp(), "|");
        for (; tokenizer.hasMoreTokens();)
            isp[new Integer(tokenizer.nextToken())] = true;
        tokenizer = new StringTokenizer(md.getMaterials(), "|");
        for (; tokenizer.hasMoreTokens();)
            mat[new Integer(tokenizer.nextToken())] = true;

        tokenizer = new StringTokenizer(md.getErrors(), "|");
        for (; tokenizer.hasMoreTokens();)
            err[new Integer(tokenizer.nextToken())] = true;

        tokenizer = new StringTokenizer(md.getStaticPressures(), "|");
        for (; tokenizer.hasMoreTokens();)
            stat[new Integer(tokenizer.nextToken())] = true;

        tokenizer = new StringTokenizer(md.getKMCH(), "|");
        for (; tokenizer.hasMoreTokens();)
            kmch[new Integer(tokenizer.nextToken())] = true;

        tokenizer = new StringTokenizer(md.getDU(), "|");
        for (; tokenizer.hasMoreTokens();)
            du[new Integer(tokenizer.nextToken())] = true;

        tokenizer = new StringTokenizer(md.getOutputSygnals(), "|");
        for (; tokenizer.hasMoreTokens();)
            output[new Integer(tokenizer.nextToken())] = true;


        for (int i = 1; i < 6; i++)
            if (isp[i])
                out.println("<td class=\"no\"l>&nbsp;</td>");
            else
                out.println("<td class=\"yes\"l>&nbsp;</td>");
        for (int i = 1; i < 8; i++)
            if (mat[i])
                out.println("<td class=\"no\"l>&nbsp;</td>");
            else
                out.println("<td class=\"yes\"l>&nbsp;</td>");

        for (int i = 1; i < 6; i++)
            if (err[i])
                out.println("<td class=\"yes\"l>&nbsp;</td>");
            else
                out.println("<td class=\"no\"l>&nbsp;</td>");

        for (int i = 1; i < 34; i++)
            if (i >= md.getLoLimit() && i <= md.getHiLimit())
                out.println("<td class=\"yes\"l>&nbsp;</td>");
            else
                out.println("<td class=\"no\"l>&nbsp;</td>");

        for (int i = 1; i < 11; i++)
            if (stat[1]) {
                out.println("<td class=\"np\"l>Н</td>");
            } else {
                if (stat[i + 1])
                    out.println("<td class=\"yes\"l>&nbsp;</td>");
                else
                    out.println("<td class=\"no\"l>&nbsp;</td>");
            }

        for (int i = 1; i < 7; i++)
            if (output[i])
                out.println("<td class=\"no\"l>&nbsp;</td>");
            else
                out.println("<td class=\"yes\"l>&nbsp;</td>");


        for (int i = 1; i < 3; i++)
            if (du[1]) {
                out.println("<td class=\"np\"l>Н</td>");
            } else {
                if (du[i + 1])
                    out.println("<td class=\"yes\"l>&nbsp;</td>");
                else
                    out.println("<td class=\"no\"l>&nbsp;</td>");
            }

        for (int i = 1; i < 34; i++)
            if (kmch[i])
                out.println("<td class=\"yes\"l>&nbsp;</td>");
            else
                out.println("<td class=\"no\"l>&nbsp;</td>");


        out.println("</tr>");


    }


    out.println("");

%>
</table>
</body>
</html>