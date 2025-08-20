package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private String bookingId;
    private String userId;
    private String aircraftId;
    private double totalAmount;
    private boolean bookingStatus;
    private Timestamp createdAt;
}
