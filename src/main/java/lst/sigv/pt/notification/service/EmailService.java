package lst.sigv.pt.notification.service;

import lst.sigv.pt.notification.NotificationNewUserData;

/**
 * Created by Afonseca on 05/01/21
 */
public interface EmailService {
   void sendNewUserEmail(NotificationNewUserData data);
}
