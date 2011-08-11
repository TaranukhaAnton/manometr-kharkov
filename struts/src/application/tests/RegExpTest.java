package application.tests;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 07.09.2010
 * Time: 0:28:22
 * To change this template use File | Settings | File Templates.
 */
public class RegExpTest {


    public static void main(String args[]) throws Exception{
      //  try {
            //URL yahoo = new URL("http://www.beyondwhois.com/directory/ch/z/ch-domain-names-31.html");
          URL yahoo = new URL("http://localhost:8080/Manometr/testAction.do?method=testForFL");
         //  URL yahoo = new URL("http://www.yahoo.com/");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }


}
