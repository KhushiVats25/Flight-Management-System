package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.entities.Seat;
import system.flight.exception.ResourceNotFoundException;
import system.flight.repository.SeatRepository;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat getSeatByName(String seatName, int aircraftId) {
        return seatRepository.findBySeatNameAndAircraftAircraftId(seatName,aircraftId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found for seat name: " + seatName));
    }

    public boolean isSeatBooked(String seatName, int aircraftId) {
        Seat seat = getSeatByName(seatName, aircraftId);
        return seat.isBooked();
    }

    public void markSeatAsBooked(String seatName, int aircraftId) {
        Seat seat = getSeatByName(seatName, aircraftId);
        seat.setBooked(true);
        seatRepository.save(seat);
    }

    public void markSeatAsAvailable(String seatName, int aircraftId) {
        Seat seat = getSeatByName(seatName, aircraftId);
        seat.setBooked(false);
        seatRepository.save(seat);
    }

}