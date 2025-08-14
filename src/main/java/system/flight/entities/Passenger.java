package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "booking_id_fk", nullable = false)
    private Booking booking;


    @ManyToOne
    @JoinColumn(name = "user_id_fk", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "gender", nullable = false,length = 3)
    private String gender;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;
}

