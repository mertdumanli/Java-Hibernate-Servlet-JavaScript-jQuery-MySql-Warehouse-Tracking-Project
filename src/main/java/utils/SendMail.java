package utils;

import entities.Admin;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public boolean isSendEmail(Admin admin) {
        System.out.println(admin);
        boolean sendMessageControl = true;

        // Gönderici Adresi
        String fromEmail = "depoprojesi@gmail.com";

        // Parola
        String pass = "depoprojesi.1";

        // Alıcı adresi
        String toEmail = "depoprojesi@gmail.com";

        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.starttls.required", "true");
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


            Session mailSession = Session.getDefaultInstance(properties, null);
            mailSession.setDebug(true);
            Message mailMessage = new MimeMessage(mailSession);

            String message = "Gonderen Kişi --> \n";
            message += "http://localhost:8082/depo_project_war_exploded/admin-remember-get?";

            String stId = String.valueOf(admin.getAd_id());

            message += "aa=" + Util.MD5(stId);
            message += "&bb=" + Util.MD5(admin.getAd_name());
            message += "&cc=" + Util.MD5(admin.getAd_surname());
            message += "&dd=" + Util.MD5(admin.getAd_email());
            message += "&ee=" + admin.getAd_password();

            mailMessage.setFrom(new InternetAddress(fromEmail));
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mailMessage.setContent(message, "text/plain; charset=UTF-8");

            mailMessage.setSubject("Depo");// mail başlığı

            Transport transport = mailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", "depoprojesi", "depoprojesi.1");
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());

        } catch (Exception e) {
            System.err.println("SendMessage Error : " + e);
            sendMessageControl = false;
        }
        return sendMessageControl;
    }
}
