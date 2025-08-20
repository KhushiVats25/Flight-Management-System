package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftResponseDTO {

    private int aircraftId;
    private String airlineName;
    private String sourceCity;
    private String destinationCity;
    private int totalSeats;
    private double pricePerSeat;
    private String aircraftStatus;

}
