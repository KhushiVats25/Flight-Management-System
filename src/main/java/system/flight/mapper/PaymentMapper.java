package system.flight.mapper;

import system.flight.dto.PaymentsRequestDTO;
import system.flight.dto.PaymentsResponseDTO;
import system.flight.entities.Booking;
import system.flight.entities.Payment;
import system.flight.entities.User;

import java.sql.Timestamp;
import java.util.UUID;

public class PaymentMapper {

    public static Payment toEntity(PaymentsRequestDTO dto, Booking booking, User user) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setUser(user);
        payment.setAmountPaid(dto.getAmountPaid());
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setTransactionId(generateTransactionId());
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        payment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        payment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return payment;
    }

    public static PaymentsResponseDTO toDTO(Payment payment) {
        PaymentsResponseDTO dto = new PaymentsResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBooking().getBookingId());
        dto.setUserId(payment.getUser().getUserId());
        dto.setAmountPaid(payment.getAmountPaid());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());
        return dto;
    }

    private static String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
