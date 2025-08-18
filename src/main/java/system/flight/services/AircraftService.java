package system.flight.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.AircraftsDTO;
import system.flight.entities.Aircraft;
import system.flight.entities.Airline;
import system.flight.entities.Route;
import system.flight.entities.Seat;
import system.flight.enums.AircraftStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.AircraftMapper;
import system.flight.repository.AircraftRepository;
import system.flight.repository.AirlineRepository;
import system.flight.repository.RouteRepository;
import system.flight.repository.SeatRepository;

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

    public Aircraft createAircraft(AircraftsDTO dto) {
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

        return savedAircraft;
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

    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    public Aircraft getAircraftById(int id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));
    }
}

