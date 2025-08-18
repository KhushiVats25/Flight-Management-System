package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.AirlinesDTO;
import system.flight.services.AirlineService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @GetMapping
    public ResponseEntity<?> getAllAirlines() {
        List<AirlinesDTO> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Airlines fetched successfully",
                "data", airlines
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAirlines(@PathVariable int id) {
        try {
            AirlinesDTO airline = airlineService.getAirlineById(id);
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "message", "Airline fetched successfully",
                    "data", airline
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAirline(@RequestBody AirlinesDTO dto) {
        try {
            AirlinesDTO created = airlineService.createAirline(dto);
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "message", "Airline added successfully",
                    "data", created
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "message", e.getMessage()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(Map.of(
                    "status", 409,
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAirline(@PathVariable int id, @RequestBody AirlinesDTO dto) {
        try {
            AirlinesDTO updated = airlineService.updateAirline(id, dto);
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "message", "Airline updated successfully",
                    "data", updated
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAirline(@PathVariable int id) {
        try {
            airlineService.deleteAirline(id);
            return ResponseEntity.ok(Map.of(
                    "status", 200,
                    "message", "Airline successfully deleted"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "message", e.getMessage()
            ));
        }
    }
}
