package lst.sigv.pt.service.impl;

import lst.sigv.pt.exception.BookingNotFoundException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.BookingEntity;
import lst.sigv.pt.model.BookingStatus;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.model.api.RestBooking;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestUser;
import lst.sigv.pt.repository.BookingRepository;
import lst.sigv.pt.repository.UserRepository;
import lst.sigv.pt.service.mapper.BookingMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Afonseca on 10/09/21
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("Test booking service")
public class BookingServiceImplTest {

    BookingServiceImpl bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    BookingMapperImpl bookingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingServiceImpl(bookingRepository, bookingMapper, userRepository);
    }

    @Test
    @DisplayName("Test create booking successfully")
    void creteBooking() throws Exception {
        RestBooking bookingRequest = getBooking();
        when(bookingService.createBooking(bookingRequest)).thenReturn(getBooking());
        RestBooking createdBook = bookingService.createBooking(getBooking());

        Assertions.assertNotNull(createdBook);
        Assertions.assertNotNull(createdBook.getAircraft());
        Assertions.assertNotNull(createdBook.getDeparture());
        Assertions.assertNotNull(createdBook.getArrive());
        Assertions.assertNotNull(createdBook.getUser());
        Assertions.assertNotNull(createdBook.getStatus());
        Assertions.assertNotNull(createdBook.getStatusDate());
        Assertions.assertNotNull(createdBook.getCreatedDate());
        Assertions.assertEquals(BookingStatus.CREATED, createdBook.getStatus());
    }

    @Test
    @DisplayName("Test try to get booking with invalid user id and booking id")
    void getBookingByUserIdWithError() {
        when(userRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());
        when(userRepository.findById(Long.parseLong("2"))).thenReturn(Optional.of(new UserEntity()));
        when(bookingRepository.findByUser(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> bookingService.getBookingByUserId("1"));
        Assertions.assertThrows(UserNotFoundException.class, () -> bookingService.getBookingByUserId(null));
        Assertions.assertThrows(UserNotFoundException.class, () -> bookingService.getBookingByUserId(""));
        Assertions.assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingByUserId("2"));
    }

    @Test
    @DisplayName("Test create booking with null request")
    void getBookingWithNull() {
        Assertions.assertThrows(Exception.class, () -> bookingService.createBooking(null));
    }

    @Test
    @DisplayName("Test cancel booking successfully")
    void cancelBooking() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUser(user);

        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingEntity));
        bookingService.cancelBooking("1", "1");
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Test cancel booking with wrong user id")
    void cancelBookingWithWrongUserId()  {

        Assertions.assertThrows(BookingNotFoundException.class, () -> bookingService.cancelBooking("2", ""));

    }
    @Test
    @DisplayName("Test cancel booking with wrong user id and booking id")
    void cancelBookingWithWrongRequest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> bookingService.cancelBooking("", ""));
        Assertions.assertThrows(UserNotFoundException.class, () -> bookingService.cancelBooking("","2"));
        Assertions.assertThrows(BookingNotFoundException.class, () -> bookingService.cancelBooking("1",""));
        Assertions.assertThrows(UserNotFoundException.class, () -> bookingService.cancelBooking(null,"2"));

    }



    private RestBooking getBooking() {
        RestBooking restBooking = new RestBooking();
        restBooking.setAircraft(new RestPlane());
        restBooking.setUser(new RestUser());
        restBooking.setStatus(BookingStatus.CREATED);
        restBooking.setDeparture(new RestAirport());
        restBooking.setArrive(new RestAirport());
        restBooking.setId(1);
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        restBooking.setStatusDate(new Date(timeStampMillis));
        restBooking.setCreatedDate(new Date(timeStampMillis));
        restBooking.getUser().setId(1L);
        return restBooking;
    }

    private BookingEntity getBookingEntity() {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1L);
        bookingEntity.setStatus(BookingStatus.CREATED);
        bookingEntity.setUser(new UserEntity());
        bookingEntity.setPlane(new PlaneEntity());
        bookingEntity.setDepartIataCode("LPPT");
        bookingEntity.setDepartIcaoCode("LPPT");
        bookingEntity.setArrivalCountry("Portugal");
        bookingEntity.setArrivalCountry("Portugal");
        bookingEntity.setArrivalCity("Porto");
        bookingEntity.setArrivalIcaoCode("LPPR");
        bookingEntity.setDepartIataCode("LPPR");
        return bookingEntity;
    }
}
