package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.PaymentsResponseDTO;
import system.flight.entities.Payment;
import system.flight.enums.PaymentStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.PaymentMapper;
import system.flight.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(int paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    public List<PaymentsResponseDTO> getPaymentsByStatus(String statusStr) {
        try {
            PaymentStatus status = PaymentStatus.valueOf(statusStr.toUpperCase());
            return paymentRepository.findByPaymentStatus(status).stream()
                    .map(PaymentMapper::toDTO)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid payment status: " + statusStr);
        }
    }


}

