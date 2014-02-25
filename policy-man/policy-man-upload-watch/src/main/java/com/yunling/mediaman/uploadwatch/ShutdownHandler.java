package com.yunling.mediaman.uploadwatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ShutdownHandler extends AbstractHandler {

	private String passwd = "yunling2010";
	
	class ShutdownRunner implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			try {
				getServer().stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if ("/shutdown".equals(baseRequest.getRequestURI())) {
			baseRequest.setHandled(true);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html;charset=utf-8");
			if (StringUtils.equals(passwd, request.getParameter("passwd"))) {
				response.getWriter().println("<h1>Begin shutting down</h1>");
				Thread th = new Thread(new ShutdownRunner());
				th.start();
			} else {
		        response.getWriter().println("<h1>Password mismatched</h1>");
			}
		}
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
