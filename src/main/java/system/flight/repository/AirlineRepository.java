package system.flight.repository;

import aj.org.objectweb.asm.commons.Remapper;
import system.flight.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline,Integer> {
    Optional<Airline> findByCode(String code);


    boolean existsByNameOrCode(String name, String code);

    List<Airline> findAllByName(String name);
}
