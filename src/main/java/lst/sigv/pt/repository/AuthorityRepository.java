package lst.sigv.pt.repository;

import lst.sigv.pt.model.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Afonseca on 22/01/21
 */
public interface AuthorityRepository extends PagingAndSortingRepository<AuthorityEntity, Long> {
    AuthorityEntity findByRole(String role);
}
