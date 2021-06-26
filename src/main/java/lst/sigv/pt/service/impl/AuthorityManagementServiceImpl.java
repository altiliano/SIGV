package lst.sigv.pt.service.impl;

import lst.sigv.pt.model.AuthorityEntity;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.RestAuthority;
import lst.sigv.pt.model.api.RestUser;
import lst.sigv.pt.repository.AuthorityRepository;
import lst.sigv.pt.service.AuthorityManagementService;
import lst.sigv.pt.service.UserService;
import lst.sigv.pt.service.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afonseca on 22/01/21
 */
@Service
public class AuthorityManagementServiceImpl implements AuthorityManagementService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final UserService userService;

    public AuthorityManagementServiceImpl(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper, UserService userService) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.userService = userService;
    }

    @Override
    public List<RestAuthority> getAuthorities() {
        List<RestAuthority> authorities = new ArrayList<>();
        authorityRepository.findAll().forEach(authorityEntity -> authorities.add(authorityMapper.authorityEntityToRestAuthority(authorityEntity)));
        return authorities;
    }

    @Transactional
    @Override
    public void associateAuthority(RestUser user, RestAuthority authority) {
        AuthorityEntity authorityEntity = authorityRepository.findByRole(authority.getRole());
        UserEntity userEntity = userService.findUserByEmail(user.getEmail());
        if (userEntity != null) {
            userEntity.getAuthorities().add(authorityEntity);
            userService.saveUser(userEntity);
        }

    }

    @Override
    public void addAuthorities(List<RestAuthority> authorities) {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        for (RestAuthority authority : authorities) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setRole(authority.getRole());
            authorityEntities.add(authorityEntity);
        }
        authorityRepository.saveAll(authorityEntities);
    }
}
