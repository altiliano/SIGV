package lst.sigv.pt.repository;

import lst.sigv.pt.model.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Afonseca on 22/01/21
 */
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
    AuthorityEntity findByRole(String role);
}
