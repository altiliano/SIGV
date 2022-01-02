package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestPlaneForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by Afonseca on 18/11/20
 */
@Mapper(componentModel = "spring")
public interface PlaneMapper {
    RestPlane planeEntityToRestPlane(PlaneEntity plane);
    @Mapping(target = "routes", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    PlaneEntity restPlaneToPlaneEntity(RestPlane plane);
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "routes", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    PlaneEntity restPlaneFormToPlaneEntity(RestPlaneForm planeForm);
    List<RestPlane> planeEntityToRestPlane (List<PlaneEntity> planes);
}
