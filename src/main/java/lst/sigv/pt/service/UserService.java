package lst.sigv.pt.service;

import lst.sigv.pt.model.UserEntity;

/**
 * Created by Afonseca on 13/11/20
 */
public interface UserService {
    UserEntity findUserByUsername(String username);

    UserEntity saveUser(UserEntity userEntity);
}
