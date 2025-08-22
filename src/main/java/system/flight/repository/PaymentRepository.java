package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Payment;
import system.flight.enums.PaymentStatus;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBooking_BookingId(int bookingId);
    List<Payment> findByPaymentStatus(PaymentStatus status);

}
