package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.BookingEntity;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.api.RestBooking;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestRoute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Afonseca on 18/11/20
 */
class PlaneMapperImplTest {

    PlaneMapperImpl planeMapper = new PlaneMapperImpl();

    @Test
    void planeEntityToRestPlane() {
        PlaneEntity planeEntity = getPlaneEntity();
        RestPlane restPlane = planeMapper.planeEntityToRestPlane(planeEntity);
        Assertions.assertEquals(planeEntity.getAircraftType(), restPlane.getAircraftType());
        Assertions.assertEquals(planeEntity.getId(), restPlane.getId());
        Assertions.assertEquals(planeEntity.getName(), restPlane.getName());
        Assertions.assertEquals(planeEntity.getRegistration(), restPlane.getRegistration());
        Assertions.assertEquals(planeEntity.getStatus(), restPlane.getStatus());
        Assertions.assertEquals(planeEntity.getPhotoUrl(), restPlane.getPhotoUrl());

    }

    @Test
    void restPlaneToPlaneEntity() {
        RestPlane restPlane = getRestPlane();
        PlaneEntity planeEntity = planeMapper.restPlaneToPlaneEntity(restPlane);

        Assertions.assertEquals(planeEntity.getAircraftType(), planeEntity.getAircraftType());
        Assertions.assertEquals(planeEntity.getId(), planeEntity.getId());
        Assertions.assertEquals(planeEntity.getName(), planeEntity.getName());
        Assertions.assertEquals(planeEntity.getRegistration(), planeEntity.getRegistration());
        Assertions.assertEquals(planeEntity.getStatus(), planeEntity.getStatus());
        Assertions.assertEquals(planeEntity.getPhotoUrl(), planeEntity.getPhotoUrl());
        Assertions.assertEquals(planeEntity.getBookings().size(), planeEntity.getBookings().size());
        //Assertions.assertEquals(planeEntity.getRoutes().size(), planeEntity.getRoutes().size());

    }

    PlaneEntity getPlaneEntity() {
        PlaneEntity planeEntity = new PlaneEntity();
        planeEntity.setAircraftType("B772");
        planeEntity.setId(1L);
        planeEntity.setName("Miguel Gomes");
        planeEntity.setRegistration("CS-TST");
        //planeEntity.setRoutes(getRoutes());
        planeEntity.setBookings(getBookings());
        planeEntity.setBookings(getBookings());
        planeEntity.setStatus(PlaneStatus.ACTIVE);
        return planeEntity;

    }

    RestPlane getRestPlane() {
        RestPlane restPlane = new RestPlane();
        restPlane.setAircraftType("B772");
        restPlane.setId(1L);
        restPlane.setName("Miguel Gomes");
        restPlane.setRegistration("CS-TST");
        restPlane.setStatus(PlaneStatus.ACTIVE);
        return restPlane;
    }

    private Set<BookingEntity> getBookings() {
        Set<BookingEntity> bookings = new HashSet<>();
        BookingEntity booking = new BookingEntity();
        booking.setId(1L);
        return bookings;
    }

    Set<RouteEntity> getRoutes() {
        Set<RouteEntity> routeEntities = new HashSet<>();
        RouteEntity routeEntity = new RouteEntity();
        //routeEntity.setDepart("LPPT");
        //routeEntity.setDestination("LPPR");
        routeEntity.setId(1L);
        routeEntities.add(routeEntity);
        return routeEntities;
    }

    private Set<RestBooking> getRestBookings() {
        Set<RestBooking> bookings = new HashSet<>();
        RestBooking booking = new RestBooking();
        booking.setId(1L);
        return bookings;
    }

    Set<RestRoute> getRestRoutes() {
        Set<RestRoute> routes = new HashSet<>();
        RestRoute route = new RestRoute();
        //route.setDepart("LPPT");
        //route.setDestination("LPPR");
        route.setId(1L);
        routes.add(route);
        return routes;
    }


}