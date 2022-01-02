package lst.sigv.pt.repository;

import lst.sigv.pt.model.FileEntity;
import org.springframework.data.repository.CrudRepository;

public interface FileStoreRepository  extends CrudRepository<FileEntity, Long> {
}
