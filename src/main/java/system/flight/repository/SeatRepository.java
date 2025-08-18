package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.flight.entities.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
}
