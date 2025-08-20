package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.BookingRequestDTO;
import system.flight.dto.BookingResponseDTO;
import system.flight.dto.BookingUpdateDTO;
import system.flight.dto.PassengerInfoDTO;
import system.flight.entities.*;
import system.flight.enums.BookingStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.BookingMapper;
import system.flight.mapper.PassengerMapper;
import system.flight.repository.*;

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

    public BookingResponseDTO createBooking(BookingRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found"));
        if (dto.getPassengers() == null || dto.getPassengers().isEmpty()) {
            throw new IllegalArgumentException("At least one passenger is required.");
        }
        String seatNumber = dto.getPassengers().get(0).getSeatNumber();
        if (seatNumber == null || seatNumber.isEmpty()) {
            throw new IllegalArgumentException("Seat number is required for booking.");
        }
        String seatName = dto.getPassengers().get(0).getSeatNumber();

        Seat seat = seatRepository.findBySeatNameAndAircraftAircraftId(seatName, dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found for seat number: " + seatName + " on aircraft ID: " + dto.getAircraftId()));

        // Map DTO to entity
        Booking booking = BookingMapper.toEntity(dto, user, aircraft);
        booking.setBookingStatus(dto.getBookingStatus());
        booking.setSeat(seat); // ✅ Set the required seat

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Map and save passengers
        List<Passenger> passengers = dto.getPassengers().stream()
                .map(p -> PassengerMapper.toEntity(p, savedBooking, user))
                .collect(Collectors.toList());

        passengerRepository.saveAll(passengers);
        savedBooking.setPassengers(passengers);

        return BookingMapper.toResponseDTO(savedBooking);
    }




    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingById(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return BookingMapper.toResponseDTO(booking);
    }

    public BookingResponseDTO updateBookingStatus(long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setBookingStatus(status);
        return BookingMapper.toResponseDTO(bookingRepository.save(booking));
    }
    public BookingResponseDTO updateBooking(long id, BookingUpdateDTO dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        BookingStatus status = BookingStatus.valueOf(dto.getBookingStatus().toUpperCase());
        booking.setBookingStatus(status);

        if (status == BookingStatus.CONFIRMED && dto.getSeatName() != null && !dto.getSeatName().isEmpty()) {
            booking.setSeatName(dto.getSeatName());
        }

        Booking updatedBooking = bookingRepository.save(booking);
        return BookingMapper.toResponseDTO(updatedBooking);
    }

    public void deleteBooking(long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        bookingRepository.delete(booking);
    }
}