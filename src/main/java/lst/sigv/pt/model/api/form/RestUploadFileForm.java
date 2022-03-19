package lst.sigv.pt.model.api.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by Afonseca on 20/02/22
 */
public class RestUploadFileForm implements Serializable {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
