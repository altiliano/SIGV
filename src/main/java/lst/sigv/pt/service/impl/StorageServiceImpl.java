package lst.sigv.pt.service.impl;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.exception.FileNotFoundException;
import lst.sigv.pt.model.FileEntity;
import lst.sigv.pt.repository.FileRepository;
import lst.sigv.pt.service.FileStorageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Afonseca on 24/10/2021.
 **/
@Service
@Slf4j
public class StorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;

    private static Path tempFolder;

    public StorageServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileEntity saveFile(FileEntity file) {
       return fileRepository.save(file);
    }

    @Override
    public FileEntity getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(new FileNotFoundException("file with id: "+ fileId + " not found"));
    }

    public void  deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    @Override
    public  String saveImageTemporary(byte[] content) throws IllegalArgumentException {
        if (tempFolder == null) {
            tempFolder = createTemporaryFolder();
        }
        if (tempFolder == null) {
            throw new IllegalArgumentException("Can´t save image, because the temporary folder is null");
        }
        try {
            return Files.write(tempFolder, content).toFile().getAbsolutePath();
        } catch (IOException ex) {
            log.error("Error went trying to save image temporary", ex.getCause());
        }

        throw new IllegalArgumentException("Can´t not save image in the temporary folder");
    }

    private Path createTemporaryFolder () throws IllegalArgumentException {
        try {
            return Files.createTempDirectory("tmpDirPrefix");
        } catch (IOException ex) {
            log.error("Error went trying to save image temporary", ex.getCause());
        }
        throw new IllegalArgumentException("can´t create temporary folder");
    }

}
