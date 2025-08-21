package system.flight.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.*;
import system.flight.entities.*;
import system.flight.enums.BookingStatus;
import system.flight.enums.PaymentStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.BookingMapper;
import system.flight.mapper.PassengerMapper;
import system.flight.mapper.PaymentMapper;
import system.flight.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Seat getSeatByName(String seatName, int aircraftId) {
        return seatRepository.findBySeatNameAndAircraftAircraftId(seatName, aircraftId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found for seat name: " + seatName + " on aircraft ID: " + aircraftId));
    }

    @Autowired
    private PassengerRepository passengerRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }
    @Autowired
    private PaymentRepository paymentRepository;



    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));

        if (dto.getPassengers() == null || dto.getPassengers().isEmpty()) {
            throw new IllegalArgumentException("At least one passenger is required.");
        }

        String seatName = dto.getPassengers().get(0).getSeatNumber();
        if (seatName == null || seatName.isEmpty()) {
            throw new IllegalArgumentException("Seat number is required for booking.");
        }

        Seat seat = seatRepository.findBySeatNameAndAircraftAircraftId(seatName, dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found for seat number: " + seatName + " on aircraft ID: " + dto.getAircraftId()));

        if (seat.isBooked()) {
            throw new IllegalStateException("Seat " + seatName + " is already booked.");
        }

        seat.setBooked(true);
        seatRepository.save(seat);

        Booking booking = BookingMapper.toEntity(dto, user, aircraft);
        booking.setBookingStatus(dto.getBookingStatus());
        booking.setSeat(seat);

        Booking savedBooking = bookingRepository.save(booking);

        List<Passenger> passengers = dto.getPassengers().stream()
                .map(p -> PassengerMapper.toEntity(p, savedBooking, user))
                .collect(Collectors.toList());

        passengerRepository.saveAll(passengers);
        savedBooking.setPassengers(passengers);
//
        // ✅ Add Payment Logic Here
        double amount = dto.getTotalAmount(); // or calculate dynamically if needed

        PaymentsRequestDTO paymentDTO = new PaymentsRequestDTO();
        paymentDTO.setBookingId(savedBooking.getBookingId());
        paymentDTO.setUserId(user.getUserId());
        paymentDTO.setAmountPaid(amount);
        paymentDTO.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentDTO.setPaymentMethod("AUTO");

        Payment payment = PaymentMapper.toEntity(paymentDTO, savedBooking, user);
        paymentRepository.save(payment);

        return bookingMapper.toResponseDTO(savedBooking);
    }






    // currently working create
    //@Transactional
//    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
//
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
//                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));
//        if (dto.getPassengers() == null || dto.getPassengers().isEmpty()) {
//            throw new IllegalArgumentException("At least one passenger is required.");
//        }
//        String seatNumber = dto.getPassengers().get(0).getSeatNumber();
//        if (seatNumber == null || seatNumber.isEmpty()) {
//            throw new IllegalArgumentException("Seat number is required for booking.");
//        }
//        String seatName = dto.getPassengers().get(0).getSeatNumber();
//
//        Seat seat = seatRepository.findBySeatNameAndAircraftAircraftId(seatName, dto.getAircraftId())
//                .orElseThrow(() -> new ResourceNotFoundException("Seat not found for seat number: " + seatName + " on aircraft ID: " + dto.getAircraftId()));
//
//        if (seat.isBooked()) {
//            throw new IllegalStateException("Seat is already booked");
//        }
//
//        seat.setBooked(true); // Mark the seat as booked
//
//        // Map DTO to entity
//
//        Booking booking = BookingMapper.toEntity(dto, user, aircraft);
//        booking.setBookingStatus(dto.getBookingStatus());
//        booking.setSeat(seat); // ✅ Set the required seat
//
//        // Save booking
//        Booking savedBooking = bookingRepository.save(booking);
//
//        // Map and save passengers
//        List<Passenger> passengers = dto.getPassengers().stream()
//                .map(p -> PassengerMapper.toEntity(p, savedBooking, user))
//                .collect(Collectors.toList());
//
//        passengerRepository.saveAll(passengers);
//        savedBooking.setPassengers(passengers);
//
//        return BookingMapper.toResponseDTO(savedBooking);
//    }




    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingById(int id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return BookingMapper.toResponseDTO(booking);
    }


    @Transactional
    public BookingResponseDTO updateBooking(int bookingId, BookingUpdateDTO dto) {
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

            // Release old seat if exists
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

        Booking updatedBooking = bookingRepository.save(booking);
        return BookingMapper.toResponseDTO(updatedBooking);
    }
//    public BookingResponseDTO updateBookingStatus(int id, BookingStatus status) {
//        Booking booking = bookingRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
//        booking.setBookingStatus(status);
//        return BookingMapper.toResponseDTO(bookingRepository.save(booking));
//    }


//    @Transactional
//    public BookingResponseDTO updateBooking(int id, BookingUpdateDTO dto) {
//        Booking booking = bookingRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
//
//        BookingStatus newStatus = BookingStatus.valueOf(dto.getBookingStatus().toUpperCase());
//        booking.setBookingStatus(newStatus);
//
//        if (newStatus == BookingStatus.CONFIRMED) {
//            booking.setSeatName(dto.getSeatName());
//            // Optionally update other fields like totalAmount, etc.
//        } else if (newStatus == BookingStatus.CANCELLED) {
//            // Ensure passengers are removed and deleted
//            booking.getPassengers().clear();
//        }
//
//        Booking savedBooking = bookingRepository.save(booking);
//        return bookingMapper.toResponseDTO(savedBooking); // Assuming you have a mapper
//    }


    public void deleteBooking(int id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        bookingRepository.delete(booking);
    }
}