package com.sp.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 14.08.12
 * Time: 18:31
 * Temporary servlet for TFL images. Use it to prevent caching of images. 
 * Should be resolved with new feed version of CCTV.
 */
public class ImageForwardingServlet extends HttpServlet {

    private static String IMAGE_URL_PREFIX = "http://www.tfl.gov.uk/tfl/livetravelnews/trafficcams/cctv";

    private static Logger LOGGER = Logger.getLogger(ImageForwardingServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageName = request.getParameter("imageName");

        URL url = new URL(IMAGE_URL_PREFIX + "/" + imageName);
        byte[] imageBytes = new byte[0];
        InputStream inputStream      = url.openStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            inputStream.close();
            imageBytes = output.toByteArray();
        } catch (IOException e) {
            LOGGER.error("doGet(): ", e);
        }  finally {
            if (inputStream != null) {
                inputStream.close();
            }
            output.close();
        }
        response.setContentType("image/jpeg");
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}
