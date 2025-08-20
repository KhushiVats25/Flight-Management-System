package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>   {

}