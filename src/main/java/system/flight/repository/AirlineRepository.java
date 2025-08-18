package system.flight.repository;

import system.flight.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {
    Optional<Airline> findByCode(String code);

    boolean existsByNameOrCode(String name, String code);

}
