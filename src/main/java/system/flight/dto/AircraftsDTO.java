package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.flight.enums.AircraftStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftsDTO {
    private int aircraftId;
    private int airlineId;
    private int routeId;
    private String flightNumber;

    private int rows;

    private int seatsPerRow;

    private int totalSeats;

    private double pricePerSeat;


    private AircraftStatus aircraftStatus;





}
