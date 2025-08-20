package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.flight.entities.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBooking_BookingId(int bookingId);
}
