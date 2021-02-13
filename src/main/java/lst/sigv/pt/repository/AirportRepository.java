package lst.sigv.pt.repository;

import lst.sigv.pt.model.AirportEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Afonseca on 12/02/21
 */
public interface AirportRepository extends CrudRepository<AirportEntity, Long> {

    AirportEntity findByIcaoCode(String icaoCode);
}
