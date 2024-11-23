package ciit.sqe.sqewheelz.Services;

import ciit.sqe.sqewheelz.Model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking);
    Optional<Booking> getBookingById(Long id);
    List<Booking> getAllBookings();
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
    void returnCar(Long id);
}
