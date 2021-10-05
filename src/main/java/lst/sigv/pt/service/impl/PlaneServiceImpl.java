package lst.sigv.pt.service.impl;

import lst.sigv.pt.exception.InvalidPlaneStatusException;
import lst.sigv.pt.exception.PlaneAlreadyExistException;
import lst.sigv.pt.exception.PlaneNotFoundException;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.repository.PlaneRepository;
import lst.sigv.pt.service.PlaneService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public PlaneEntity createPlane(PlaneEntity plane) throws PlaneAlreadyExistException {
        if (existsByRegistration(plane.getRegistration())) {
            throw new PlaneAlreadyExistException("Plane with registration: " + plane.getRegistration() + " already exist");
        }
        plane.setStatus(PlaneStatus.INACTIVE);
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
            PlaneEntity plane = planeEntity.get();
            if (!isValidStatus(plane.getStatus(), status)) {
                planeEntity.get().setStatus(status);
                return planeRepository.save(plane);
            } else {
                throw new InvalidPlaneStatusException("Invalid plane status");
            }
        }
        throw new PlaneNotFoundException("plane wiht id: " + planeId + " not found");
    }

    @Override
    public boolean existsByRegistration(String registration) {
        return planeRepository.existsByRegistration(registration);
    }

    @Override
    public PlaneEntity findPlaneById(Long planeId) {
        return planeRepository.findById(planeId).orElse(null);
    }

    private boolean isValidStatus(PlaneStatus currentStatus, PlaneStatus newStatus) {
        return currentStatus.equals(newStatus);
    }


    //TODO fix this
    @Override
    public List<PlaneEntity> getAllActivePlane() {
        List<PlaneEntity> planes = new ArrayList<>();
        planeRepository.findAll().forEach(planes::add);
        return planes;
    }
}
