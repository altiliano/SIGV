package lst.sigv.pt.service;

import org.springframework.security.core.Authentication;

/**
 * Created by Afonseca on 06/03/21
 */
public interface AuthenticationFacade {

    Authentication getAuthentication();
}
