<html>
<head>
    <title>500 Error</title>
    <script type="text/javascript">
        function pageHeight() {
            return  window.innerHeight != null
            ? window.innerHeight
            : document.documentElement && document.documentElement.clientHeight
            ? document.documentElement.clientHeight
            : document.body != null ? document.body.clientHeight : null;
        }


       window.onload = function() {
        var resolution = pageHeight();
        var res_code = "";
        if (resolution < 650) {
            res_code = 1280;
        } else if (resolution > 650 && resolution < 700) {
            res_code = 1100;
        } else  {
            res_code = 1440;
        }
        var serverName = "<%="http://" + request.getServerName() + ":" + request.getLocalPort() %>";
        document.getElementById("homeLink").href = serverName;
        var cssPath = serverName + "/live.whitelabelsaas.com/errors/css/500/rebranding_" + res_code + ".css" + "?version=" + <%=com.sp.util.Util.getApplicationVersion()%> ;
        document.getElementById("brandingLink").href = cssPath;
       }
    </script>
    <link id="brandingLink" rel="stylesheet" type="text/css"/>
</head>
<body class="error_body">
    <div align="center">
     <table width="100%" style="border:solid;border:0"
           cellpadding="0" cellspacing="0" class="header_bg">

        <tr align="left">
            <td colspan="1"><div class="logo">&nbsp</div></td>
            <td colspan="1"><div class="header">&nbsp</div></td>
        </tr>

     </table>
    </div>

    <div align="center" style="padding-top:15px;height:85%;">
     <table align="left" width="100%"  cellpadding="0" cellspacing="0" class="body"
             style="margin-bottom:20px">
        <tr>
           <td width="25%"></td>
           <td width="50%" class="top_row" align="left">
                <span class="top_text" >IT LOOKS LIKE AN UNWANTED ERROR HAPPENED WITH</span><br>
                <span  class="top_text">THIS PAGE</span>
           </td>
           <td width="25%"></td>
        </tr>
        <tr>
           <td width="25%"></td>
           <td width="50%" align="left">
             <p style="padding:0;" class="er500_text">500<span class="error_text">Error</span></p>
           </td>
           <td width="25%"></td>
        </tr>
         <tr>
           <td width="25%"></td>
           <td width="50%" align="left">
               <div class="main_text">
                    <span>We don’t like these errors and have made a</span><br>
                    <span>record of it for you so</span><br>
                    <span>we can stop it</span>
               </div>
           </td>
           <td width="25%"></td>
        </tr>
        <tr>
           <td width="25%"></td>
           <td width="50%" align="left" class="link_text">
               <div>
                    Click <a id="homeLink">here</a><br>
                    <span>to go home</span><br>
               </div>
           </td>
           <td width="25%"></td>
        </tr>
        <tr>
           <td width="100%" colspan="3" style="padding-bottom:15px;padding-right:15px;" class="credits_block" align="right">
               <span class="credits_text" style="vertical-align:text-bottom;">If you want to shout at a human being, call us: +44 1509 808 564</span>
           </td>
       </tr>
     </table>
</div>

</body>
</html>
