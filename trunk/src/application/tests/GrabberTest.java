package application.tests;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 20.09.2010
 * Time: 14:46:06
 * To change this template use File | Settings | File Templates.
 */
public class GrabberTest {
    public static void main(String[] args) throws IOException {
        Map<String, String> prop = new HashMap<String, String>();
        prop.put("Host", "ceac.state.gov");
        prop.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");
        prop.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        prop.put("Accept-Language", "ru,en-us;q=0.7,en;q=0.3");
        prop.put("Accept-Charset", "windows-1251,utf-8;q=0.7,*;q=0.7");
        prop.put("Keep-Alive", "115");
        prop.put("Connection", "keep-alive");
        prop.put("Cache-Control", "max-age=0");
        Graber g = new Graber();
        g.get(new URL("https://ceac.state.gov/GENNIV/"), prop, "result");
        //System.out.println(g.content);

        int ind = g.content.indexOf("pqsMKFDb");
        String __VIEWSTATE = g.content.substring(ind, ind + 140);
        System.out.println("__VIEWSTATE = " + __VIEWSTATE);

        String encodingType = "UTF-8";
        String data = URLEncoder.encode("__EVENTARGUMENT", encodingType) + "=" + URLEncoder.encode("", encodingType);
        data += "&" + URLEncoder.encode("__EVENTTARGET", encodingType) + "=" + URLEncoder.encode("", encodingType);
        data += "&" + URLEncoder.encode("__LASTFOCUS", encodingType) + "=" + URLEncoder.encode("", encodingType);
        data += "&" + URLEncoder.encode("ctl00$ddlLanguage", encodingType) + "=" + URLEncoder.encode("en-US", encodingType);
        data += "&" + URLEncoder.encode("__VIEWSTATE", encodingType) + "=" + URLEncoder.encode(__VIEWSTATE, encodingType);
        data += "&" + URLEncoder.encode("ctl00$SiteContentPlaceHolder$FormView1$Button2", encodingType) + "=" + URLEncoder.encode("Start Application", encodingType);
        System.out.println("data = " + data);
     //   g.post(new URL("http://localhost:8080/Manometr/testAction.do?method=testForFL"), data, prop, "result2");

       data = "__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=pqsMKFDb%2Foz4e0171qkNdZyxeBYLtC%2F9jA6bxIyiW52ogjJPv%2Br7FM3QVCcuT8VSAWD2PkUMm4WOe9ChxseX%2F2I0AbBx0sRcUe%2BAs7gudvXcobD34%2Fluqt3KhNy2O91YNzmP%2FJRx0s0%3D&ctl00%24ddlLanguage=en-US&ctl00%24SiteContentPlaceHolder%24FormView1%24Button2=Start+Application";
      //  g.post(new URL("https://ceac.state.gov/GENNIV/default.aspx"), data, prop, "result2");
        g.post(new URL("http://localhost:8080/Manometr/testAction.do?method=testForFL"), data, prop, "result2");

    }
}
