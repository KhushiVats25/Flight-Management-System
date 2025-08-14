package system.flight.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.RouteDTO;
import system.flight.services.RouteService;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteDTO>createRoute(@RequestBody RouteDTO routeDTO){
        RouteDTO createdRoute=routeService.createRoute(routeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoute);
    }
//    @GetMapping
//    public ResponseEntity<List<RouteDTO>>getAllRoutes(){
//        return ResponseEntity.ok(routeService.getAllRoles());
//    }
}
