package lst.sigv.pt.service.impl;

import lst.sigv.pt.exception.AirportAlreadyExistException;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.model.api.RestPageRequest;
import lst.sigv.pt.model.api.RestPageResult;
import lst.sigv.pt.repository.AirportRepository;
import lst.sigv.pt.service.AirportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by Afonseca on 12/02/21
 */
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
class AirportServiceImplTest {

    @Autowired
    AirportService airportService;

    @Autowired
    AirportRepository airportRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addAirport() {
        RestAirport restAirport  = RestAirport.builder()
                .country("Portugal")
                .iataCode("LPFR")
                .icaoCode("LPFR")
                .latitude("1111")
                .longitude("2222")
                .name("Humberto Delgado")
                .city("Faro").build();
        RestAirport savedAirport = airportService.addAirport(restAirport);
        Assertions.assertNotNull(savedAirport.getCity());
        Assertions.assertNotNull(savedAirport.getCountry());
        Assertions.assertNotNull(savedAirport.getIataCode());
        Assertions.assertNotNull(savedAirport.getId());
        Assertions.assertNotNull(savedAirport.getName());
        Assertions.assertNotNull(savedAirport.getLatitude());
        Assertions.assertNotNull(savedAirport.getLongitude());

        Assertions.assertEquals(restAirport.getName(), savedAirport.getName());
        Assertions.assertEquals(restAirport.getCity(), savedAirport.getCity());
        Assertions.assertEquals(restAirport.getCountry(), savedAirport.getCountry());
        Assertions.assertEquals(restAirport.getLatitude(), savedAirport.getLatitude());
        Assertions.assertEquals(restAirport.getLongitude(), savedAirport.getLongitude());
    }

    @Test
   public  void editAirport() {

        RestAirport restAirport  = RestAirport.builder()
                .country("Portugal")
                .iataCode("LPMA")
                .icaoCode("LPMA")
                .latitude("1111")
                .longitude("2222")
                .name("Cristiano Ronaldo")
                .city("Funchal").build();
        restAirport = airportService.addAirport(restAirport);
        airportService.findAirportById(restAirport.getId());

        Assertions.assertNotNull(restAirport);
        Assertions.assertNotNull(restAirport.getCity());
        Assertions.assertNotNull(restAirport.getCountry());
        Assertions.assertNotNull(restAirport.getIataCode());
        Assertions.assertNotNull(restAirport.getId());
        Assertions.assertNotNull(restAirport.getName());
        Assertions.assertNotNull(restAirport.getLatitude());
        Assertions.assertNotNull(restAirport.getLongitude());

        restAirport.setCity("Faro");

        RestAirport editedAirport = airportService.updateAirport(restAirport);

        Assertions.assertNotNull(restAirport.getCity());
        Assertions.assertNotNull(restAirport.getCountry());
        Assertions.assertNotNull(restAirport.getIataCode());
        Assertions.assertNotNull(restAirport.getId());
        Assertions.assertNotNull(restAirport.getName());
        Assertions.assertNotNull(restAirport.getLatitude());
        Assertions.assertNotNull(restAirport.getLongitude());
        Assertions.assertEquals(restAirport.getCity(), editedAirport.getCity());

    }

    @Test
    public void testAlreadyExistAirport() {
        RestAirport restAirport = RestAirport.builder()
                .country("Portugal")
                .iataCode("LPPT")
                .icaoCode("LPPT")
                .latitude("1111")
                .longitude("2222")
                .name("Humberto Delgado")
                .city("lIS").build();
        try {
             airportService.addAirport(restAirport);
        } catch (AirportAlreadyExistException exception) {
            Assertions.assertNotNull(exception.getMessage());
        }
    }



    @Test
    void getAirports() {
        Page<RestAirport> result = airportService.getAllAirports(new RestPageRequest(0, 10));
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertTrue(result.getContent().size() > 0);
        //Assertions.assertEquals(result.getPageSize(), 10);
    }

}