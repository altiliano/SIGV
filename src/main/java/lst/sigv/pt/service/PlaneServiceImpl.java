package lst.sigv.pt.service;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.repository.PlaneRepository;
import lst.sigv.pt.service.mapper.PlaneMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Afonseca on 17/11/20
 */
@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final PlaneMapper planeMapper;

    public PlaneServiceImpl(PlaneRepository planeRepository, PlaneMapper planeMapper) {
        this.planeRepository = planeRepository;
        this.planeMapper = planeMapper;
    }

    @Override
    public RestPlane createPlane(RestPlane plane) {
        PlaneEntity planeEntity = planeMapper.restPlaneToPlaneEntity(plane);
        return planeMapper.planeEntityToRestPlane(planeRepository.save(planeEntity));
    }

    @Override
    public RestPlane updatePlane(RestPlane plane) {
        PlaneEntity planeEntity = planeMapper.restPlaneToPlaneEntity(plane);
        return planeMapper.planeEntityToRestPlane(planeEntity);
    }

    @Override
    public RestPlane changePlaneStatus(PlaneStatus status, Long planeId) {
        Optional<PlaneEntity> planeEntity = planeRepository.findById(planeId);
        if (planeEntity.isPresent()) {
            planeEntity.get().setStatus(status);
            return planeMapper.planeEntityToRestPlane(planeRepository.save(planeEntity.get()));
        }
        //TODO throw exception
        return null;
    }

    @Override
    public boolean existsByRegistration(String registration) {
        return planeRepository.existsByRegistration(registration);
    }
}
