package system.flight.mapper;

import system.flight.dto.FeedbackDTO;
import system.flight.entities.Feedback;
import system.flight.entities.Passenger;
//
public class FeedbackMapper {

    public static Feedback toEntity(FeedbackDTO dto, Passenger passenger) {
        Feedback feedback = new Feedback();
        feedback.setPassenger(passenger);
        feedback.setFeedbackTime(dto.getFeedbackTime());
        feedback.setFeedbackMessage(dto.getFeedbackMessage());
        return feedback;
    }

    public static FeedbackDTO toDTO(Feedback feedback) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setFeedbackId(feedback.getFeedbackId());
        dto.setPassengerId(feedback.getPassenger().getPassengerId());
        dto.setFeedbackTime(feedback.getFeedbackTime());
        dto.setFeedbackMessage(feedback.getFeedbackMessage());
        return dto;
    }
}

