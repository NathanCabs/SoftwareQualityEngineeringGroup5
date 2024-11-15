package ciit.sqe.sqewheelz.Model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Bookingid;


    @JoinColumn(name = "user_id", nullable = false)
    private Long user_Id;


    @JoinColumn(name = "car_id", nullable = false)
    private Long car_Id;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "amount", nullable = false)
    private double amount;



    public Booking() {}

    public Booking (Long user_Id, Long car_Id, LocalDate bookingDate, LocalDate returnDate) {
        this(user_Id,car_Id,bookingDate,returnDate,"BOOKED",0);
    }

    public Booking(Long user_Id, Long car_Id, LocalDate bookingDate, LocalDate returnDate, String status, double amount) {
        this.user_Id = user_Id;
        this.car_Id = car_Id;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        this.status = status;
        this.amount = amount;

    }

    public Long getBookingid() {
        return Bookingid;
    }

    public void setBookingid(Long bookingid) {
        Bookingid = bookingid;
    }

    public Long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Long user_Id) {
        this.user_Id = user_Id;
    }

    public Long getCar_Id() {
        return car_Id;
    }

    public void setCar_Id(Long car_Id) {
        this.car_Id = car_Id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
