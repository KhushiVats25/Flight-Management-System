package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.flight.enums.BookingStatus;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {

    private int userId;
    private int aircraftId;
    private double totalAmount;
    private BookingStatus bookingStatus;

    private List<PassengerDTO> passengers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PassengerDTO {
        private String name;
        private int age;
        private String gender;
        private String seatNumber;
    }



}

