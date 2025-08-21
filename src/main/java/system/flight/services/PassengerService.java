package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.PassengerInfoDTO;
import system.flight.entities.Passenger;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.PassengerMapper;
import system.flight.repository.PassengerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<PassengerInfoDTO> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(PassengerMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public PassengerInfoDTO getPassengerById(int id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        return PassengerMapper.mapToDTO(passenger);
    }

    public void deletePassenger(int id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        passengerRepository.delete(passenger);
    }
}