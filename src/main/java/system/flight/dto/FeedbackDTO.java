package system.flight.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private int feedbackId;
    private int passengerId;
    private LocalDateTime feedbackTime;
    private String feedbackMessage;
}
