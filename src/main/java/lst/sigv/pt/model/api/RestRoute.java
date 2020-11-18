package lst.sigv.pt.model.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Afonseca on 18/11/20
 */
@Data
public class RestRoute implements Serializable {

    private long id;
    private String depart;
    private String destination;
    private Set<RestPlane> planes;

}