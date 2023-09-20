package org.example.pigeon.email;


import lombok.extern.slf4j.Slf4j;
import org.example.pigeon.email.vo.EmailSendSubject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Component
public class EmailSender {

    @Value("${spring.user.email.name}")
    private String userEmail;
    @Value("${spring.user.email.key}")
    private String userEmailKey;

    private final Properties props = new Properties();
    public EmailSender() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void send(String to, EmailSendSubject subject, String text) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail, userEmailKey);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // 수신자 이메일
            message.setSubject(subject.getKey());
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
