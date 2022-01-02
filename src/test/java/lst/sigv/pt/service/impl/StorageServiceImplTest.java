package lst.sigv.pt.service.impl;

import lst.sigv.pt.exception.FileNotFoundException;
import lst.sigv.pt.model.FileEntity;
import lst.sigv.pt.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

/**
 * Created by Afonseca on 24/10/2021.
 **/
class StorageServiceImplTest {

    @InjectMocks
    private StorageServiceImpl storageService;
    @Mock
    private FileRepository fileRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveFile() {
        FileEntity file = new FileEntity();
        file.setName("newFile");
        file.setType("PROFILE_IMAGE");
        Mockito.when(fileRepository.save(any())).thenReturn(getFile());
        FileEntity result = storageService.saveFile(file);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("newFile", result.getName());
        Assertions.assertEquals("PROFILE_IMAGE", result.getType());

    }

    @Test
    void getFileById() {
        Mockito.when(fileRepository.findById(any())).thenReturn(Optional.of(getFile()));
        FileEntity result  = storageService.getFileById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("newFile", result.getName());
        Assertions.assertEquals("PROFILE_IMAGE", result.getType());
    }

    @Test
    void getFileByIdWithWrongId() {
        long id = 1L;
        Mockito.when(fileRepository.findById(id)).thenReturn(Optional.empty());
        try {
            storageService.getFileById(id);
        } catch (Exception ex) {
            if (ex instanceof  FileNotFoundException) {
                Assertions.assertTrue(true);
            }else {
                Assertions.fail();
            }
        }
    }

    @Test
    void deleteFile() {
        storageService.deleteFile(1L);
        Mockito.verify(fileRepository, times(1))
                .deleteById(any());
    }


    private FileEntity getFile () {
        FileEntity file = new FileEntity();
        file.setName("newFile");
        file.setType("PROFILE_IMAGE");
        file.setId(1L);
        return  file;
    }


}