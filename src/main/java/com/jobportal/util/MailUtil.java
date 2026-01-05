package com.jobportal.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class MailUtil {

    public static void sendMail(String to, String subject, String body) {

        final String from = "naveen181810@zohomail.in";
        final String password = "thzvrrBcSYGr";


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.zoho.in");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true"); // SSL
        props.put("mail.smtp.starttls.enable", "false");

        Session session = Session.getInstance(
        	    props,
        	    new javax.mail.Authenticator() {
        	        protected PasswordAuthentication getPasswordAuthentication() {
        	            return new PasswordAuthentication(from, password);
        	        }
        	    }
        	);


        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Mail sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
