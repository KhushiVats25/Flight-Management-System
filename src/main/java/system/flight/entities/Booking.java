package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id_pk")
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id_fk")
    private User user;

    @ManyToOne
    @JoinColumn(name = "aircraft_id_fk", nullable = false)
    private Aircraft aircraft;

    @Column(name="total_amount",nullable = false)
    private double amount;

    @Column(name="booking_status")
    private String bookingStatus;

    @Column(name="created_at" , nullable=false)
    private Timestamp createdAt;
}
