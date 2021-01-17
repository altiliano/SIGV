package lst.sigv.pt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;

import java.security.SecureRandom;

/**
 * Created by Afonseca on 17/01/21
 */
@Configuration
public class TokenConfiguration {

    @Value("${token.serverSecret}")
    private String serverSecret;

    @Value("${token.serverInteger}")
    private Integer serverInteger;
    @Bean
    public TokenService getTokenService()   {
        KeyBasedPersistenceTokenService keyBasedPersistenceTokenService = new KeyBasedPersistenceTokenService();
        keyBasedPersistenceTokenService.setServerSecret(serverSecret);
        keyBasedPersistenceTokenService.setServerInteger(serverInteger);
        keyBasedPersistenceTokenService.setPseudoRandomNumberBytes(16);
        keyBasedPersistenceTokenService.setSecureRandom(new SecureRandom());
        return keyBasedPersistenceTokenService;
    }
}
