package system.flight.mapper;

import system.flight.dto.RouteDTO;
import system.flight.entities.Route;

public class RouteMapper {

    public static RouteDTO toDTO(Route Route){
        return new RouteDTO(Route.getRouteId(),Route.getDepartureTime(),Route.getArrivalTime(),Route.getSourceCity(),Route.getDestinationCity(),Route.getDistanceKm(),Route.getEstimatedTime());
    }

    public static Route toEntity(RouteDTO RouteDTO){
        Route Route=new Route();
        Route.setRouteId(RouteDTO.getRouteId());
        Route.setSourceCity(RouteDTO.getSourceCity());
        Route.setDestinationCity(RouteDTO.getDestinationCity());
        Route.setDistanceKm(RouteDTO.getDistanceInKm());
        Route.setEstimatedTime(RouteDTO.getEstimatedTime());
        Route.setArrivalTime(RouteDTO.getArrivalTime());
        Route.setDepartureTime(RouteDTO.getDepartureTime());
        return Route;
    }
}
