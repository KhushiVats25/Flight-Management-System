package system.flight.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.AircraftResponseDTO;
import system.flight.dto.AircraftsDTO;
import system.flight.entities.*;
import system.flight.enums.AircraftStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.AircraftMapper;
import system.flight.repository.AircraftRepository;
import system.flight.repository.AirlineRepository;
import system.flight.repository.RouteRepository;
import system.flight.repository.SeatRepository;
import system.flight.utility.OwnershipUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserService userService;

    public AircraftResponseDTO createAircraft(AircraftsDTO dto) {
        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));

        String flightNumber = generateFlightNumber(airline.getName());
        dto.setFlightNumber(flightNumber);


        Aircraft aircraft=AircraftMapper.toEntity(dto,airline,route);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        List<Seat> seats = generateSeats(savedAircraft);
        seatRepository.saveAll(seats);
        savedAircraft.setSeats(seats);

        return AircraftMapper.toResponseDTO(savedAircraft);
    }

    private String generateFlightNumber(String airlineName) {
        String prefix = airlineName.substring(0, 3).toUpperCase();
        int randomNumber = new Random().nextInt(9000) + 1000;
        return prefix + randomNumber;
    }

    private List<Seat> generateSeats(Aircraft aircraft) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= aircraft.getRows(); row++) {
            for (char col = 'A'; col < 'A' + aircraft.getSeatsPerRow(); col++) {
                Seat seat = new Seat();
                seat.setSeatName(row + String.valueOf(col));
                seat.setAircraft(aircraft);
                seat.setBooked(false);
                seats.add(seat);
            }
        }
        return seats;
    }

    public List<AircraftResponseDTO> getAllAircrafts() {
        List<Aircraft> aircrafts = aircraftRepository.findAll();
        List<AircraftResponseDTO> responseDTOS = new ArrayList<>();

        for (Aircraft a : aircrafts) {
            if (!Boolean.TRUE.equals(a.getIsDeleted())) {
                responseDTOS.add(AircraftMapper.toResponseDTO(a));
            }
        }

        return responseDTOS;
    }


    public AircraftResponseDTO getAircraftById(int id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .filter(a -> !Boolean.TRUE.equals(a.getIsDeleted()))
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft Not Found or has been deleted"));


        return AircraftMapper.toResponseDTO(aircraft);
    }


    public AircraftResponseDTO updateAircraft(int id, AircraftsDTO dto) {
        Aircraft existingAircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route not found"));


        User currentUser = userService.getCurrentAuthenticatedUser();

        OwnershipUtils.validateOwnership(existingAircraft.getAirline().getOwner(), currentUser);

        existingAircraft.setAirline(airline);
        existingAircraft.setRoute(route);
        existingAircraft.setFlightNumber(dto.getFlightNumber());
        existingAircraft.setRows(dto.getRows());
        existingAircraft.setSeatsPerRow(dto.getSeatsPerRow());
        existingAircraft.setTotalSeats(dto.getTotalSeats());
        existingAircraft.setPricePerSeat(dto.getPricePerSeat());
        existingAircraft.setAircraftStatus(dto.getAircraftStatus());

        return AircraftMapper.toResponseDTO(aircraftRepository.save(existingAircraft));
    }

    public void deleteAircraft(int id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with ID: " + id));

        User currentUser = userService.getCurrentAuthenticatedUser();

        OwnershipUtils.validateOwnership(aircraft.getAirline().getOwner(), currentUser);
        aircraft.setIsDeleted(true);
        aircraftRepository.save(aircraft);
    }



}

