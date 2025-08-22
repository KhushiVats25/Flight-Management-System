package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.AircraftResponseDTO;
import system.flight.dto.AircraftsDTO;
import system.flight.dto.ApiResponseDTO;
import system.flight.entities.Aircraft;
import system.flight.services.AircraftService;
import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;


    @PostMapping
    public ResponseEntity<ApiResponseDTO<AircraftResponseDTO>> createAircraft(@RequestBody AircraftsDTO dto) {
        AircraftResponseDTO createdAircraft= aircraftService.createAircraft(dto);

        ApiResponseDTO<AircraftResponseDTO> response = new ApiResponseDTO<>(
                201,
                "Aircraft registered successfully",
                createdAircraft
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AircraftResponseDTO>>> getAllAircrafts() {
        List<AircraftResponseDTO> responseDTO= aircraftService.getAllAircrafts();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "All aircrafts retrieved successfully", responseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AircraftResponseDTO>> getAircraftById(@PathVariable int id) {
        AircraftResponseDTO retrievedAircraft = aircraftService.getAircraftById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Aircraft details retrieved successfully", retrievedAircraft));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AircraftResponseDTO>> updateAircraft(@PathVariable int id, @RequestBody AircraftsDTO dto) {
        AircraftResponseDTO updatedAircraft = aircraftService.updateAircraft(id, dto);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Aircraft updated successfully", updatedAircraft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAircraft(@PathVariable int id) {

        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Aircraft deleted successfully", null));
    }



}
