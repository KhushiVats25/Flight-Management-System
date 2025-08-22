package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.flight.enums.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsRequestDTO {
    private int bookingId;
    private int userId;
    private double amountPaid;
    private PaymentStatus paymentStatus;
    private String paymentMethod;
}
