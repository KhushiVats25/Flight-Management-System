package system.flight.mapper;

import org.springframework.stereotype.Component;
import system.flight.dto.BookingRequestDTO;
import system.flight.dto.BookingResponseDTO;
import system.flight.entities.Aircraft;
import system.flight.entities.Booking;
import system.flight.entities.Passenger;
import system.flight.entities.User;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class BookingMapper {
    public static Booking toEntity(BookingRequestDTO dto, User user, Aircraft aircraft) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAircraft(aircraft);
        booking.setTotalAmount(dto.getTotalAmount());
        booking.setBookingStatus(dto.getBookingStatus());
        booking.setCreatedAt(LocalDateTime.now());

        List<Passenger> passengerList = dto.getPassengers().stream().map(p -> {
            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setAge(p.getAge());
            passenger.setGender(p.getGender());
            passenger.setSeatNumber(p.getSeatNumber());
            passenger.setUser(user);
            passenger.setBooking(booking); // Important for bidirectional mapping
            return passenger;
        }).collect(Collectors.toList());

        //booking.setPassengers(passengerList);

        return booking;
    }

    public static BookingResponseDTO toResponseDTO(Booking booking) {
        List<BookingResponseDTO.PassengerInfo> passengerInfos = booking.getPassengers().stream().map(p ->
                new BookingResponseDTO.PassengerInfo(
                        p.getPassengerId(),
                        p.getName(),
                        p.getAge(),
                        p.getGender(),
                        p.getSeatNumber()
                )
        ).collect(Collectors.toList());

        return new BookingResponseDTO(
                booking.getBookingId(),
                booking.getUser().getUserId(),
                booking.getAircraft().getAircraftId(),
                booking.getTotalAmount(),
                booking.isConfirmed(),
                booking.isCancelled(),
                booking.getCreatedAt(),
                passengerInfos
        );


    }
}
