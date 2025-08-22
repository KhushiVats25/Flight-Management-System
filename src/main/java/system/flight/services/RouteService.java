package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.RouteDTO;
import system.flight.entities.Route;

import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.RouteMapper;
import system.flight.repository.RouteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public RouteDTO createRoute(RouteDTO routeDTO) {
        Route route = RouteMapper.toEntity(routeDTO);
        return RouteMapper.toDTO(routeRepository.save(route));
    }

    public List<RouteDTO> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        if (routes.isEmpty()) {
            throw new ResourceNotFoundException("No routes found");
        }
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public List<RouteDTO> getRoutesBySourceCity(String sourceCity) {
        List<Route> routes = routeRepository.findBySourceCity(sourceCity);
        if (routes.isEmpty()) {
            throw new ResourceNotFoundException("No routes found from source city: " + sourceCity);
        }
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public List<RouteDTO> getRoutesByDestinationCity(String destinationCity) {
        List<Route> routes = routeRepository.findByDestinationCity(destinationCity);
        if (routes.isEmpty()) {
            throw new ResourceNotFoundException("No routes found to destination city: " + destinationCity);
        }
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public List<RouteDTO> getRoutesByArrivalTime(LocalDateTime arrivalTime) {
        List<Route> routes = routeRepository.findByArrivalTime(arrivalTime);
        if (routes.isEmpty()) {
            throw new ResourceNotFoundException("No routes found with arrival time: " + arrivalTime);
        }
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public List<RouteDTO> getRoutesByDepartureTime(LocalDateTime departureTime) {
        List<Route> routes = routeRepository.findByDepartureTime(departureTime);
        if (routes.isEmpty()) {
            throw new ResourceNotFoundException("No routes found with departure time: " + departureTime);
        }
        return routes.stream()
                .map(RouteMapper::toDTO)
                .toList();
    }

    public RouteDTO getRouteById(int id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with ID: " + id));
        return RouteMapper.toDTO(route);
    }

    public RouteDTO updateRoute(int routeId, RouteDTO routeDTO) {
        Route existingRoute = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with ID: " + routeId));

        if (routeDTO.getDepartureTime() != null) {
            existingRoute.setDepartureTime(routeDTO.getDepartureTime());
        }
        if (routeDTO.getArrivalTime() != null) {
            existingRoute.setArrivalTime(routeDTO.getArrivalTime());
        }
        if (routeDTO.getDistanceInKm() != -1) {
            existingRoute.setDistanceKm(routeDTO.getDistanceInKm());
        }

        Route updatedRoute = routeRepository.save(existingRoute);
        return RouteMapper.toDTO(updatedRoute);
    }

    public void deleteRoute(int routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Route not found with ID: " + routeId));
        route.setIsDeleted(true);
        routeRepository.save(route);
    }
}