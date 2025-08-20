package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.flight.entities.Payment;
import system.flight.services.PaymentService;

import java.util.List;


@RestController
@RequestMapping("/api/payments")
public  class PaymentController{

        @Autowired
        private PaymentService paymentService;

        @GetMapping
        public ResponseEntity<List<Payment>> getAllPayments() {
            List<Payment> payments = paymentService.getAllPayments();
            return ResponseEntity.ok(payments);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Payment> getPaymentById(@PathVariable int id) {
            Payment payment = paymentService.getPaymentById(id);
            return ResponseEntity.ok(payment);
        }
}

