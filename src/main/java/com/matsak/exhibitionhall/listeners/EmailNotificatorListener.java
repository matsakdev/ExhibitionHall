package com.matsak.exhibitionhall.listeners;

import com.itextpdf.text.DocumentException;
import com.matsak.exhibitionhall.cfg.EmailSession;
import com.matsak.exhibitionhall.db.entity.Order;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.utils.PDFGenerator;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import static jakarta.mail.Transport.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class EmailNotificatorListener implements OrderListener{
    private Logger logger = LogManager.getLogger(EmailNotificatorListener.class);

    private Session session = EmailSession.getInstance().getSession();
    private String email;

    public EmailNotificatorListener(String email) {
        this.email = email;
    }

    @Override
    public void update(String eventType, Order order) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("stanislavmatsak@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(order.getEmail()));
            message.setSubject("Your tickets are ready!");

            Multipart multipart = createBody(order);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Cannot send the email message " + e.getMessage());
        }
    }

    private Multipart createBody(Order order) {
        try {
            String msg = "Get your tickets and download them to visit the exhibition(-s)";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            for (Ticket ticket : order.getTickets()) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(PDFGenerator.ticketPDF(ticket));
                multipart.addBodyPart(attachmentBodyPart);
            }
            return multipart;
        } catch (MessagingException | IOException | DocumentException e) {
            logger.error("Cannot send the email message " + e.getMessage());
            return null;
        }
    }
}
