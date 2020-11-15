package lst.sigv.pt.model.api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Afonseca on 15/11/20
 */
@Getter
@Setter
public class RestAuthority implements Serializable {
    private String role;
}
