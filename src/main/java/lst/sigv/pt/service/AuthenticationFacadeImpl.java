package lst.sigv.pt.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Afonseca on 06/03/21
 */
@Component
public class AuthenticationFacadeImpl  implements  AuthenticationFacade{
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
