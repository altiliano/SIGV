package lst.sigv.pt.service.impl;

import lst.sigv.pt.exception.InvalidUserStatusException;
import lst.sigv.pt.exception.UserAlreadyExitException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.FileEntity;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.UserStatus;
import lst.sigv.pt.repository.UserRepository;
import lst.sigv.pt.service.FileStoreService;
import lst.sigv.pt.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

/**
 * Created by Afonseca on 13/11/20
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileStoreService fileStoreService;

    public UserServiceImpl(UserRepository userRepository, FileStoreService fileStoreService) {
        this.userRepository = userRepository;
        this.fileStoreService = fileStoreService;
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


    @Override
    public UserEntity findUserById(Long userId) {
       return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    @Override
    public UserEntity addPhoto(MultipartFile file, Long userId) throws IOException, UserNotFoundException {
        UserEntity user = findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("cannot upload profile photo.Because user with id: {} not found" + userId);
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getName());
        fileEntity.setContent(file.getBytes());
        fileEntity.setType(file.getContentType());

        fileStoreService.upload(fileEntity);

        user.setProfilePhotoId(fileEntity.getId());
        return user;
    }
}
