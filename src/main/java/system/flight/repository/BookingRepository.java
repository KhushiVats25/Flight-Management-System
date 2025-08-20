package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>   {

    // You can add custom query methods here if needed
    // For example:
    // List<Booking> findByUser_UserId(int userId);
    // List<Booking> findByAircraft_AircraftId(int aircraftId);
}