package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestRoute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Afonseca on 18/11/20
 */
class RouteMapperImplTest {
    private RouteMapper routeMapper;
    private AirportMapper airportMapper;

    @BeforeEach
    void setUp() {
        routeMapper = new RouteMapperImpl();
        airportMapper = new AirportMapperImpl();
    }

    @Test
    void routeEntityToRestRoute() {
        RouteEntity routeEntity = getRouteEntity();
        RestRoute restRoute = routeMapper.routeEntityToRestRoute(routeEntity);
        Assertions.assertEquals(restRoute.getDepart(), routeEntity.getDepart());
        Assertions.assertEquals(restRoute.getDestinations().size(), routeEntity.getDestinations().size());
        Assertions.assertEquals(restRoute.getId(), routeEntity.getId());
        Assertions.assertEquals(restRoute.getPlanes().size(), routeEntity.getPlanes().size());
    }

    @Test
    void restRouteToRouteEntity() {
        RestRoute restRoute = getRestRoute();
        RouteEntity routeEntity = routeMapper.restRouteToRouteEntity(restRoute);
        Assertions.assertEquals(restRoute.getDepart(), routeEntity.getDepart());
        Assertions.assertEquals(restRoute.getDestinations().size(), routeEntity.getDestinations().size());
        Assertions.assertEquals(restRoute.getId(), routeEntity.getId());
        Assertions.assertEquals(restRoute.getPlanes().size(), routeEntity.getPlanes().size());

    }

    RouteEntity getRouteEntity() {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(1L);
        routeEntity.getDestinations().add(getAirport());

        routeEntity.setDepart(getAirport());
        routeEntity.setPlanes(getAvailablePlane());
        return routeEntity;
    }

    AirportEntity getAirport() {

        AirportEntity airportEntity = new AirportEntity();
        airportEntity.setId(1L);
        airportEntity.setName("Nelson Mandela");
        airportEntity.setCity("Praia");
        airportEntity.setCountry("Cape Verde");
        airportEntity.setIataCode("GVNP");
        airportEntity.setIcaoCode("GVNP");
        airportEntity.setLongitude("11111111");
        airportEntity.setLatitude("22222222");

        return airportEntity;

    }

    private Set<PlaneEntity> getAvailablePlane() {
        Set<PlaneEntity> planes = new HashSet<>();
        PlaneEntity planeEntity = new PlaneEntity();
        planeEntity.setStatus(PlaneStatus.ACTIVE);
        planeEntity.setRegistration("CS-TST");
        planeEntity.setName("Miguel Fonseca");
        planeEntity.setId(1L);
        planeEntity.setAircraftType("B738");
        planes.add(planeEntity);
        return planes;
    }


    RestRoute getRestRoute() {
        RestRoute restRoute = new RestRoute();
        restRoute.setId(1L);
        restRoute.getDestinations().add(airportMapper.airportEntityToRestAirport(getAirport()));
        restRoute.setDepart(airportMapper.airportEntityToRestAirport(getAirport()));
        restRoute.setPlanes(getAvailableRestPlane());
        return restRoute;
    }

    private Set<RestPlane> getAvailableRestPlane() {
        Set<RestPlane> planes = new HashSet<>();
        RestPlane restPlane = new RestPlane();
        restPlane.setStatus(PlaneStatus.ACTIVE);
        restPlane.setRegistration("CS-TST");
        restPlane.setName("Miguel Fonseca");
        restPlane.setId(1L);
        restPlane.setAircraftType("B738");
        planes.add(restPlane);
        return planes;
    }
}