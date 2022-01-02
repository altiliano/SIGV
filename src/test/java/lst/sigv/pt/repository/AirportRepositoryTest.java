package lst.sigv.pt.repository;

import lst.sigv.pt.model.AirportEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Afonseca on 05/10/2021.
 **/
@SpringBootTest
@ContextConfiguration(name = "testApplication.properties")
class AirportRepositoryTest {
    @Autowired
    private  AirportRepository airportRepository;

    @Test
    void findByIcaoCode() {
       AirportEntity airport = airportRepository.findByIcaoCode("LPPT");
       Assertions.assertNotNull(airport);

    }

    @Test
    void findAirportEntityById() {
        AirportEntity airport = airportRepository.findAirportEntityById(1L);
        Assertions.assertNotNull(airport);
    }

}