package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import system.flight.dto.RouteDTO;
import system.flight.entities.Role;
import system.flight.entities.Route;
import system.flight.mapper.RouteMapper;
import system.flight.repository.RouteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouteService {
@Autowired
    private RouteRepository routeRepository;

    public RouteDTO createRoute(RouteDTO routeDTO){
        Route route= RouteMapper.toEntity(routeDTO);
        return RouteMapper.toDTO(routeRepository.save(route));
    }

    public List<RouteDTO> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public List<RouteDTO> getRoutesBySourceCity(String sourceCity) {
        List<Route> routes = routeRepository.findBySourceCity(sourceCity);
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public List<RouteDTO> getRoutesByDestinationCity(String destinationCity) {
        List<Route> routes = routeRepository.findByDestinationCity(destinationCity);
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }



    public List<RouteDTO> getRoutesByArrivalTime(LocalDateTime arrivalTime) {
        List<Route> routes = routeRepository.findByArrivalTime(arrivalTime);
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

}
