package lst.sigv.pt.repository;

import lst.sigv.pt.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Afonseca on 13/11/20
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
