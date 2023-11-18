package com.opcodesolutions.web.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Mailer extends HttpServlet {

	public static final String DEFAULT_FROM_EMAIL = getRecipientConfig("default", "fromEmail", null);
	public static final String DEFAULT_TO_EMAIL_LIST = getRecipientConfig("default", "toEmailList", null);
	public static final String DEFAULT_CC_EMAIL_LIST = getRecipientConfig("default", "ccEmailList", null);
	public static final String DEFAULT_BCC_EMAIL_LIST = getRecipientConfig("default", "bccEmailList", null);

	public static String getRecipientConfig(String recipient, String key, String defaultValue) {
		return Config.get("mailer.recipient." + recipient + "." + key, defaultValue);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");
		response.getWriter().print("OK");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String toEmailList = null;

		try {
			String recipient = request.getParameter("recipient");
			String fromEmail = getRecipientConfig(recipient, "fromEmail", DEFAULT_FROM_EMAIL);
			toEmailList = getRecipientConfig(recipient, "toEmailList", DEFAULT_TO_EMAIL_LIST);
			String ccEmailList = getRecipientConfig(recipient, "ccEmailList", DEFAULT_CC_EMAIL_LIST);
			String bccEmailList = getRecipientConfig(recipient, "bccEmailList", DEFAULT_BCC_EMAIL_LIST);

			String email = request.getParameter("email");
			String subject = request.getParameter("subject");
			StringBuilder bodyBuilder = new StringBuilder();
			String confirmation = null;
			boolean sendEmail = true;
			for (Map.Entry<String, String[]> bodyPart : request.getParameterMap().entrySet()) {
				for (String bodyPartValue : bodyPart.getValue()) {
					if ("No Javascript".equals(bodyPartValue)) {
						sendEmail = false;
					}
					bodyPartValue = bodyPartValue.trim();
					boolean isMultiLine = bodyPartValue.contains("\n");
					if (isMultiLine) {
						bodyBuilder.append("\n");
					}
					bodyBuilder.append(bodyPart.getKey().toUpperCase()).append(": ");
					if (isMultiLine) {
						bodyBuilder.append("\n\n");
					}
					bodyBuilder.append(bodyPartValue).append("\n");
					if (isMultiLine) {
						bodyBuilder.append("\n");
					}
					if ("message".equals(bodyPart.getKey())) {
						confirmation = bodyBuilder.toString();
						bodyBuilder.append("\n------------------------------------------\n\n");
					}
				}
			}
			String body = bodyBuilder.toString();

			if (sendEmail) {
				MailUtils.sendEmail(fromEmail, email, toEmailList, ccEmailList, bccEmailList, subject, body);
			}

			response.setStatus(sendEmail ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain");
			PrintWriter writer = response.getWriter();
			writer.println(sendEmail ? "Thank you, we'll get back to you shortly."
					: "Your message was not sent because you've been detected as a bot!");
			writer.println("\n------------------------------------------\n");
			writer.println(confirmation != null ? confirmation : body);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			PrintWriter writer = response.getWriter();
			writer.println("We're sorry, there was a problem processing your request.");
			writer.println();
			writer.println("Please contact us directly: " + toEmailList);
		}

	}

}
