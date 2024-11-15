package ciit.sqe.sqewheelz.Services;

import ciit.sqe.sqewheelz.Model.Booking;
import ciit.sqe.sqewheelz.Model.Car;
import ciit.sqe.sqewheelz.Model.User;
import ciit.sqe.sqewheelz.Repository.BookingRepository;
import ciit.sqe.sqewheelz.Repository.CarRepository;
import ciit.sqe.sqewheelz.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImplementation( BookingRepository bookingRepository, CarRepository carRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;

    }


    @Override
    public Booking createBooking(Booking booking) {
        //Calculate booking in days
        User user = userRepository.findById(booking.getUser_Id()).orElse(null);
        Car car = carRepository.findById(booking.getCar_Id()).orElse(null);

        long durationInDays = ChronoUnit.DAYS.between(booking.getBookingDate(), booking.getReturnDate());

        //Fetch car price from Car repository and calculate the amount
        assert car != null;
        double carPrice = durationInDays * car.getPrice();
        booking.setAmount(carPrice);

        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Car car = carRepository.findById(bookingDetails.getCar_Id()).orElse(null);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));

        if (bookingDetails.getUser_Id() != null){
            booking.setUser_Id(bookingDetails.getUser_Id());
        }
        if (bookingDetails.getCar_Id() != null){
            booking.setCar_Id(bookingDetails.getCar_Id());
        }
        if(bookingDetails.getBookingDate() != null) {
            booking.setBookingDate(bookingDetails.getBookingDate());
        }
        if(bookingDetails.getReturnDate() != null) {
            booking.setReturnDate(bookingDetails.getReturnDate());
        }
        if(bookingDetails.getCar_Id() != null) {
            booking.setCar_Id(bookingDetails.getCar_Id());
        }
        if(bookingDetails.getStatus() != null) {
            booking.setStatus(bookingDetails.getStatus());
        }


            long durationDays = ChronoUnit.DAYS.between(booking.getBookingDate(), booking.getReturnDate());
            assert car != null;
            double carPrice = car.getPrice();
            booking.setAmount(durationDays * carPrice);

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }


}
