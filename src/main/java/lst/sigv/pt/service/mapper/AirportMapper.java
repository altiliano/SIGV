package lst.sigv.pt.service.mapper;

import io.swagger.annotations.Scope;
import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.api.RestAirport;
import org.mapstruct.Mapper;

/**
 * Created by Afonseca on 12/02/21
 */
@Mapper( componentModel = "spring")
public interface AirportMapper {

    RestAirport airportEntityToRestAirport(AirportEntity airport);
    AirportEntity restAirportToAirportEntity(RestAirport airport);
}
