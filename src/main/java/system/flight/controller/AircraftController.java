package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.AircraftResponseDTO;
import system.flight.dto.AircraftsDTO;
import system.flight.dto.AirlinesDTO;
import system.flight.entities.Aircraft;
import system.flight.services.AircraftService;

import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @PostMapping
    public AircraftResponseDTO createAircraft(@RequestBody AircraftsDTO dto) {
        return aircraftService.createAircraft(dto);
    }

    @GetMapping
    public List<AircraftResponseDTO> getAllAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @GetMapping("/{id}")
    public AircraftResponseDTO getAircraftById(@PathVariable int id) {
        return aircraftService.getAircraftById(id);
    }

    @PutMapping("/{id}")
    public AircraftResponseDTO updateAircraft(@PathVariable int id, @RequestBody AircraftsDTO dto) {
        return aircraftService.updateAircraft(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAircraft(@PathVariable int id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok("Aircraft with ID " + id + " has been deleted successfully.");
    }



}
