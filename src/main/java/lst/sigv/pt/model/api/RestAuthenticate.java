package lst.sigv.pt.model.api;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Afonseca on 15/11/20
 */
@Data
public class RestAuthenticate implements Serializable {
    private String username;
    private String password;
}
