package lst.sigv.pt.service.impl;

/**
 * Created by Afonseca on 12/09/21
 */

import lst.sigv.pt.exception.BookingNotFoundException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.BookingEntity;
import lst.sigv.pt.model.BookingStatus;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.RestBooking;
import lst.sigv.pt.repository.BookingRepository;
import lst.sigv.pt.repository.UserRepository;
import lst.sigv.pt.service.BookingService;
import lst.sigv.pt.service.mapper.BookingMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public RestBooking createBooking(RestBooking booking) throws Exception {
        if (booking == null) {
            throw new Exception("Invalid request the booking is null");
        }
        String userId = booking.getUser().getId().toString();
        if (isInvalidUserId(userId)) {
            throw new UserNotFoundException("user with id" + userId + " not found ");
        }
        BookingEntity bookingEntity = bookingMapper.restBookingToBookingEntity(booking);
        BookingEntity newBooking = bookingRepository.save(bookingEntity);
        return bookingMapper.bookingEntityToRestBooking(newBooking);
    }

    @Override
    public RestBooking getBookingByUserId(String userId) throws UserNotFoundException, BookingNotFoundException {

        if (isInvalidUserId(userId)) {
            throw new UserNotFoundException("user with id" + userId + " not found ");
        }

        UserEntity userEntity = getUserById(userId);

        Optional<BookingEntity> booking = bookingRepository.findByUser(userEntity);
        if (!booking.isPresent()) {
            throw new BookingNotFoundException("booking for user with id: " + userId + " not found");
        }
        return bookingMapper.bookingEntityToRestBooking(booking.get());
    }

    @Override
    @Transactional
    public void cancelBooking(String userId, String bookingId) throws Exception {
        if (isInvalidUserId(userId)) {
            throw new UserNotFoundException("user with id: " + userId + " not found");
        }
        if (isInvalidBookingId(bookingId)) {
            throw new BookingNotFoundException("booking with id: " + bookingId + " not found");
        }
        long bookId = Long.parseLong(bookingId);

        Optional<BookingEntity> booking = bookingRepository.findById(bookId);
        if (booking.isPresent()) {
            UserEntity user = booking.get().getUser();
            if (!user.getId().equals(Long.parseLong(userId))) {
                throw new Exception("Invalid operation user id: " + userId + " is not equals user id " + user.getId());
            }
            booking.get().setStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking.get());
        } else {
            throw new BookingNotFoundException("booking with id: " + bookingId + " not found");
        }

    }

    private boolean isInvalidUserId(String userId) {
        return userId == null || userId.isEmpty();
    }

    private boolean isInvalidBookingId(String bookingId) {
        return bookingId == null || bookingId.isEmpty();
    }

    private UserEntity getUserById(String userId) throws UserNotFoundException {
        return userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UserNotFoundException("user with id" + userId + " not found "));
    }
}
