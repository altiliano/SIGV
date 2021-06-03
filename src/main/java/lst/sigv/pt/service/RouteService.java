package lst.sigv.pt.service;

import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestRouteForm;

import java.util.List;

/**
 * Created by Afonseca on 18/11/20
 */
public interface RouteService {
    RestRoute createRoute(RestRouteForm form);
    RestRoute updateRoute(RestRoute route);
    RestRoute activeRoute(String routeId);
    RestRoute inactiveRoute(String routeId);
    List<RestRoute> getAllAvailableRoute();
    List<RestRoute> getAllActiveRoute();
    void deleteRoute(String routeId);
    RestRoute findRouteById(String routeId);
}
