package system.flight.mapper;

import system.flight.dto.AircraftsDTO;
import system.flight.entities.Aircraft;
import system.flight.entities.Airline;
import system.flight.entities.Route;

public class AircraftMapper {

    public static AircraftsDTO toDTO(Aircraft aircraft){
        return new AircraftsDTO(aircraft.getAircraftId(),aircraft.getAirline().getAirlineId(),aircraft.getRoute().getRouteId(),aircraft.getFlightNumber(),aircraft.getRows(),aircraft.getSeatsPerRow(),aircraft.getTotalSeats(),aircraft.getPricePerSeat(),aircraft.getAircraftStatus());
    }

    public static Aircraft toEntity(AircraftsDTO dto, Airline airline, Route route){
        Aircraft aircraft=new Aircraft();
        aircraft.setAircraftId(dto.getAircraftId());
        aircraft.setAirline(airline);
        aircraft.setRoute(route);
        aircraft.setFlightNumber(dto.getFlightNumber());
        aircraft.setRows(dto.getRows());
        aircraft.setSeatsPerRow(dto.getSeatsPerRow());
        aircraft.setTotalSeats(dto.getTotalSeats());
        aircraft.setPricePerSeat(dto.getPricePerSeat());
        aircraft.setAircraftStatus(dto.getAircraftStatus());

        return aircraft;
    }
}
