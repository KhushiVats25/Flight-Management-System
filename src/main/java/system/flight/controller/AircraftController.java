package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.AircraftsDTO;
import system.flight.entities.Aircraft;
import system.flight.services.AircraftService;

import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @PostMapping
    public Aircraft createAircraft(@RequestBody AircraftsDTO dto) {
        return aircraftService.createAircraft(dto);
    }

    @GetMapping
    public List<Aircraft> getAllAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @GetMapping("/{id}")
    public Aircraft getAircraftById(@PathVariable int id) {
        return aircraftService.getAircraftById(id);
    }
}
