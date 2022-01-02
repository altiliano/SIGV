package lst.sigv.pt.repository;

import lst.sigv.pt.model.RouteEntity;
import lst.sigv.pt.model.RouteStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Afonseca on 05/10/2021.
 **/
@SpringBootTest
@ContextConfiguration( name = "testApplication.properties")
class RouteRepositoryTest {

    @Autowired
    private RouteRepository routeRepository;

    @Test
    void findAllByStatus() {
        List<RouteEntity>  routeEntities = routeRepository.findAllByStatusAndDepart_IcaoCode(RouteStatus.INACTIVE, "GVNP");
        Assertions.assertNotNull(routeEntities);
        Assertions.assertEquals(routeEntities.size(), 1);
    }
}