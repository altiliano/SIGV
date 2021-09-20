package lst.sigv.pt.repository;

import lst.sigv.pt.model.BookingEntity;
import lst.sigv.pt.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Afonseca on 18/09/21
 */
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {

    Optional<BookingEntity> findByUser(UserEntity userEntity);
}
