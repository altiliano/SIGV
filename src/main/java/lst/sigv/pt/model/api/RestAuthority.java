package lst.sigv.pt.model.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Afonseca on 15/11/20
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
public class RestAuthority implements Serializable {
    private String role;

    public RestAuthority(String role) {
        this.role = role;
    }
}
