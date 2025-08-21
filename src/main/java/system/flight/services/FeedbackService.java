package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import system.flight.dto.ApiResponseDTO;
import system.flight.dto.FeedbackDTO;
import system.flight.entities.Feedback;
import system.flight.entities.Passenger;
import system.flight.exception.ResourceNotFoundException;
import system.flight.repository.FeedbackRepository;
import system.flight.repository.PassengerRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public ApiResponseDTO<FeedbackDTO> submitFeedback(FeedbackDTO dto) {
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        Feedback feedback = new Feedback();
        feedback.setPassenger(passenger);
        feedback.setFeedbackTime(dto.getFeedbackTime());
        feedback.setFeedbackMessage(dto.getFeedbackMessage());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        dto.setFeedbackId(savedFeedback.getFeedbackId());

        return new ApiResponseDTO<>(HttpStatus.OK.value(), "Feedback submitted successfully", dto);
    }


    public ApiResponseDTO<Feedback> getFeedback(int feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

        return new ApiResponseDTO<>(HttpStatus.OK.value(), "Feedback fetched successfully", feedback);
    }


    public ApiResponseDTO<Void> deleteFeedback(int feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

        feedbackRepository.delete(feedback);

        return new ApiResponseDTO<>(HttpStatus.OK.value(), "Feedback deleted successfully", null);
    }
}
