package system.flight.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "seat_name", nullable = false, length = 5)
    private String seatName;

    @ManyToOne
    @JoinColumn(name = "aircraft_id_fk", nullable = false)
    @JsonIgnore
    private Aircraft aircraft;

    @Column(name = "is_booked", nullable = false)
    private boolean isBooked = false;
}
