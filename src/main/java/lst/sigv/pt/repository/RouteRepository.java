package lst.sigv.pt.repository;

import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.RouteStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Afonseca on 18/11/20
 */
public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
    Iterable<RouteEntity> findAllByStatus(RouteStatus status);
}
