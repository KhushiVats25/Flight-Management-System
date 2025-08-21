package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.entities.Aircraft;
import system.flight.entities.Seat;
import system.flight.exception.ResourceNotFoundException;
import system.flight.repository.AircraftRepository;
import system.flight.repository.SeatRepository;


@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private AircraftRepository aircraftRepository;


    @GetMapping("/{aircraftId}/{seatName}")
    public ResponseEntity<Seat> getSeat(@PathVariable int aircraftId, @PathVariable String seatName) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + aircraftId));

        Seat seat = seatRepository.findBySeatNameAndAircraftAircraftId(seatName, aircraftId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found for seat name: " + seatName + " on aircraft ID: " + aircraftId));

        return ResponseEntity.ok(seat);
    }


    }
