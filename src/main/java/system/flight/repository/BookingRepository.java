package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Booking;
import system.flight.enums.BookingStatus;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {


    List<Booking> findByUserUserId(int userId);

    List<Booking> findByBookingStatus(BookingStatus status);
}