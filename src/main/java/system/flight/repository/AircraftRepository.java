package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Aircraft;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {
}
