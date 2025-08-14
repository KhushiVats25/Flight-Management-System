package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Route;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findBySourceCity(String sourceCity);
    List<Route> findByDestinationCity(String destinationCity);
    List<Route> findByArrivalTime(LocalDateTime arrivalTime);
    List<Route> findByDepartureTime(LocalDateTime departureTime);
}
