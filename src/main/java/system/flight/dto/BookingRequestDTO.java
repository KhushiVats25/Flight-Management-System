package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {

    private Integer userId;
    private Integer aircraftId;
    private Double totalAmount;
    private boolean bookingStatus;
    private Timestamp createdAt;
    private String seatName;


}

