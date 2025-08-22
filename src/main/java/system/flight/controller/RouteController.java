package system.flight.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.ApiResponseDTO;
import system.flight.dto.RouteDTO;
import system.flight.services.RouteService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<RouteDTO>> createRoute(@RequestBody RouteDTO routeDTO){
        RouteDTO createdRoute=routeService.createRoute(routeDTO);
        ApiResponseDTO<RouteDTO> responseDTO= new ApiResponseDTO<>(HttpStatus.OK.value(), "Route created successfully",createdRoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        List<RouteDTO> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/source")
    public ResponseEntity<List<RouteDTO>> getRoutesBySourceCity(@RequestParam String sourceCity) {
        List<RouteDTO> routes = routeService.getRoutesBySourceCity(sourceCity);
        return ResponseEntity.ok(routes);
    }
@GetMapping("/destination")
    public  ResponseEntity<List<RouteDTO>> getRoutesByDestinationCity(@RequestParam String destinationCity){
        List<RouteDTO> routes=routeService.getRoutesByDestinationCity(destinationCity);
        return ResponseEntity.ok(routes);

}

    @GetMapping("/arrival")
    public ResponseEntity<List<RouteDTO>> getRoutesByArrivalTime(@RequestParam String arrivalTime) {
        LocalDateTime time = LocalDateTime.parse(arrivalTime);
        List<RouteDTO> routes = routeService.getRoutesByArrivalTime(time);
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/departure")
    public ResponseEntity<List<RouteDTO>> getRoutesByDepartureTime(@RequestParam String departureTime) {
        LocalDateTime time = LocalDateTime.parse(departureTime);
        List<RouteDTO> routes = routeService.getRoutesByDepartureTime(time);
        return ResponseEntity.ok(routes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable int id) {
        RouteDTO route = routeService.getRouteById(id);
        return ResponseEntity.ok(route);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RouteDTO> updateRoute(@PathVariable int id, @RequestBody RouteDTO routeDTO) {
        RouteDTO updatedRoute = routeService.updateRoute(id, routeDTO);
        return updatedRoute != null ? ResponseEntity.ok(updatedRoute) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable int id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.ok("Route deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}




