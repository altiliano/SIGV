package lst.sigv.pt.service;

import lst.sigv.pt.exception.InvalidPlaneStatusException;
import lst.sigv.pt.exception.InvalidRouteStatusException;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.RouteStatus;
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
        RouteEntity entity = getRouteById(routeId);
        if (!isValidStatus(entity.getStatus(), RouteStatus.ACTICE)) {
            throw new InvalidRouteStatusException("Can not active route with id: " + routeId);
        }
        entity.setStatus(RouteStatus.ACTICE);
        return routeMapper.routeEntityToRestRoute(routeRepository.save(entity));
    }

    @Override
    public RestRoute inactiveRoute(String routeId) {
        RouteEntity routeEntity = getRouteById(routeId);
        if (!isValidStatus(routeEntity.getStatus(), RouteStatus.INACTIVE)) {
            throw new InvalidRouteStatusException("Can not inactive route with id: " + routeId);
        }
        routeEntity.setStatus(RouteStatus.INACTIVE);
        return routeMapper.routeEntityToRestRoute(routeRepository.save(routeEntity));
    }

    @Override
    public List<RestRoute> getAllAvailableRoute() {
        List<RestRoute> routes = new ArrayList<>();
        routeRepository.findAll().forEach(routeEntity -> routes.add(routeMapper.routeEntityToRestRoute(routeEntity)));
        return routes;
    }

    @Override
    public List<RestRoute> getAllActiveRoute() {
        List<RestRoute> routes = new ArrayList<>();
        routeRepository.findAllByStatus(RouteStatus.ACTICE).forEach(routeEntity -> routes.add(routeMapper.routeEntityToRestRoute(routeEntity)));
        return routes;
    }

    private RouteEntity getRouteById(String routeId) {
        return routeRepository.findById(Long.valueOf(routeId)).orElse(null);
    }


    @Override
    public void deleteRoute(String routeId) {
        RouteEntity routeEntity = routeRepository.findById(Long.valueOf(routeId)).orElse(null);
        if (!isValidStatus(routeEntity.getStatus(), RouteStatus.DELETE)) {
            throw new InvalidRouteStatusException("can not delete route with id: " + routeId);
        }
        routeEntity.setStatus(RouteStatus.DELETE);
        routeRepository.save(routeEntity);
    }

    @Override
    public RestRoute findRouteById(String routeId) {
        return routeMapper.routeEntityToRestRoute(getRouteById(routeId));
    }

    public boolean isValidStatus(RouteStatus currentStatus, RouteStatus newRouteStatus) {
        if (currentStatus.equals(RouteStatus.DELETE)) {
            return false;
        } else {
            if (currentStatus.equals(newRouteStatus)) {
                return false;
            }
        }
        return true;
    }
}
