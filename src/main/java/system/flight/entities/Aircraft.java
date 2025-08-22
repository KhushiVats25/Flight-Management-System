package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;
import system.flight.enums.AircraftStatus;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "rows", nullable = false)
    private int rows;

    @Column(name = "seats_per_row", nullable = false)
    private int seatsPerRow;

    @Column(name = "total_seats")
    private int totalSeats;


    @Column(name = "price_per_seat", nullable = false)
    private double pricePerSeat;

    @Enumerated(EnumType.STRING)
    @Column(name = "aircraft_status", nullable = false, length = 20)
    private AircraftStatus aircraftStatus;


    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();


    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public boolean isDeleted() {
        return Boolean.TRUE.equals(this.isDeleted);
    }

}
