package ciit.sqe.sqewheelz.Controllers;


import ciit.sqe.sqewheelz.Model.Booking;
import ciit.sqe.sqewheelz.Services.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
//        if (booking.getCar() != null) {
//            System.out.println("Car price: " + booking.getCar().getPrice());
//        } else {
//            System.out.println("Car is null!");
//        }

        if(!"USER".equals(role)) {
            throw new RuntimeException("Users can only book a booking") ;
        }
        return bookingService.createBooking(booking);
        //return "Booking created successfully";
    }

    //Get all bookings
    @GetMapping
    public List<Booking> getAllBookings(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied: Admin only!");
        }
        return bookingService.getAllBookings();
    }

    //Get bookings by id (admin or by own user)
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id, HttpServletRequest request) {
        Booking booking = bookingService.getBookingById(id).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        if("ADMIN".equals(role) || booking.getUser_Id().equals(userId)) {
            return bookingService.getBookingById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
        }
        else{
            throw new RuntimeException("Access denied: You can only view your own booking");
        }

    }

    //Update a booking (user only)
    @PutMapping("/{id}")
    public String updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails, HttpServletRequest request) {
        Booking booking = bookingService.getBookingById(id).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));

        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");

        if("USER".equals(role) && booking.getUser_Id().equals(userId)) {
            bookingService.updateBooking(id, bookingDetails);
            return "Booking updated successfully";
        }
        else{
            throw new RuntimeException("Access denied: You can only update your own booking");
        }
    }


    //Delete a booking
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id, HttpServletRequest request) {
        Booking booking = bookingService.getBookingById(id).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));

        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");

        if("USER".equals(role) && booking.getUser_Id().equals(userId)) {
            bookingService.deleteBooking(id);
            return "Booking deleted successfully";
        }
        throw new RuntimeException("Access denied: You can only delete your own booking");
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<String> returnCar(@PathVariable Long id){
        try{
            bookingService.returnCar(id);
            return ResponseEntity.ok("Car returned successfully");
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
