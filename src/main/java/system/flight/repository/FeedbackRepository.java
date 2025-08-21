package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.flight.entities.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}

