package ciit.sqe.sqewheelz.Repository;

import ciit.sqe.sqewheelz.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
