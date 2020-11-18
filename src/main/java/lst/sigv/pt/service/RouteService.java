package lst.sigv.pt.service;

import lst.sigv.pt.model.api.RestRoute;

/**
 * Created by Afonseca on 18/11/20
 */
public interface RouteService {
    RestRoute createRoute(RestRoute route);
    RestRoute updateRoute(RestRoute route);
}
