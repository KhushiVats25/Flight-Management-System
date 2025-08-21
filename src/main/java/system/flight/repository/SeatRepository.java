package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Seat;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    //
    Optional<Seat> findBySeatNameAndAircraftAircraftId(String seatName, int aircraftId);
}
