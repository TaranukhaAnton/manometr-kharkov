package com.sp.util;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

public class AttachmentServlet extends HttpServlet {

	private static final long serialVersionUID = 9059118258207815755L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("report-name");
		if (fileName != null && fileName.trim().length() > 5 /*_.ext*/ && fileName.contains("_")) {
			try {
				String normalizedName = fileName.substring(0, fileName.indexOf('_'));
				
				String tempDir = System.getProperty("java.io.tmpdir");
				File file = new File(tempDir, fileName);
				FileInputStream in = new FileInputStream(file);

				response.setContentLength(in.available());
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + normalizedName + "\";");
				response.setContentType("application/pdf");

				response.resetBuffer();

				ServletOutputStream out = response.getOutputStream();

				try {
					IOUtils.copy(in, out);
				} finally {
					IOUtils.closeQuietly(in);
					out.flush();
					IOUtils.closeQuietly(out);
					
					try {
						file.delete();
					} catch (Exception e) {
						//do nothing
					}
				}

			} catch (FileNotFoundException e) {
				fileNotFound(response);
			} catch (Exception e) {
				if (e.getCause() instanceof SocketException) {
				} else {
					throw new ServletException(e);
				}
			}
		} else
		{
			fileNotFound(response);
		}
	}

	private void fileNotFound(HttpServletResponse response) throws IOException {
		response.sendError(404);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
