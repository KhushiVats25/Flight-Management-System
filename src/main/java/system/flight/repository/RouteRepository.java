package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
}
