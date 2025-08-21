package system.flight.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import system.flight.enums.BookingStatus;

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
    @JsonIgnore
    private Aircraft aircraft;

//    @OneToOne
//    private Seat seat;

//    @ManyToOne
//    @JoinColumn(name = "seat_seat_id", nullable = false)
//    private Seat seat;


    @ManyToOne(optional = true)
    @JoinColumn(name = "seat_seat_id", nullable = true)
    private Seat seat;




    @Column(name="total_amount",nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @Column(name="created_at" , nullable=false)
    private Timestamp createdAt;


}
