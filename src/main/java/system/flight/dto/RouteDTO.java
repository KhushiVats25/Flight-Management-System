package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {
    private int RouteId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String sourceCity;
    private String destinationCity;
    private int distanceInKm;
    private LocalTime estimatedTime;


}