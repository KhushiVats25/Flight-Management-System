package system.flight.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import system.flight.dto.PassengerInfoDTO;
import system.flight.services.PassengerService;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;


    @GetMapping
    public List<PassengerInfoDTO> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public PassengerInfoDTO getPassengerById(@PathVariable int id) {
        return passengerService.getPassengerById(id);
    }


}
