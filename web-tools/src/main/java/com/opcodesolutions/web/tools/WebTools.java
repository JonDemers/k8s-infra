package com.opcodesolutions.web.tools;

import static java.lang.Integer.parseInt;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class WebTools {

	public static void main(String[] args) throws Exception {
		Server server = new Server(parseInt(Config.get("webtools.server.port", "8080") ));
		ServletContextHandler context = new ServletContextHandler(server, Config.get("webtools.server.contextPath", "/"));
		context.addServlet(Mailer.class, "/mailer");
		server.start();
		server.join();
	}

}
