package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.flight.entities.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
}
