package system.flight.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import system.flight.dto.*;
import system.flight.entities.*;
import system.flight.enums.BookingStatus;
import system.flight.enums.PaymentStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.BookingMapper;
import system.flight.repository.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AircraftRepository aircraftRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        // No manual instantiation needed
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setUserId(1);
        dto.setAircraftId(1);
        dto.setTotalAmount(500.0);
        dto.setBookingStatus(BookingStatus.CONFIRMED);

        BookingRequestDTO.PassengerDTO passenger = new BookingRequestDTO.PassengerDTO();
        passenger.setName("John");
        passenger.setAge(30);
        passenger.setGender("Male");
        passenger.setSeatNumber("A1");

        dto.setPassengers(List.of(passenger));

        User user = new User();
        user.setUserId(1);

        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftId(1);

        Seat seat = new Seat();
        seat.setSeatName("A1");
        seat.setBooked(false);

        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(aircraftRepository.findById(1)).thenReturn(Optional.of(aircraft));
        when(seatRepository.findBySeatNameAndAircraftAircraftId("A1", 1)).thenReturn(Optional.of(seat));
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(passengerRepository.saveAll(any(List.class)))
                .thenReturn(List.of(passenger));
        when(paymentRepository.save(any())).thenReturn(payment);

        BookingResponseDTO response = bookingService.createBooking(dto);

        assertNotNull(response);
        assertEquals(500.0, response.getTotalAmount());
        assertTrue(response.isConfirmed());
    }

    @Test
    void testGetAllBookings() {
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setTotalAmount(300.0);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUser(new User());
        booking.setAircraft(new Aircraft());
        booking.setPassengers(new ArrayList<>());

        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<BookingResponseDTO> bookings = bookingService.getAllBookings();

        assertEquals(1, bookings.size());
        assertEquals(300.0, bookings.get(0).getTotalAmount());
    }
//
    @Test
    void testGetBookingById_Success() {
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setTotalAmount(300.0);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUser(new User());
        booking.setAircraft(new Aircraft());
        booking.setPassengers(new ArrayList<>());

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        BookingResponseDTO response = bookingService.getBookingById(1);

        assertNotNull(response);
        assertEquals(300.0, response.getTotalAmount());
    }

    @Test
    void testUpdateBooking_Cancelled() {
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setAircraft(new Aircraft());

        Seat seat = new Seat();
        seat.setSeatName("A1");
        seat.setBooked(true);
        booking.setSeat(seat);


        User user = new User();
        user.setUserId(1);
        booking.setUser(user);


        BookingUpdateDTO dto = new BookingUpdateDTO();
        dto.setBookingStatus("CANCELLED");

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(paymentRepository.findByBooking_BookingId(1)).thenReturn(new ArrayList<>());
        when(bookingRepository.save(any())).thenReturn(booking);

        BookingResponseDTO response = bookingService.updateBooking(1, dto);

        assertTrue(response.isCancelled());
    }

    @Test
    void testDeleteBooking_Success() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).delete(booking);

        assertDoesNotThrow(() -> bookingService.deleteBooking(1));
        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    void testGetBookingById_NotFound() {
        when(bookingRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(99));
    }
}
