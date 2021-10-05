package lst.sigv.pt.repository;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Afonseca on 05/10/2021.
 **/
@SpringBootTest
@ContextConfiguration(name = "testApplication.properties")
class PlaneRepositoryTest {

    @Autowired
    private PlaneRepository planeRepository;

    @Test
    void existsByRegistration() {
      Assertions.assertTrue(planeRepository.existsByRegistration("CS-TVV"));
    }

    @Test
    void findAllByStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PlaneEntity> planes = planeRepository.findAllByStatus(PlaneStatus.INACTIVE, pageable);
        Assertions.assertNotNull(planes);
        Assertions.assertTrue(planes.getTotalPages() > 0);
        Assertions.assertTrue(planes.getTotalElements() > 1);
    }
}