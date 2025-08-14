package system.flight.services;

import system.flight.dto.RouteDTO;
import system.flight.entities.Route;
import system.flight.mapper.RouteMapper;
import system.flight.repository.RouteRepository;

public class RouteService {

    private RouteRepository routeRepository;

    public RouteDTO createRole(RouteDTO routeDTO){
        Route route= RouteMapper.toEntity(routeDTO);
        return RouteMapper.toDTO(routeRepository.save(route));
    }
}
