package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.flight.dto.ApiResponseDTO;
import system.flight.dto.PaymentsResponseDTO;
import system.flight.entities.Payment;
import system.flight.services.PaymentService;

import java.util.List;


@RestController
@RequestMapping("/api/payments")
public  class PaymentController{

        @Autowired
        private PaymentService paymentService;

        @GetMapping
        public ResponseEntity<ApiResponseDTO<List<Payment>>> getAllPayments() {
            List<Payment> retrievedPayments = paymentService.getAllPayments();
            return ResponseEntity.ok(new ApiResponseDTO<>(200, "All payments retrieved successfully",retrievedPayments));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<Payment>> getPaymentById(@PathVariable int id) {
            Payment retrievedPayment = paymentService.getPaymentById(id);
            return ResponseEntity.ok(new ApiResponseDTO<>(200, "Payment details retrieved successfully",retrievedPayment));
        }

        @GetMapping("/status/{status}")
        public ResponseEntity<List<PaymentsResponseDTO>> getPaymentsByStatus(@PathVariable String status) {
            List<PaymentsResponseDTO> payments = paymentService.getPaymentsByStatus(status);
            return ResponseEntity.ok(payments);
        }
}

