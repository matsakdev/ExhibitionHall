package com.matsak.exhibitionhall.cfg;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.util.Properties;

public class EmailSession {
    private static EmailSession emailSession = null;
    private Session session = null;

    public static EmailSession getInstance(){
        if (emailSession == null) initializeSession();
        return emailSession;
    }

    private EmailSession(Session sessionReady){
        this.session = sessionReady;
    }

    private static void initializeSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("stanislavmatsak@gmail.com", "yomkditiursuokvf");
            }
        });
        emailSession = new EmailSession(session);
    }

    public Session getSession() {
        return session;
    }


}
