package lst.sigv.pt.service.impl;

/**
 * Created by Afonseca on 12/09/21
 */

import lst.sigv.pt.exception.BookingNotFoundException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.BookingEntity;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.RestBooking;
import lst.sigv.pt.repository.BookingRepository;
import lst.sigv.pt.repository.UserRepository;
import lst.sigv.pt.service.BookingService;
import lst.sigv.pt.service.mapper.BookingMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.userRepository = userRepository;
    }


    @Override
    public RestBooking createBooking(RestBooking booking) {
        BookingEntity bookingEntity = bookingMapper.restBookingToBookingEntity(booking);
        BookingEntity newBooking = bookingRepository.save(bookingEntity);
        return bookingMapper.bookingEntityToRestBooking(newBooking);
    }

    @Override
    public RestBooking getBookingByUserId(String userId) throws UserNotFoundException, BookingNotFoundException {
        if (userId == null || userId.isEmpty()) {
            throw new UserNotFoundException("user with id " + userId + " not found");
        }
        Optional<UserEntity> userEntity = userRepository.findById(Long.parseLong(userId));
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException("user with id " + userId + " not found");
        }
        Optional<BookingEntity> booking = bookingRepository.findByUser(userEntity.get());
        if (!booking.isPresent()) {
            throw new BookingNotFoundException("booking for user with id: " + userId + " not foun");
        }
        return bookingMapper.bookingEntityToRestBooking(booking.get());
    }
}
