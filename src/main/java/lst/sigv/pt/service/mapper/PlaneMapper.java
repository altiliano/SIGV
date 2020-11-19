package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestPlaneForm;
import org.mapstruct.Mapper;

/**
 * Created by Afonseca on 18/11/20
 */
@Mapper(componentModel = "spring")
public interface PlaneMapper {
    RestPlane planeEntityToRestPlane(PlaneEntity plane);
    PlaneEntity restPlaneToPlaneEntity(RestPlane plane);
    PlaneEntity restPlaneFormToPlaneEntity(RestPlaneForm planeForm);
}
