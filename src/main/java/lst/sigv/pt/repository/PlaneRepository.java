package lst.sigv.pt.repository;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Afonseca on 17/11/20
 */
public interface PlaneRepository extends PagingAndSortingRepository<PlaneEntity, Long> {
    boolean existsByRegistration(String registration);

    Page<PlaneEntity> findAllByStatus(PlaneStatus status, Pageable pageable);
}
