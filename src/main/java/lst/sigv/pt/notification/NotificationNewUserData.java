package lst.sigv.pt.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Afonseca on 05/01/21
 */

@Getter
@Setter
@Builder
public class NotificationNewUserData  implements Serializable {
    private String userEmail;
    private String name;
    private String message;
    private String url;
}
