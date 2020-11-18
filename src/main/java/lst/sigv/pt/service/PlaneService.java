package lst.sigv.pt.service;

import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.api.RestPlane;

/**
 * Created by Afonseca on 17/11/20
 */
public interface PlaneService {

    RestPlane createPlane(RestPlane plane);

    RestPlane updatePlane(RestPlane plane);

    RestPlane changePlaneStatus(PlaneStatus status, Long planeId);

    boolean existsByRegistration(String registration);
}