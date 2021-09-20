package lst.sigv.pt.service;

import lst.sigv.pt.model.api.RestBooking;

/**
 * Created by Afonseca on 12/09/21
 */
public interface BookingService {

    RestBooking createBooking(RestBooking booking);
    RestBooking getBookingByUserId(String userId);
}
