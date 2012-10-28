<%@ page import="ua.com.manometer.model.price.OptionsPrice" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%--<%= request.getParameter("type") %>--%>
    <table class="options">
        <tr>
            <th class="first" rowspan="2"></th>
            <th colspan="2">общ.</th>
            <th colspan="2">Вн.</th>
            <th colspan="2">Ex</th>
            <th colspan="2">AC</th>
            <th colspan="2">K</th>
        </tr>
        <tr>
            <th>c/c</th>
            <th>цена</th>
            <th>c/c</th>
            <th>цена</th>
            <th>c/c</th>
            <th>цена</th>
            <th>c/c</th>
            <th>цена</th>
            <th>c/c</th>
            <th>цена</th>

        </tr>
        <%  Integer type = new Integer( request.getParameter("type"));

            String[][] array = {{"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"},
                                {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"},
                                {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"}};

            String[][] lab =   {{"42", "24", "&#8730 42", "05", "50", "&#8730 05", "ДУ 50", "ДУ 80", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "-И", "-ПИ", "ВМ", "-Хим", "-P", "-PK", "-PD", "госповерка"},
                                {"42", "24", "&#8730 42", "05", "50", "&#8730 05", "ДУ 50", "ДУ 80", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "-И", "-ПИ", "ВМ", "-Хим", "-P", "-PK", "-PD", "госповерка"},
                                {"05", "&#8730 05", "50", "42", "&#8730 42", "24", "ДУ 50", "ДУ 80", "-И", "-ПИ", "ВМ", "-Хим", "-P", "-PK", "-PD", "госповерка"}};


             Map<OptionsPrice, OptionsPrice> map = ( Map<OptionsPrice, OptionsPrice>) request.getAttribute("map");
            for (int c = 0; c < array[type].length; c++) {
                out.println("<tr><th>" + lab[type][c] + "</th>");
                for (int isp = 0; isp < 5; isp++) {


                    OptionsPrice key = new OptionsPrice(type, isp, array[type][c]);
                    OptionsPrice item =  map.get(key);

                     %>
        <td>
            <a id="c<%=type%>-<%=isp%>-<%=array[type][c]%>"
               href="javascript:void(view(<%=type%>,<%=item.getIsp()%>,'<%=lab[type][c]%>','<%=array[type][c]%>',<%=item.getPrice()%>,<%=item.getCost()%>))"><%=item.getCost()%>
            </a>
        </td>
        <td>
            <a id="p<%=type%>-<%=isp%>-<%=array[type][c]%>"
               href="javascript:void(view(<%=type%>,<%=item.getIsp()%>,'<%=lab[type][c]%>','<%=array[type][c]%>',<%=item.getPrice()%>,<%=item.getCost()%>))"><%=item.getPrice()%>
            </a>
        </td>
        <%

                }
                out.println("</tr>");
            }
        %>
    </table>
