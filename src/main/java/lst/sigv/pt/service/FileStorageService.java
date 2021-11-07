package lst.sigv.pt.service;

import lst.sigv.pt.model.FileEntity;

/**
 * Created by Afonseca on 24/10/2021.
 **/
public interface FileStorageService {

    FileEntity saveFile(FileEntity file);

    FileEntity getFileById(Long fileId);


    String saveImageTemporary(byte[] content);
}
