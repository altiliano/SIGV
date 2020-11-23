package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestRouteForm;
import org.mapstruct.Mapper;

/**
 * Created by Afonseca on 18/11/20
 */
@Mapper(componentModel = "spring")
public interface RouteMapper {
    RestRoute routeEntityToRestRoute(RouteEntity routeEntity);
    RouteEntity restRouteToRouteEntity(RestRoute route);
    RestRoute restRouteFormToRestRoute(RestRouteForm form);
}
