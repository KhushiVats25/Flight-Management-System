package system.flight.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Entity
@Table(name = "passengers")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id_pk")
    private int passengerId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    // Link to Booking
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // Link to User (who made the booking)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

