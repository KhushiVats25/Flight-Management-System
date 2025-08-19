package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.PassangerDTO;
import system.flight.dto.PassangerResponseDTO;
import system.flight.entities.Booking;
import system.flight.entities.Passenger;
import system.flight.entities.User;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.PassengerMapper;
import system.flight.repository.BookingRepository;
import system.flight.repository.PassengerRepository;
import system.flight.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public PassangerResponseDTO createPassenger(PassangerDTO dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = PassengerMapper.toEntity(dto, booking, user);
        passenger.setSeatNumber(booking.getSeat());

        Passenger savedPassenger = passengerRepository.save(passenger);

        return PassengerMapper.toResponseDTO(savedPassenger);
    }

    public List<PassangerResponseDTO> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        List<PassangerResponseDTO> responseList = new ArrayList<>();

        for (Passenger p : passengers) {
            responseList.add(PassengerMapper.toResponseDTO(p));
        }

        return responseList;
    }

    public PassangerResponseDTO getPassengerById(int id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        return PassengerMapper.toResponseDTO(passenger);
    }

    public PassangerResponseDTO updatePassenger(int id, PassangerDTO dto) {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingPassenger.setBooking(booking);
        existingPassenger.setUser(user);
        existingPassenger.setName(dto.getName());
        existingPassenger.setAge(dto.getAge());
        existingPassenger.setGender(dto.getGender());
        // Seat number is now handled via Booking, so no need to set it here

        Passenger updatedPassenger = passengerRepository.save(existingPassenger);
        return PassengerMapper.toResponseDTO(updatedPassenger);
    }

    public void deletePassenger(int id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with ID: " + id));
        passengerRepository.delete(passenger);
    }
}