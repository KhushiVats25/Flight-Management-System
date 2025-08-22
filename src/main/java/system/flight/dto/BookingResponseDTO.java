package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private long bookingId;
    private int userId;
    private int aircraftId;
    private double totalAmount;
    boolean confirmed;
    boolean cancelled;
    private LocalDateTime createdAt;
    private List<PassengerInfo> passengers;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PassengerInfo {
        private int passengerId;
        private String name;
        private int age;
        private String gender;
        private String seatNumber;
    }

}
