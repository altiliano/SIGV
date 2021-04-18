package lst.sigv.pt.notification.service;

import lst.sigv.pt.notification.NotificationNewUserData;

import javax.mail.MessagingException;

/**
 * Created by Afonseca on 05/01/21
 */
public interface EmailService {
   void sendNewUserEmail(NotificationNewUserData data) throws MessagingException;
   void sendUrlToChangePassword(NotificationNewUserData data) throws MessagingException;
}
