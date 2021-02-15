package ba.unsa.etf.rpr.email;

import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    public static void sendEmail(String sender, String password, String recipent,String subject, String text) throws MessagingException {
        System.out.println("PREPARING MESSAGE");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","25");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        Message message = prepareMessage(session,sender,recipent,subject,text);
        Transport.send(message);
        System.out.println("MESSAGE SENT");
    }


    private static Message prepareMessage(Session session, String myAcc, String recipent,String subject, String text){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAcc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipent));
            message.setSubject(subject);
            message.setText(text);
            return message;
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
}
