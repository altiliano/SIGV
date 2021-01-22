package lst.sigv.pt.service;

import lst.sigv.pt.model.api.RestAuthority;
import lst.sigv.pt.model.api.RestUser;

import java.util.List;

/**
 * Created by Afonseca on 22/01/21
 */
public interface AuthorityManagementService {
    List<RestAuthority> getAuthorities();
    void associateAuthority(RestUser user, RestAuthority authority);
}
