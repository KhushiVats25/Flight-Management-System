package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.BookingUpdateDTO;
import system.flight.dto.PaymentsRequestDTO;
import system.flight.entities.*;
import system.flight.enums.BookingStatus;
import system.flight.enums.PaymentStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.PaymentMapper;
import system.flight.repository.*;

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

    @Autowired
    private PaymentRepository paymentRepository;

    public Booking createBooking(int userId, int aircraftId, double amount, String seatName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        Seat seat = aircraft.getSeats().stream()
                .filter(s -> s.getSeatName().equalsIgnoreCase(seatName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Seat not found"));

        if (seat.isBooked()) {
            throw new IllegalArgumentException("Seat already booked. Please choose another.");
        }

        seat.setBooked(true);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAircraft(aircraft);
        booking.setAmount(amount);
        booking.setSeat(seat);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        seat.setBooking(booking);
        Booking savedBooking = bookingRepository.save(booking);

        // Create PaymentRequestDTO for mapper
        PaymentsRequestDTO paymentDTO = new PaymentsRequestDTO();
        paymentDTO.setBookingId(savedBooking.getBookingId());
        paymentDTO.setUserId(user.getUserId());
        paymentDTO.setAmountPaid(amount);
        paymentDTO.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentDTO.setPaymentMethod("AUTO");


        Payment payment = PaymentMapper.toEntity(paymentDTO, savedBooking, user);
        paymentRepository.save(payment);

        return savedBooking;
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
        booking.setBookingStatus(newStatus);

        if (newStatus == BookingStatus.CANCELLED) {
            // Release seat
            if (booking.getSeat() != null) {
                Seat seat = booking.getSeat();
                seat.setBooked(false);
                seat.setBooking(null);
                booking.setSeat(null);
            }

            // Update payment status to REFUNDED for successful payments
            List<Payment> payments = paymentRepository.findByBooking_BookingId(bookingId);
            for (Payment payment : payments) {
                if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
                    payment.setPaymentStatus(PaymentStatus.REFUNDED);
                    payment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    paymentRepository.save(payment);
                }
            }

        } else if (newStatus == BookingStatus.CONFIRMED) {
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

            if (booking.getSeat() != null) {
                Seat oldSeat = booking.getSeat();
                oldSeat.setBooked(false);
                oldSeat.setBooking(null);
            }

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
