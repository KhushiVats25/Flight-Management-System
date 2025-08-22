package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.AirlinesDTO;
import system.flight.dto.ApiResponseDTO;
import system.flight.services.AirlineService;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    // Create Airline
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AirlinesDTO>> createAirline(@RequestBody AirlinesDTO dto) {
        ApiResponseDTO<AirlinesDTO> response = airlineService.createAirline(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Get All Airlines
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AirlinesDTO>>> getAllAirlines() {
        ApiResponseDTO<List<AirlinesDTO>> response = airlineService.getAllAirlines();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    //  Get Airline by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AirlinesDTO>> getAirlineById(@PathVariable int id) {
        ApiResponseDTO<AirlinesDTO> response = airlineService.getAirlineById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    //  Get Airlines by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponseDTO<List<AirlinesDTO>>> getAirlinesByName(@PathVariable String name) {
        ApiResponseDTO<List<AirlinesDTO>> response = airlineService.getAirlinesByName(name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Update Airline
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AirlinesDTO>> updateAirline(@PathVariable int id, @RequestBody AirlinesDTO dto) {
        ApiResponseDTO<AirlinesDTO> response = airlineService.updateAirline(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Delete Airline
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAirline(@PathVariable int id) {
        ApiResponseDTO<Void> response = airlineService.deleteAirline(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
