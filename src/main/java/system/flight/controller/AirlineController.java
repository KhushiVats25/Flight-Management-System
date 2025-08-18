package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.AirlinesDTO;
import system.flight.services.AirlineService;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @GetMapping
    public List<AirlinesDTO> getAllAirlines() {

        return airlineService.getAllAirlines();
    }

    @GetMapping("/{id}")
    public AirlinesDTO getAirlines(@PathVariable int id) {
        return airlineService.getAirlineById(id);
    }

    @PostMapping()
    public AirlinesDTO createAirline(@RequestBody AirlinesDTO dto) {
        return airlineService.createAirline(dto);
    }

    @PutMapping("/{id}")
    public AirlinesDTO updateAirline(@PathVariable int id, @RequestBody AirlinesDTO dto) {
        return airlineService.updateAirline(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAirline(@PathVariable int id) {
        airlineService.deleteAirline(id);
    }
}

