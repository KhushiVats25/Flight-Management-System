package system.flight.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import system.flight.dto.PassangerDTO;
import system.flight.dto.PassangerResponseDTO;
import system.flight.services.PassengerService;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassangerContoller {

    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public PassangerResponseDTO createPassenger(@RequestBody PassangerDTO dto) {
        return passengerService.createPassenger(dto);
    }

    @GetMapping
    public List<PassangerResponseDTO> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public PassangerResponseDTO getPassengerById(@PathVariable int id) {
        return passengerService.getPassengerById(id);
    }

    @PutMapping("/{id}")
    public PassangerResponseDTO updatePassenger(@PathVariable int id, @RequestBody PassangerDTO dto) {
        return passengerService.updatePassenger(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable int id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.ok("Passenger with ID " + id + " has been deleted successfully.");
    }
}
