package lst.sigv.pt.model.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Afonseca on 22/11/2020.
 **/
@Data
public class RestRouteForm implements Serializable {
    private String depart;
    private String destination;
    private Set<RestPlane> planes;
}
