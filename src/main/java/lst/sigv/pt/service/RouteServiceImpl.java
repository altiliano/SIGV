package lst.sigv.pt.service;

import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.repository.RouteRepository;
import lst.sigv.pt.service.mapper.RouteMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afonseca on 18/11/20
 */
@Service
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    @Override
    public RestRoute createRoute(RestRoute route) {
        return saveRoute(routeMapper.restRouteToRouteEntity(route));
    }

    @Override
    public RestRoute updateRoute(RestRoute route) {
        return saveRoute(routeMapper.restRouteToRouteEntity(route));
    }

    private RestRoute saveRoute(RouteEntity routeEntity) {
        return routeMapper.routeEntityToRestRoute(routeRepository.save(routeEntity));
    }

    @Override
    public RestRoute activeRoute(String routeId) {
        return null;
    }

    @Override
    public RestRoute inactiveRoute(String routeId) {
        return null;
    }

    @Override
    public List<RestRoute> getAllAvailableRoute() {
        List<RestRoute> routes = new ArrayList<>();
        routeRepository.findAll().forEach(routeEntity -> routes.add(routeMapper.routeEntityToRestRoute(routeEntity)));
        return routes;
    }

    private RestRoute getRouteById(String routeId) {
        RouteEntity routeEntity = routeRepository.findById(Long.valueOf(routeId)).orElse(null);
        if (routeEntity == null) {
            return null;
        }
        return routeMapper.routeEntityToRestRoute(routeEntity);
    }
}
