package lst.sigv.pt.model.api;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Afonseca on 18/11/20
 */
@Data
public class RestPlaneForm implements Serializable {
    private String name;
    private String registration;
    private String photoUrl;
    private String textureUrl;
    private String aircraftType;
}
