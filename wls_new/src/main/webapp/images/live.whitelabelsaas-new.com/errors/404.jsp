<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>404 Error</title>
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
        var cssPath = serverName + "/live.whitelabelsaas.com/errors/css/404/rebranding_" + res_code + ".css" + "?version=" + <%=com.sp.util.Util.getApplicationVersion()%> ;
        document.getElementById("brandingLink").href = cssPath;
       }
    </script>
    <link id="brandingLink" rel="stylesheet" type="text/css"/>
</head>
<body class="error_body" >
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
     <table align="left" width="100%" cellpadding="0" cellspacing="0" class="body">
        <tr>
           <td width="25%"></td>
           <td width="50%" class="top_row" align="left">
                <span class="top_text" >IT LOOKS LIKE YOU CAME ACROSS AN INCORRECT URL</span><br>
           </td>
           <td width="25%"></td>
        </tr>
        <tr>
           <td width="25%"></td>
           <td width="50%" align="left" style="margin-bottom:0;">
             <p style="padding:0;" class="er500_text">404<span class="error_text">Error</span></p>
           </td>
           <td width="25%"></td>
        </tr>
         <tr>
           <td width="25%"></td>
           <td width="50%" align="left">
               <div class="main_text">
                    <span>Please check the spelling and if you clicked</span><br>
                    <span>a link to get here please send us an Email</span><br>
                    <span>telling us where you found the link and</span><br>
                    <span>how to make</span><br>
                    <span>it happen</span><br>
                    <span>again</span>
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
           <td width="100%" colspan="3" class="credits_block" align="right" style="padding-bottom:15px;padding-right:15px;">
               <span class="credits_text" style="vertical-align:text-bottom;">If you want to shout at a human being, call us: +44 1509 808 564</span>
           </td>
       </tr>
     </table>
</div>

</body>
</html>
