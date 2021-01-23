package lst.sigv.pt.repository;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Afonseca on 17/11/20
 */
public interface PlaneRepository extends CrudRepository<PlaneEntity, Long> {
    boolean existsByRegistration(String registration);

    Iterable<PlaneEntity> findAllByStatus(PlaneStatus status);
}
