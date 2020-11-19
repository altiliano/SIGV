package lst.sigv.pt.service;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.repository.PlaneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Afonseca on 17/11/20
 */
@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Override
    public PlaneEntity createPlane(PlaneEntity plane) {
        return planeRepository.save(plane);
    }

    @Override
    public PlaneEntity updatePlane(PlaneEntity plane) {
        return planeRepository.save(plane);
    }

    @Override
    public PlaneEntity changePlaneStatus(PlaneStatus status, Long planeId) {
        Optional<PlaneEntity> planeEntity = planeRepository.findById(planeId);
        if (planeEntity.isPresent()) {
            planeEntity.get().setStatus(status);
            return planeRepository.save(planeEntity.get());
        }
        //TODO throw exception
        return null;
    }

    @Override
    public boolean existsByRegistration(String registration) {
        return planeRepository.existsByRegistration(registration);
    }

    @Override
    public PlaneEntity findPlaneById(Long planeId) {
        return planeRepository.findById(planeId).orElse(null);
    }
}
