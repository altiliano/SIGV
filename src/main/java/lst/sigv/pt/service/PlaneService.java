package lst.sigv.pt.service;

import lst.sigv.pt.exception.PlaneAlreadyExistException;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;

/**
 * Created by Afonseca on 17/11/20
 */
public interface PlaneService {

    PlaneEntity createPlane(PlaneEntity plane) throws PlaneAlreadyExistException;

    PlaneEntity updatePlane(PlaneEntity plane);

    PlaneEntity changePlaneStatus(PlaneStatus status, Long planeId);

    boolean existsByRegistration(String registration);

    PlaneEntity findPlaneById(Long planeId);
}
