package application.tests;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//data = __EVENTARGUMENT=&__EVENTTARGET=&__LASTFOCUS=&ctl00%24ddlLanguage=en-US&__VIEWSTATE=pqsMKFDb%2FowuHv%2BbHbx74Q8MVBkEVbj373Saa05AwAFjOMzmbyIUJKi7g1v64UepVYUg%2BaJXe%2F5wLuj9TbUJFU6WtrV4Aq9b%2B3NF1rGuI1wE5rS4CTTUiX4k%2B03CIAVbFwqh4D1caDs%3D&ctl00%24SiteContentPlaceHolder%24FormView1%24Button2=Start+Application
//data = __EVENTARGUMENT=&__EVENTTARGET=&__LASTFOCUS=&ctl00%24ddlLanguage=en-US&__VIEWSTATE=&ctl00%24SiteContentPlaceHolder%24FormView1%24Button2=Start+Application
public class PostForm {
    public static void main(String[] args) throws Exception {
//String str ="http://localhost:8080/Manometr/testAction.do?method=testForFL";
      URLConnection urlConnection;


       // URL url = new URL("http://localhost:8080/Manometr/testAction.do?method=redactCoForward");
        URL url = new URL("https://ceac.state.gov/GENNIV/");


        urlConnection = url.openConnection();


//        urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//
//        urlConnection.setRequestProperty("Cache-Control", "no-cache");
//        urlConnection.setRequestProperty("Pragma", "no-cache");



        CookiesInJava cookiesInJava = new CookiesInJava();
        cookiesInJava.readCookies(urlConnection, true, true);


        String __VIEWSTATE = "";
        DataInputStream dis;
        String inputLine;
        int i = 0;
        dis = new DataInputStream(urlConnection.getInputStream());
        while ((inputLine = dis.readLine()) != null) {

            if (inputLine.contains("__VIEWSTATE")) {
                String[] s = inputLine.split("\"");
                __VIEWSTATE = s[7];
            }
        }
         System.out.println("__VIEWSTATE = " + __VIEWSTATE);
         getInfoToFile((HttpURLConnection) urlConnection, "res1");
        dis.close();


        String encodingType = "UTF-8";
        String data = URLEncoder.encode("__EVENTARGUMENT", encodingType) + "=" + URLEncoder.encode("", encodingType);
        data += "&" + URLEncoder.encode("__EVENTTARGET", encodingType) + "=" + URLEncoder.encode("", encodingType);
        data += "&" + URLEncoder.encode("__LASTFOCUS", encodingType) + "=" + URLEncoder.encode("", encodingType);
        data += "&" + URLEncoder.encode("ctl00$ddlLanguage", encodingType) + "=" + URLEncoder.encode("en-US", encodingType);
        data += "&" + URLEncoder.encode("__VIEWSTATE", encodingType) + "=" + URLEncoder.encode(__VIEWSTATE, encodingType);
        data += "&" + URLEncoder.encode("ctl00$SiteContentPlaceHolder$FormView1$Button2", encodingType) + "=" + URLEncoder.encode("Start Application", encodingType);

        System.out.println("data = " + data);

           url = new URL("https://ceac.state.gov/GENNIV/default.aspx");
//      url = new URL("http://localhost:8080/Manometr/testAction.do?method=testForFL");
          URLConnection conn = url.openConnection();





      



           cookiesInJava.writeCookies(conn, true);

            postString((HttpURLConnection) conn, data);

    }

    /**
     * Post a string to an URL
     */
    static public void postString(HttpURLConnection conn, String body) {
        try {
// URL must use the http protocol!

            conn.setRequestMethod("POST");
            conn.setAllowUserInteraction(false); // you may not ask the user
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setUseCaches(false);

// the Content-type should be default, but we set it anyway
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Map<String, List<String>> headers = conn.getRequestProperties();
            for (String s : headers.keySet()) {
                System.out.println("s = " + s);
            }
// the content-length should not be necessary, but we're cautious
            conn.setRequestProperty("Content-length", Integer.toString(body.length()));
//get the output stream to POST our form data
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(body);
            wr.flush();
            wr.close();

//
// get the input stream for reading the reply
// IMPORTANT! Your body will not get transmitted if you get the
// InputStream before completely writing out your output first!
// save the the content to a file
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("result.html")));
// get response
            InputStream rawInStream = conn.getInputStream();

            Map<String, List<String>> h = conn.getHeaderFields();
            for (String s : h.keySet()) {
                System.out.println("p = " + s);
            }

            BufferedReader rdr = new BufferedReader(new InputStreamReader(rawInStream));
            String line;
            while ((line = rdr.readLine()) != null) {
                out.println(line);
            }
            out.close();
            rdr.close();
        }
        catch (Exception e) {
            System.out.println("Exception " + e.toString());
            e.printStackTrace();
        }
    }


    static public void getInfoToFile(HttpURLConnection conn, String fileName) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName+".html")));
// get response
            InputStream rawInStream = conn.getInputStream();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(rawInStream));
            String line;
            while ((line = rdr.readLine()) != null) {
                out.println(line);
            }
            out.close();
            rdr.close();
        }
        catch (Exception e) {
            System.out.println("Exception " + e.toString());
            e.printStackTrace();
        }
    }






}

