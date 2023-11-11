package com.opcodesolutions.web.tools;

import static javax.mail.Message.RecipientType.TO;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.internet.InternetAddress.parse;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	private static final String SMTP_HOST = Config.get("smtp.host", "localhost");
	private static final String SMTP_PORT = Config.get("smtp.port", "25");
	private static final String SMTP_AUTH = Config.get("smtp.auth");
	private static final String SMTP_STARTTLS_ENABLE = Config.get("smtp.starttls.enable");
	private static final String SMTP_USERNAME = Config.get("smtp.username");
	private static final String SMTP_PASSWORD = Config.get("smtp.password");

	public static void sendEmail(
			String fromEmail,
			String replyToEmailList,
			String toEmailList,
			String ccEmailList,
			String bccEmailList,
			String subject,
			String body)
			throws MessagingException {
		sendEmail(getDefaultSession(), fromEmail, replyToEmailList, toEmailList, ccEmailList, bccEmailList, subject, body);
	}

	public static Session getDefaultSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", SMTP_PORT);
		if (SMTP_AUTH != null) {
			props.put("mail.smtp.auth", SMTP_AUTH);
		}
		if (SMTP_STARTTLS_ENABLE != null) {
			props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
		}

		Authenticator auth = null;
		if (SMTP_USERNAME != null) {
			auth = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
				}
			};
		}

		return Session.getInstance(props, auth);
	}

	public static void sendEmail(
			Session session,
			String fromEmail,
			String replyToEmailList,
			String toEmailList,
			String ccEmailList,
			String bccEmailList,
			String subject,
			String body)
			throws MessagingException {

		MimeMessage msg = new MimeMessage(session);

		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		msg.setFrom(new InternetAddress(fromEmail));
		msg.setReplyTo(parseEmailList(replyToEmailList));
		msg.setRecipients(TO, parseEmailList(toEmailList));
		msg.setRecipients(CC, parseEmailList(ccEmailList));
		msg.setRecipients(BCC, parseEmailList(bccEmailList));
		msg.setSubject(subject, "UTF-8");
		msg.setText(body, "UTF-8");

		Transport.send(msg);
	}

	private static InternetAddress[] parseEmailList(String emailList) throws AddressException {
		return emailList != null ? parse(emailList, false) : null;
	}

}
