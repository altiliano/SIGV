package lst.sigv.pt.service.impl;

import lombok.RequiredArgsConstructor;
import lst.sigv.pt.model.AuthorityEntity;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Afonseca on 13/11/20
 */
@RequiredArgsConstructor
@Service
public class LstUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findUserByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("user with username: " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), convertSpringAuthorities(userEntity.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertSpringAuthorities(Set<AuthorityEntity> authorities) {
        if (authorities != null && authorities.size() > 0) {
            return authorities.stream()
                    .map(AuthorityEntity::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
