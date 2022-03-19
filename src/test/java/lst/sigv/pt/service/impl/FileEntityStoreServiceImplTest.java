package lst.sigv.pt.service.impl;

import lst.sigv.pt.model.FileEntity;
import lst.sigv.pt.repository.FileStoreRepository;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing FileStoreService")
public class FileEntityStoreServiceImplTest {
    @InjectMocks
    private FileStoreServiceImpl fileStoreService;

    @Mock
    private FileStoreRepository fileStoreRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void uploadFile() {
        FileEntity fileEntity = getFile();
        Mockito.when(fileStoreRepository.save(fileEntity)).thenReturn(fileEntity);
         fileStoreService.upload(fileEntity);

        Assert.assertEquals(fileEntity.getName(), fileEntity.getName());
        Assert.assertEquals(fileEntity.getType(), fileEntity.getType());
        Assert.assertArrayEquals(fileEntity.getContent(), fileEntity.getContent());
    }

    @Test
    public void getFileById() {
        Mockito.when(fileStoreRepository.findById(1L)).thenReturn(Optional.of(getFile()));
        FileEntity file = fileStoreService.getFileById(1L);
        Assert.assertNotNull(file);
        Assert.assertNotNull(file.getContent());
        Assert.assertNotNull(file.getType());
        Assert.assertNotNull(file.getName());
    }

    private FileEntity getFile() {
        File file =  new File("src/test/resources/file/file.png");
        FileEntity fileEntity = new FileEntity();
        fileEntity.setContent(file.getPath().getBytes());
        fileEntity.setName(file.getName());
        fileEntity.setType(FilenameUtils.getExtension(file.getName()));
        return  fileEntity;
    }
}
