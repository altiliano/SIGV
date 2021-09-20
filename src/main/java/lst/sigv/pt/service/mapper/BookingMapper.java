package lst.sigv.pt.service.mapper;

import lst.sigv.pt.model.BookingEntity;
import lst.sigv.pt.model.api.RestBooking;
import org.mapstruct.Mapper;

/**
 * Created by Afonseca on 18/09/21
 */
@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingEntity restBookingToBookingEntity(RestBooking source);

    RestBooking bookingEntityToRestBooking(BookingEntity source);
}
