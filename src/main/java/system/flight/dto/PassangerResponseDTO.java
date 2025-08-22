package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.flight.entities.Seat;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassangerResponseDTO {
    private int passengerId;
    private String name;
    private String gender;
    private Seat seatNumber;


}

