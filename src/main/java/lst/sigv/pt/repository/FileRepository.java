package lst.sigv.pt.repository;

import lst.sigv.pt.model.FileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Afonseca on 24/10/2021.
 **/
public interface FileRepository extends PagingAndSortingRepository<FileEntity,Long> {
}
