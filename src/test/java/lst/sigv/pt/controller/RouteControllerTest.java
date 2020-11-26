package lst.sigv.pt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.RouteStatus;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestRouteForm;
import lst.sigv.pt.service.RouteService;
import lst.sigv.pt.service.mapper.RouteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Afonseca on 23/11/20
 */
class RouteControllerTest {
    @InjectMocks
    private RouteController controller;
    @Mock
    private RouteService routeService;
    @Mock
    private RouteMapper routeMapper;

    private MockMvc mockMvc;

    private static final String routeForm = "{\n" +
            "    \"depart\": \"LPPT\",\n" +
            "    \"destination\": \"GVNP\",\n" +
            "    \"planes\": [\n" +
            "        {\n" +
            "            \"name\": \"Rui Gome\",\n" +
            "            \"registration\": \"CS-CST\",\n" +
            "            \"photoUrl\": \"https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg\",\n" +
            "            \"textureUrl\": \"https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg\",\n" +
            "            \"aircraftType\": \"B737-800\"\n" +
            "        },\n" +
            "          {\n" +
            "            \"name\": \"Rui Gome\",\n" +
            "            \"registration\": \"CS-TST\",\n" +
            "            \"photoUrl\": \"https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg\",\n" +
            "            \"textureUrl\": \"https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg\",\n" +
            "            \"aircraftType\": \"B757-200\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createRoute() throws Exception {
        RestRoute restRoute = getRoute();
        ObjectMapper objectMapper = new ObjectMapper();
        RestRouteForm restRouteForm = objectMapper.readValue(routeForm, RestRouteForm.class);

        when(routeMapper.restRouteFormToRestRoute(restRouteForm)).thenReturn(restRoute);
        when(routeService.createRoute(restRoute)).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/create")
                .content(routeForm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart", is(restRoute.getDepart())))
                .andExpect(jsonPath("$.destination", is(restRoute.getDestination())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    @Test
    void updateRoute() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RestRoute restRoute = objectMapper.readValue(routeForm, RestRoute.class);
        when(routeService.updateRoute(restRoute)).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/update")
                .content(routeForm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart", is(restRoute.getDepart())))
                .andExpect(jsonPath("$.destination", is(restRoute.getDestination())))
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    @Test
    void getAllActiveRoute() {
    }

    @Test
    void deleteRoute() throws Exception {
        mockMvc.perform(post("/api/route/delete")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void activeRoute() throws Exception {
        RestRoute restRoute = getRoute();
        restRoute.setStatus(RouteStatus.ACTICE);
        when(routeService.activeRoute("1")).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/active")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart", is(restRoute.getDepart())))
                .andExpect(jsonPath("$.destination", is(restRoute.getDestination())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is(restRoute.getStatus().toString())))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    @Test
    void inactiveRoute() throws Exception {
        RestRoute restRoute = getRoute();
        restRoute.setStatus(RouteStatus.INACTIVE);
        when(routeService.inactiveRoute("1")).thenReturn(restRoute);

        mockMvc.perform(post("/api/route/inactive")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depart", is(restRoute.getDepart())))
                .andExpect(jsonPath("$.destination", is(restRoute.getDestination())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is(restRoute.getStatus().toString())))
                .andExpect(jsonPath("$.planes", hasSize(2)));
    }

    private RestRoute getRoute() {
        RestRoute route = new RestRoute();
        route.setStatus(RouteStatus.INACTIVE);
        route.setDepart("LPPT");
        route.setDestination("GVNP");
        route.setId(1L);
        route.setPlanes(getPlanes());
        return route;
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