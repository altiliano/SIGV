package lst.sigv.pt.service.impl;

import lst.sigv.pt.exception.InvalidUserStatusException;
import lst.sigv.pt.exception.UserAlreadyExitException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.UserStatus;
import lst.sigv.pt.repository.UserRepository;
import lst.sigv.pt.service.UserService;
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
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("user with username:  " + username + " not found");
        }
        return user;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void activeUser(String email) {
        UserEntity user = findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("user with email: " + email + " not found");
        }
        if (!user.getStatus().equals(UserStatus.ACTIVE) || !user.getStatus().equals(UserStatus.DELETED)) {
            user.setStatus(UserStatus.ACTIVE);
            saveUser(user);
        }
        throw new InvalidUserStatusException("invalid user status");
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if (findUserByEmail(userEntity.getEmail()) != null) {
            throw new UserAlreadyExitException("user whit email: " + userEntity.getEmail() + "already exist");
        }
        return saveUser(userEntity);
    }


}
