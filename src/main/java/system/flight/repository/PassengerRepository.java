package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
}