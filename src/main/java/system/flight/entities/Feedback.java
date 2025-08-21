package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @ManyToOne
    @JoinColumn(name = "passenger_id_fk", nullable = false)
    private Passenger passenger;

    @Column(name = "feedback_time", nullable = false)
    private LocalDateTime feedbackTime;

    @Column(name = "message", nullable = false, length = 1000)
    private String feedbackMessage;
}

