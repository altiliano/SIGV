package lst.sigv.pt.notification.service;

/**
 * Created by Afonseca on 05/01/21
 */

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.notification.NotificationNewUserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final MessageSource messageSource;
    private final TemplateEngine templateEngine;
    private final TokenService tokenService;
    @Value("${notification.email.from}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender emailSender, MessageSource messageSource, TemplateEngine templateEngine, TokenService tokenService) {
        this.emailSender = emailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.tokenService = tokenService;
    }

    @Override
    public void sendNewUserEmail(NotificationNewUserData data) throws MessagingException {

        Token token = tokenService.allocateToken(data.getUserEmail());
        Context context = new Context();
        context.setVariable("link", getUrlToActiveUser(token));
        context.setVariable("name", data.getName());
        String template = templateEngine.process("emails/welcome", context);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(messageSource.getMessage("email.newUser.subject", null, Locale.ENGLISH));
        helper.setText(template, true);
        helper.setFrom(fromEmail);
        helper.setTo(data.getUserEmail());
        emailSender.send(mimeMessage);

    }

    private String getUrlToActiveUser(Token token) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .replacePath(null)
                .build()
                .toUriString();
        return baseUrl + "/user/active/user?=" + token.getKey();
    }


}
