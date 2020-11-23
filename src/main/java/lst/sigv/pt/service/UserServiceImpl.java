package lst.sigv.pt.service;

import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Afonseca on 13/11/20
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse( null);
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }


}
