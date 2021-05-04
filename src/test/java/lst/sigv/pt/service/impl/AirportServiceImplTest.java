package lst.sigv.pt.service.impl;

import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.repository.AirportRepository;
import lst.sigv.pt.service.AirportService;
import lst.sigv.pt.service.mapper.AirportMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Afonseca on 12/02/21
 */
class AirportServiceImplTest {
    @Mock
    AirportMapper airportMapper;
    @Mock
    AirportRepository airportRepository;
    @Mock
    AirportService airportService;

    RestAirport restAirport;
    AirportEntity airportEntity;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        restAirport = new RestAirport();
        restAirport.setCity("Lisbon");
        restAirport.setIataCode("LP");
        restAirport.setId("1");
        restAirport.setCountry("Portugal");

        airportEntity = new AirportEntity();
        airportEntity.setCity("Lisbon");
        airportEntity.setIataCode("LP");
        airportEntity.setId(1);
        airportEntity.setCountry("Portugal");


    }

    @Test
    void saveAirport() {
        when(airportMapper.restAirportToAirportEntity(restAirport)).thenReturn(airportEntity);
        when(airportRepository.save(airportEntity)).thenReturn(airportEntity);
        when(airportService.editAirport(restAirport)).thenReturn(restAirport);
        RestAirport result = airportService.editAirport(restAirport);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getCity(), airportEntity.getCity());
        Assertions.assertEquals(result.getCountry(), airportEntity.getCountry());
        Assertions.assertEquals(result.getIcaoCode(), airportEntity.getIcaoCode());
    }

    @Test
    void getAirports() {
        List<AirportEntity> airportEntities = new ArrayList<>();
        List<RestAirport> restAirports = airportService.getAirports();
        restAirports.add(restAirport);
        airportEntities.add(airportEntity);
        when(airportMapper.restAirportToAirportEntity(restAirport)).thenReturn(airportEntity);
        when(airportRepository.findAll()).thenReturn(airportEntities);
        when(airportService.getAirports()).thenReturn(restAirports);

        List<RestAirport> result = airportService.getAirports();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
    }

}