<%@ page import="application.hibernate.Factory" %>
<%@ page import="application.data.Supplier" %>
<%@ page import="application.data.Currency" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Simple jsp page</title>
    <script type="text/javascript" src="js/ui/jquery-1.4.1.js"></script>
    <style type="text/css">
        /*label, input {*/
        /*display: block;*/
        /*}*/
     


        #content {
            margin: 0px 0px 10px 10px;
            overflow-y: scroll; /* ��������� ������ ��������� */
            height: 630px;
            padding: 0px; /* ���� ������ ������ */
        /*border: solid 1px black;*/
        }

        .inp {
             width:500px;
        }
    </style>
    <SCRIPT type="text/javascript">
        function changeImage() {
            $("#preview").fadeOut("slow", function() {
                $("#preview").attr("src", "images/reportImages/preview_image" + $("#logo :selected").val() + ".jpg");
                $("#preview").fadeIn("slow");
            });

        }


    </SCRIPT>


</head>
<body>


<%
    String id = request.getParameter("id");
    Supplier supplier = new Supplier(null, "", "", "", "", "", "", "", "", "", "");
    if (id != null) if (!id.isEmpty()) {
        try {
            Supplier s = Factory.getSupplierDAO().findById(new Long(id));
            if (s != null) supplier = s;

        } catch (Exception e) {
        }
    }


%>


<form action="supplierAction.do?method=saveSupplier" method="post">
    <input type="hidden" name="id" id="id" value="<%= (supplier.getId()==null)?"":supplier.getId()%>"/>
    <input type="hidden" name="method" id="method" value="saveSuplier"/>

    <div id="content">
        <table>
            <tr>
                <td>Условное название</td>
                <td><input type="text" class="inp" name="alias" id="alias" 
                           value="<%= (supplier.getAlias()==null)?"":supplier.getAlias()%>"/></td>
            </tr>
            <tr>
                <td>Название</td>
                <td><input type="text" class="inp" name="name" id="name"
                           value="<%=(supplier.getName()==null)?"":supplier.getName()%>"/></td>
            </tr>
            <tr>
                <td>Адрес</td>
                <td><input type="text" class="inp" name="address" id="address"
                           value="<%=(supplier.getAddress()==null)?"":supplier.getAddress()%>"/></td>
            </tr>
            <tr>
                <td>Телефоны для связи</td>
                <td>
                    <input type="text" class="inp" name="phone" id="phone"
                           value="<%=(supplier.getPhone() == null) ? "" : supplier.getPhone()%>"/>
            </tr>
            <tr>
                <td>Код ЕРДПОУ</td>
                <td><input type="text" class="inp" name="edrpou" id="edrpou"
                           value="<%=(supplier.getErdpou()==null)?"":supplier.getErdpou()%>"/></td>
            </tr>
            <tr>
                <td>ИНН</td>
                <td><input type="text" class="inp" name="inn" id="inn" value="<%=(supplier.getInn()==null)?"":supplier.getInn()%>"/>
                </td>
            </tr>
            <tr>
                <td>ИНН/КПП</td>
                <td><input type="text" class="inp" name="innkpp" id="innkpp"
                           value="<%=(supplier.getInnkpp()==null)?"":supplier.getInnkpp()%>"/></td>
            </tr>
            <tr>
                <td>ОКПО</td>
                <td><input type="text" class="inp" name="okpo" id="okpo"
                           value="<%=(supplier.getOkpo()==null)?"":supplier.getOkpo()%>"/></td>
            </tr>
            <tr>
                <td>ОГРН</td>
                <td><input type="text" class="inp" name="ogrn" id="ogrn"
                           value="<%=(supplier.getOgrn()==null)?"":supplier.getOgrn()%>"/></td>
            </tr>
            <tr>
                <td>Банк посредник, название</td>
                <td><input type="text" class="inp" name="bankMediatorName" id="bankMediatorName"
                           value="<%=(supplier.getBankMediatorName()==null)?"":supplier.getBankMediatorName()%>"/></td>
            </tr>
            <tr>
                <td>Банк посредник, город, страна</td>
                <td><input type="text" class="inp" name="bankMediatorAddress" id="bankMediatorAddress"
                           value="<%=(supplier.getBankMediatorAddress()==null)?"":supplier.getBankMediatorAddress()%>"/>
                </td>
            </tr>
            <tr>
                <td>Банк посредник, SWIFT</td>
                <td><input type="text" class="inp" name="bankMediatorSWIFT" id="bankMediatorSWIFT"
                           value="<%=(supplier.getBankMediatorSWIFT()==null)?"":supplier.getBankMediatorSWIFT()%>"/>
                </td>
            </tr>
            <tr>
                <td>Банк получатель, название</td>
                <td><input type="text" class="inp" name="bankBeneficiaryName" id="bankBeneficiaryName"
                           value="<%=(supplier.getBankBeneficiaryName()==null)?"":supplier.getBankBeneficiaryName()%>"/>
                </td>
            </tr>
            <tr>
                <td>Банк получатель, город, страна</td>
                <td><input type="text" class="inp" name="bankBeneficiaryAddress" id="bankBeneficiaryAddress"
                           value="<%=(supplier.getBankBeneficiaryAddress()==null)?"":supplier.getBankBeneficiaryAddress()%>"/>
                </td>
            </tr>
            <tr>
                <td>Банк получатель, SWIFT</td>
                <td><input type="text" class="inp" name="bankBeneficiarySWIFT" id="bankBeneficiarySWIFT"
                           value="<%=(supplier.getBankBeneficiarySWIFT()==null)?"":supplier.getBankBeneficiarySWIFT()%>"/>
                </td>
            </tr>
            <tr>
                <td>Банк получатель, асс</td>
                <td><input type="text" class="inp" name="bankBeneficiaryASS" id="bankBeneficiaryASS"
                           value="<%=(supplier.getBankBeneficiaryASS()==null)?"":supplier.getBankBeneficiaryASS()%>"/>
                </td>
            </tr>
            <tr>
                <td>р/с</td>
                <td><input type="text" class="inp" name="rs" id="rs"
                           value="<%=(supplier.getRs()==null)?"":supplier.getRs() %>"/></td>
            </tr>
            <tr>
                <td>т/с</td>
                <td><input type="text" class="inp" name="ts" id="ts" value="<%=(supplier.getTs() ==null)?"":supplier.getTs()%>"/>
                </td>
            </tr>
            <tr>
                <td>Банк</td>
                <td><input type="text" class="inp" name="bank" id="bank"
                           value="<%=(supplier.getBank()==null)?"":supplier.getBank()%>"/></td>
            </tr>
            <tr>
                <td>МФО банка</td>
                <td><input type="text" class="inp" name="mfoBank" id="mfoBank"
                           value="<%=(supplier.getMfoBank()==null)?"":supplier.getMfoBank()%>"/></td>
            </tr>
            <tr>
                <td>БИК</td>
                <td><input type="text" class="inp" name="BIC" id="BIC" value="<%=(supplier.getBIC()==null)?"":supplier.getBIC()%>"/>
                </td>
            </tr>
            <tr>
                <td>к/с</td>
                <td><input type="text" class="inp" name="ks" id="ks" value="<%=(supplier.getKs()==null)?"":supplier.getKs()%>"/>
                </td>
            </tr>
            <tr>
                <td>Валюта р/с</td>
                <td><select name="currency" id="currency" onkeypressEn="true">
                    <% List<Currency> list = Factory.getCurrencyDAO().findAll();

                        for (Currency currency : list) { %>
                    <option value="<%=currency.getId()%>"
                            <%= ((supplier.getCurrency() != null) && (currency.getId().equals(supplier.getCurrency().getId()))) ? "selected" : ""%>
                            ><%=currency.getName()%>
                    </option>

                    <%}%>
                </select></td>
            </tr>
            <tr>
                <td>Логотип</td>
                <td>
                    <select name="logo" id="logo" onchange="changeImage()">

                        <option value="1" <%=supplier.getLogo().equals("1")?"selected":""%> >Логотип 1</option>
                        <option value="2" <%=supplier.getLogo().equals("2")?"selected":""%> >Логотип 2</option>
                        <option value="3" <%=supplier.getLogo().equals("3")?"selected":""%> >Логотип 3</option>
                        <option value="4" <%=supplier.getLogo().equals("4")?"selected":""%> >Логотип 4</option>
                    </select>

            </tr>
            <tr>
                <td></td>
                <td>

                    <IMG id="preview"
                         src="images/reportImages/preview_image<%=(supplier.getLogo().isEmpty())?"1":supplier.getLogo()%>.jpg">


            </tr>

            <tr>
                <td>Схема налогообложения</td>
                <td><input type="text" class="inp" name="taxationScheme" id="taxationScheme"
                           value="<%=(supplier.getTaxationScheme()==null)?"":supplier.getTaxationScheme()%>"/></td>
            </tr>
            <tr>
                <td>Должность начальника</td>
                <td><input type="text" class="inp" name="chief" id="chief"
                           value="<%=(supplier.getChief()==null)?"":supplier.getChief()%>"/></td>
            </tr>
            <tr>
                <td>ФИО начальника</td>
                <td><input type="text" class="inp" name="FIOchief" id="FIOchief"
                           value="<%=(supplier.getFIOChief()==null)?"":supplier.getFIOChief()%>"/></td>
            </tr>
            <tr>
                <td>№ свидетельства плательщика НДС &nbsp; &nbsp; &nbsp; &nbsp;</td>
                <td><input type="text" class="inp" name="NDSPayerNom" id="NDSPayerNom"
                           value="<%=(supplier.getNDSPayerNom()==null)?"":supplier.getNDSPayerNom()%>"/></td>
            </tr>


        </table>
    </div>


    <input type="submit" value="Применить">
    <input type="button" value="Отмена" onclick="location.href='supplierAction.do?method=viewSuppliers'">
</form>

</body>
</html>