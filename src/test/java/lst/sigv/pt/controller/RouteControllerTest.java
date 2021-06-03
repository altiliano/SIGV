package lst.sigv.pt.controller;

import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.RouteStatus;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestRouteForm;
import lst.sigv.pt.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Afonseca on 23/11/20
 */
@ExtendWith(MockitoExtension.class)
class RouteControllerTest {
    @InjectMocks
    private RouteController controller;

    @Mock
    private  RouteService routeService;

    private MockMvc mockMvc;

    private  final String routeId ="1";

    private static final String routeForm = "{\n" +
            "  \"depart\": {\n" +
            "    \"city\": \"Lisbon\",\n" +
            "    \"country\": \"Portugal\",\n" +
            "    \"iataCode\": \"LPPT\",\n" +
            "    \"icaoCode\": \"LPPT\",\n" +
            "    \"id\": \"1\",\n" +
            "    \"latitude\": \"1111\",\n" +
            "    \"longitude\": \"2222\",\n" +
            "    \"name\": \"teste name\"\n" +
            "  },\n" +
            "  \"destination\": {\n" +
            "    \"city\": \"Praia\",\n" +
            "    \"country\": \"Cape Verde\",\n" +
            "    \"iataCode\": \"GVNP\",\n" +
            "    \"icaoCode\": \"GVNP\",\n" +
            "    \"id\": \"2\",\n" +
            "    \"latitude\": \"111111\",\n" +
            "    \"longitude\": \"22222\",\n" +
            "    \"name\": \"Nelson Mandela\"\n" +
            "  },\n" +
            "  \"planes\": [\n" +
            "    {\n" +
            "      \"aircraftType\": \"B738\",\n" +
            "      \"id\": 44,\n" +
            "      \"name\": \"Teste\",\n" +
            "      \"photoUrl\": \"wwwww\",\n" +
            "      \"registration\": \"cccc\",\n" +
            "      \"status\": \"ACTIVE\",\n" +
            "      \"textureUrl\": \"wwwww\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void createRoute() throws Exception {
        RestRoute restRoute = getRoute();
        Mockito.when(controller.createRoute(any(RestRouteForm.class))).thenReturn(restRoute);
        mockMvc.perform(post("/api/route/create")
                .content(routeForm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart.icaoCode", is(restRoute.getDepart().getIcaoCode())))
                .andExpect(jsonPath("$.destinations", hasSize(1)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    @Test
    void updateRoute() throws Exception {
        RestRoute restRoute = getRoute();
        Mockito.when(controller.updateRoute(any(RestRoute.class))).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/update")
                .content(routeForm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart.icaoCode", is(restRoute.getDepart().getIcaoCode())))
                .andExpect(jsonPath("$.destinations", hasSize(restRoute.getDestinations().size())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.planes", hasSize(restRoute.getPlanes().size())));
    }

    @Test
    void getAllActiveRoute() {
    }

    @Test
    void deleteRoute() throws Exception {
        mockMvc.perform(post("/api/route/delete/"+routeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void activeRoute() throws Exception {
        RestRoute restRoute = getRoute();
        restRoute.setStatus(RouteStatus.ACTIVE);
        when(controller.activeRoute(routeId)).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/active/" + routeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart.icaoCode", is(restRoute.getDepart().getIcaoCode())))
                .andExpect(jsonPath("$.destinations", hasSize(1)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is(restRoute.getStatus().toString())))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    @Test
    void inactiveRoute() throws Exception {
        RestRoute restRoute = getRoute();
        restRoute.setStatus(RouteStatus.INACTIVE);
        when(controller.inactiveRoute(routeId)).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/inactive/"+routeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart.icaoCode", is(restRoute.getDepart().getIcaoCode())))
                .andExpect(jsonPath("$.destinations", hasSize(1)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is(restRoute.getStatus().toString())))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    private RestRoute getRoute() {
        RestRoute route = new RestRoute();
        route.setStatus(RouteStatus.INACTIVE);
        route.setDepart(getDepartAirport());
        route.getDestinations().add(getDepartAirport());
        route.setId(1L);
        route.setPlanes(getPlanes());
        return route;
    }

    private RestAirport getDepartAirport() {
       return RestAirport.builder()
               .city("Praia")
               .country("Cape Verde")
               .iataCode("GVNP")
               .icaoCode("GVNP")
               .latitude("11111111")
               .longitude("2222222")
               .build();

    }

    private RestAirport getDestinationAirport() {
        return RestAirport.builder()
                .city("Lisbon")
                .country("Portugal")
                .iataCode("LPPT")
                .icaoCode("LPPT")
                .latitude("11111111")
                .longitude("2222222")
                .build();

    }

    private Set<RestPlane> getPlanes() {
        Set<RestPlane> planes = new HashSet<>();
        RestPlane plane1 = new RestPlane();
        RestPlane plane2 = new RestPlane();
        plane1.setStatus(PlaneStatus.ACTIVE);
        plane1.setRegistration("CS-CST");
        plane1.setName("Rui Gomes");
        plane2.setTextureUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        plane2.setPhotoUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        plane1.setId(1L);
        plane1.setAircraftType("B737-800");

        plane2.setStatus(PlaneStatus.ACTIVE);
        plane2.setRegistration("CS-TST");
        plane2.setName("Rui Monteiro");
        plane2.setTextureUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        plane2.setPhotoUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        plane2.setId(2L);
        plane2.setAircraftType("B757-200");

        planes.add(plane2);
        planes.add(plane1);
        return planes;
    }
}