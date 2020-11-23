package lst.sigv.pt.model.api;

import lombok.Data;
import lst.sigv.pt.model.PlaneStatus;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Afonseca on 17/11/20
 */
@Data
public class RestPlane implements Serializable {
    private long id;
    private String name;
    private String registration;
    private String aircraftType;
    private String photoUrl;
    private String textureUrl;
    private PlaneStatus status;
}
