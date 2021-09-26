package lst.sigv.pt.service;

import lst.sigv.pt.exception.BookingNotFoundException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.api.RestBooking;

/**
 * Created by Afonseca on 12/09/21
 */
public interface BookingService {

    RestBooking createBooking(RestBooking booking) throws Exception;
    RestBooking getBookingByUserId(String userId);
    void cancelBooking(String userId, String bookingId) throws Exception;
}
