package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDTO {

    private int passengerId;     // Unique identifier
    private String name;         // Passenger's name
    private int age;             // Passenger's age
    private String gender;       // Passenger's gender
    private String seatNumber;   // Assigned seat
}