package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.flight.enums.PaymentStatus;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsResponseDTO {
    private int paymentId;
    private int bookingId;
    private int userId;
    private double amountPaid;
    private PaymentStatus paymentStatus;
    private String paymentMethod;
    private String transactionId;
    private Timestamp paymentDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
