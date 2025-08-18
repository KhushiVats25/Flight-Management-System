package system.flight.mapper;

import system.flight.dto.BookingResponseDTO;
import system.flight.dto.BookingRequestDTO;
import system.flight.entities.Aircraft;
import system.flight.entities.Booking;
import system.flight.entities.User;
import system.flight.enums.BookingStatus;

import java.sql.Timestamp;

public class BookingMapper {

    public static Booking toEntity(BookingRequestDTO dto, User user, Aircraft aircraft) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAircraft(aircraft);

        booking.setAmount(dto.getTotalAmount() != null ? dto.getTotalAmount() : 0.0);
        booking.setBookingStatus(dto.isBookingStatus() ? BookingStatus.CONFIRMED : BookingStatus.CANCELLED);
       // booking.setBookingStatus(dto.isBookingStatus() ? "CONFIRMED" : "PENDING");
        booking.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Timestamp(System.currentTimeMillis()));

        return booking;
    }

    public static BookingResponseDTO toDto(Booking booking) {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(String.valueOf(booking.getBookingId()));
        response.setUserId(String.valueOf(booking.getUser().getUserId()));
        response.setAircraftId(String.valueOf(booking.getAircraft().getAircraftId()));
        response.setTotalAmount(booking.getAmount()); // Keep as double for accuracy
       // response.setBookingStatus("CONFIRMED".equalsIgnoreCase(booking.getBookingStatus()));
        response.setBookingStatus(BookingStatus.CONFIRMED.equals(booking.getBookingStatus()));

        return response;
    }

}
