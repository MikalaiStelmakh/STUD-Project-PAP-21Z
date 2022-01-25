package z11.libraryapp;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail {
    public int randomCode() {
        int min = 100000;
        int max = 999999;
        int diff = max - min;
        Random random = new Random();
        int code = random.nextInt(diff + 1);
        code += min;
        return code;
    }

    public int SendMail(String email){
        int rand = randomCode();
        String to = email;
        String from = "tankist.sliv@gmail.com";
        String host = "smtp.gmail.com";
        String pass = "libraryapp";

        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.user", "tankist.sliv@gmail.com");
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");


        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        final String user = "tankist.sliv@gmail.com";
                        final String password = "libraryapp";
                        return new PasswordAuthentication(user,password);
                    }
                });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("LibApp");
            String html = "<html>\n" +
                    "<body>\n" +
                    "<h1>Hello, this is your code, write in a line to enter as an administrator</h1>\n" +
                    "<h1>" + rand + "</h1>" +
                    "</body>\n" +
                    "</html>";
            //message.setText("");
            message.setContent(html, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return rand;
    }
}
