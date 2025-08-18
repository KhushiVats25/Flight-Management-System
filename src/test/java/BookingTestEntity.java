import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "booking_test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id_pk")
    private int bookingId;

    // Replacing relationships with primitive fields
    @Column(name = "user_id_fk")
    private int userId;

    @Column(name = "aircraft_id_fk", nullable = false)
    private int aircraftId;

    @Column(name = "total_amount", nullable = false)
    private double amount;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
}