package lst.sigv.pt.model.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RestRequestChangePasswordForm implements Serializable {
    private String email;
}
