package application.tests;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 16.09.2010
 * Time: 0:42:26
 * To change this template use File | Settings | File Templates.
 */
public class Graber {
//    String cookie = "";
    String content;
    CookiesInJava cookie = new CookiesInJava();


    public void getInfoToFile(HttpURLConnection conn, String fileName) {
        StringBuffer sb = new StringBuffer();
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".html")));
// get response
            InputStream rawInStream = conn.getInputStream();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(rawInStream));
            String line;
            while ((line = rdr.readLine()) != null) {
                out.println(line);
                sb.append(line);
            }
            out.close();
            rdr.close();
            content = sb.toString();
        }
        catch (Exception e) {
            System.out.println("Exception " + e.toString());
            e.printStackTrace();
        }
    }

    public void get(URL url, Map<String, String> prop, String resultFileName) throws IOException {
        URLConnection conn = url.openConnection();

        for (String s : prop.keySet())
            conn.setRequestProperty(s, prop.get(s));
        cookie.writeCookies(conn, true);
        // if (!cookie.isEmpty())
        //     conn.setRequestProperty("Cookie", cookie);
        getInfoToFile((HttpURLConnection) conn, resultFileName);
        cookie.readCookies((HttpURLConnection) conn, true, true);


    }

    public void post(URL url, String body, Map<String, String> prop, String resultFileName) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        cookie.writeCookies(conn, true);

        conn.setRequestMethod("POST");
        conn.setAllowUserInteraction(false); // you may not ask the user
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);


        for (String s : prop.keySet())
            conn.setRequestProperty(s, prop.get(s));
        conn.setRequestProperty("Content-length", Integer.toString(body.length()));
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        wr.write(body);
        wr.flush();
        wr.close();
        for (String key : conn.getHeaderFields().keySet()) {
            System.err.println(key + ":");
            List<String> val = conn.getHeaderFields().get(key);
            for (String s : val) {
                System.out.println(s);
            }

        }
        


       // getInfoToFile((HttpURLConnection) conn, resultFileName);
       // cookie.readCookies((HttpURLConnection) conn, true, true);


    }
}
