package lst.sigv.pt.notification.service;

import lst.sigv.pt.notification.NotificationNewUserData;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

/**
 * Created by Afonseca on 06/01/21
 */
@SpringBootTest
class EmailServiceImplTest {

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
    }

    // @Test
    void sendNewUserEmail() throws MessagingException {
        emailService.sendNewUserEmail(NotificationNewUserData.builder()
                .userEmail("a@gmail.com")
                .name("Altiliano Fonseca")
                .url("https://springhow.com/spring-boot-email-thymeleaf/")
                .build());
    }
}