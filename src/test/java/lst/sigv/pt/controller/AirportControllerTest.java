package lst.sigv.pt.controller;

import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest {
    @InjectMocks
    private AirportController airportController;
    @Mock
    private AirportService airportService;

    private String airportForm = "{\n" +
            "    \"icaoCode\":\"LPPT\",\n" +
            "    \"iataCode\": \"LPPT\",\n" +
            "    \"city\": \"Lisbon\",\n" +
            "    \"country\": \"Portugal\",\n" +
            "    \"latitude\": \"111\",\n" +
            "    \"longitude\": \"222\",\n" +
            "    \"name\": \"Humberto Delgado\",\n" +
            "    \"id\": \"1\"\n" +
            "}";

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();

    }


    @Test
    void getAllAirports() {
    }

    @Test
   public void addAirport() throws Exception {
        RestAirport restAirport = getRestAirport();
        when(airportController.addAirport(any(RestAirport.class))).thenReturn(restAirport);
        mockMvc.perform(post("/api/airport/create")
                .content(airportForm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is(restAirport.getCity())))
                .andExpect(jsonPath("$.country", is(restAirport.getCountry())))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.latitude", is("111")));
    }

    @Test
    public  void editAirport() throws Exception {
        RestAirport restAirport = getRestAirport();
        when(airportController.editAirport(any(RestAirport.class))).thenReturn(restAirport);
        mockMvc.perform(put("/api/airport/edit")
                .content(airportForm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is(restAirport.getCity())))
                .andExpect(jsonPath("$.country", is(restAirport.getCountry())))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.latitude", is("111")));

    }

    @Test
    public void deleteAirport() throws Exception {
        mockMvc.perform(post("/api/airport/delete/111111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private RestAirport getRestAirport() {
        RestAirport restAirport = new RestAirport();
        restAirport.setCity("Lisbon");
        restAirport.setCountry("Portugal");
        restAirport.setIataCode("LPPT");
        restAirport.setIcaoCode("LPPT");
        restAirport.setName("Humberto Delgado");
        restAirport.setId("1");
        restAirport.setLatitude("111");
        restAirport.setLongitude("222");
        return restAirport;
    }

    private AirportEntity getAirportEntity() {
        AirportEntity restAirport = new AirportEntity();
        restAirport.setCity("Lisbon");
        restAirport.setCountry("Portugal");
        restAirport.setIataCode("LPPT");
        restAirport.setIcaoCode("LPPT");
        restAirport.setId(1L);
        restAirport.setLatitude("111");
        restAirport.setLongitude("222");
        return restAirport;
    }
}
