package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;
import system.flight.enums.AircraftStatus;

@Entity
@Table(name = "aircrafts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id")
    private int aircraftId;

    @ManyToOne
    @JoinColumn(name = "airline_id_fk")
    private Airline airline;


    @Column(name = "flight_number", nullable = false, unique = true, length = 10)
    private String flightNumber;


    @ManyToOne
    @JoinColumn(name = "route_id_fk")
    private Route route;

    @Column(name = "seat_name", length = 5)
    private String seatsName;

    @Column(name = "price_per_seat", nullable = false)
    private double pricePerSeat;

    @Enumerated(EnumType.STRING)
    @Column(name = "aircraft_status", nullable = false, length = 20)
    private AircraftStatus aircraftStatus;
}
