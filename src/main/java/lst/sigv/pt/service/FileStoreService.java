package lst.sigv.pt.service;


import lst.sigv.pt.model.FileEntity;

public interface FileStoreService {

    void upload(FileEntity fileEntity);

    FileEntity getFileById(long id);

    void deleteFileById(long id);
}
