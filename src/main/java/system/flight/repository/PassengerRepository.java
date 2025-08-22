package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.flight.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

}