package lst.sigv.pt.service.impl;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.model.FileEntity;
import lst.sigv.pt.repository.FileStoreRepository;
import lst.sigv.pt.service.FileStoreService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileStoreServiceImpl  implements FileStoreService {

   private final FileStoreRepository fileStoreRepository;

    public FileStoreServiceImpl(FileStoreRepository fileStoreRepository) {
        this.fileStoreRepository = fileStoreRepository;
    }

    @Override
    public FileEntity upload(FileEntity fileEntity) {
        return fileStoreRepository.save(fileEntity);
    }

    @Override
    public FileEntity getFileById(long id) {
        return fileStoreRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFileById(long id) {
        fileStoreRepository.deleteById(id);
    }
}
