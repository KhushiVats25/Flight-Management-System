package system.flight.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.ApiResponseDTO;
import system.flight.dto.FeedbackDTO;
import system.flight.entities.Feedback;
import system.flight.services.FeedbackService;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<FeedbackDTO>> submitFeedback(@RequestBody FeedbackDTO dto) {
        return ResponseEntity.ok(feedbackService.submitFeedback(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Feedback>> getFeedback(@PathVariable int id) {
        return ResponseEntity.ok(feedbackService.getFeedback(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteFeedback(@PathVariable int id) {
        return ResponseEntity.ok(feedbackService.deleteFeedback(id));
    }


}

