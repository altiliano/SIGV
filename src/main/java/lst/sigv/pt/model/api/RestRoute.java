package lst.sigv.pt.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lst.sigv.pt.model.RouteStatus;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Afonseca on 18/11/20
 */
@Data
public class RestRoute implements Serializable {
    private long id;
    private RestAirport depart;
    private Set<RestAirport> destinations = new HashSet<>();
    private RouteStatus status;
    private Set<RestPlane> planes;

}
