package lst.sigv.pt.service;

import lst.sigv.pt.model.FileEntity;
import lst.sigv.pt.model.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Afonseca on 13/11/20
 */
public interface UserService {
    UserEntity findUserByUsername(String username);

    UserEntity saveUser(UserEntity userEntity);

    UserEntity findUserByEmail(String email);

    UserEntity findUserById(Long userId);

    void activeUser(String email);

    UserEntity createUser(UserEntity userEntity);

    UserEntity addPhoto(MultipartFile file, Long userId) throws IOException;
}
