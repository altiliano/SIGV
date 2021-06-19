package lst.sigv.pt.repository;

import lst.sigv.pt.model.AirportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Afonseca on 12/02/21
 */
public interface AirportRepository extends PagingAndSortingRepository<AirportEntity, Long> {

    AirportEntity findByIcaoCode(String icaoCode);
    AirportEntity findAirportEntityById(long id);
}
