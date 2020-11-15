package lst.sigv.pt.model.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Afonseca on 15/11/20
 */
@Data
public class RestAuthenticateResponse implements Serializable {
    private String token;
    private Set<RestAuthority> authorities;

    public RestAuthenticateResponse(String token, Set<RestAuthority> authorities) {
        this.token = token;
        this.authorities = authorities;
    }
}
