package lst.sigv.pt.model.api;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Afonseca on 22/11/2020.
 **/

public class RestRouteForm implements Serializable {
    private RestAirport depart;
    private List<RestAirport> destination;
    private Set<RestPlane> planes;

    public RestAirport getDepart() {
        return depart;
    }

    public RestRouteForm setDepart(RestAirport depart) {
        this.depart = depart;
        return this;
    }

    public List<RestAirport> getDestination() {
        return destination;
    }

    public RestRouteForm setDestination(List<RestAirport> destination) {
        this.destination = destination;
        return this;
    }

    public Set<RestPlane> getPlanes() {
        return planes;
    }

    public RestRouteForm setPlanes(Set<RestPlane> planes) {
        this.planes = planes;
        return this;
    }
}
