package lst.sigv.pt.repository;

import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.RouteStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Afonseca on 18/11/20
 */
public interface RouteRepository extends PagingAndSortingRepository<RouteEntity, Long> {
    Iterable<RouteEntity> findAllByStatus(RouteStatus status);
}
