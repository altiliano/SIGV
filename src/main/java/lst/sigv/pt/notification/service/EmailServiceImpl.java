package lst.sigv.pt.notification.service;

/**
 * Created by Afonseca on 05/01/21
 */

import lst.sigv.pt.notification.NotificationNewUserData;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final MessageSource messageSource;

    public EmailServiceImpl(JavaMailSender emailSender, MessageSource messageSource) {
        this.emailSender = emailSender;
        this.messageSource = messageSource;
    }

    @Override
    public void sendNewUserEmail(NotificationNewUserData data) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(data.getUserEmail());
        message.setSubject(messageSource.getMessage("email.newUser.subject", null, Locale.ENGLISH));
        message.setText(data.getMessage());
        emailSender.send(message);

    }
}
