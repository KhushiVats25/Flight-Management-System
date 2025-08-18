package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.BookingResponseDTO;
import system.flight.dto.BookingUpdateDTO;
import system.flight.entities.Booking;
import system.flight.entities.Seat;
import system.flight.entities.User;
import system.flight.entities.Aircraft;
import system.flight.enums.BookingStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.repository.BookingRepository;
import system.flight.repository.UserRepository;
import system.flight.repository.AircraftRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    public Booking createBooking(int userId, int aircraftId, double amount, String seatName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        // Find the seat by name
        Seat seat = aircraft.getSeats().stream()
                .filter(s -> s.getSeatName().equalsIgnoreCase(seatName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Seat not found"));

        // Check if seat is already booked
        if (seat.isBooked()) {
            throw new IllegalArgumentException("Seat already booked. Please choose another.");
        }

        // Mark seat as booked
        seat.setBooked(true);

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAircraft(aircraft);
        booking.setAmount(amount);
        booking.setSeat(seat); // Link seat to booking
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        seat.setBooking(booking); // Optional: link booking back to seat

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(int id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }
    public Booking updateBooking(int bookingId, BookingUpdateDTO dto) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        BookingStatus newStatus = BookingStatus.valueOf(dto.getBookingStatus().toUpperCase());

        // Handle status update
        booking.setBookingStatus(newStatus);

        if (newStatus == BookingStatus.CANCELLED) {
            // Release seat if any
            if (booking.getSeat() != null) {
                Seat seat = booking.getSeat();
                seat.setBooked(false);
                seat.setBooking(null);
                booking.setSeat(null);
            }
        } else if (newStatus == BookingStatus.CONFIRMED) {
            // Seat name must be provided
            if (dto.getSeatName() == null || dto.getSeatName().isEmpty()) {
                throw new IllegalArgumentException("Seat name is required when booking is confirmed.");
            }

            Aircraft aircraft = booking.getAircraft();
            Seat newSeat = aircraft.getSeats().stream()
                    .filter(s -> s.getSeatName().equalsIgnoreCase(dto.getSeatName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Seat not found"));

            if (newSeat.isBooked()) {
                throw new IllegalArgumentException("Seat is already booked.");
            }

            // Release old seat
            if (booking.getSeat() != null) {
                Seat oldSeat = booking.getSeat();
                oldSeat.setBooked(false);
                oldSeat.setBooking(null);
            }

            // Assign new seat
            newSeat.setBooked(true);
            newSeat.setBooking(booking);
            booking.setSeat(newSeat);
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        bookingRepository.delete(booking);
    }
}