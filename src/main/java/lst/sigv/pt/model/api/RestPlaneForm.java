package lst.sigv.pt.model.api;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Afonseca on 18/11/20
 */
@Data
public class RestPlaneForm implements Serializable {
    @NotBlank
    private String name;
    @NotBlank
    private String registration;
    @NotBlank
    @URL
    private String photoUrl;
    @URL
    private String textureUrl;
    @NotBlank
    private String aircraftType;
}
