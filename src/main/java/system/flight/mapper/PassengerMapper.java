package system.flight.mapper;

import system.flight.dto.PassangerDTO;
import system.flight.dto.PassangerResponseDTO;
import system.flight.entities.Booking;
import system.flight.entities.Passenger;
import system.flight.entities.User;

public class PassengerMapper {

    // Convert Entity to DTO (used for internal logic or updates)
    public static PassangerDTO toDTO(Passenger passenger) {
        PassangerDTO dto = new PassangerDTO();
        dto.setPassengerId(passenger.getPassengerId());
        dto.setBookingId(passenger.getBooking().getBookingId());
        dto.setUserId(passenger.getUser().getUserId());
        dto.setName(passenger.getName());
        dto.setAge(passenger.getAge());
        dto.setGender(passenger.getGender());
        dto.setSeatNumber(passenger.getBooking().getSeat()); // Seat comes from Booking
        return dto;
    }

    // Convert DTO to Entity (used during creation or update)
    public static Passenger toEntity(PassangerDTO dto, Booking booking, User user) {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(dto.getPassengerId());
        passenger.setBooking(booking);
        passenger.setUser(user);
        passenger.setName(dto.getName());
        passenger.setAge(dto.getAge());
        passenger.setGender(dto.getGender());
        passenger.setSeatNumber((booking.getSeat()));
        // Seat number is managed via Booking, not set directly in Passenger
        return passenger;
    }

    // Convert Entity to ResponseDTO (used for API responses)
    public static PassangerResponseDTO toResponseDTO(Passenger passenger) {
        PassangerResponseDTO response = new PassangerResponseDTO();
        response.setPassengerId(passenger.getPassengerId());
        response.setName(passenger.getName());
        response.setGender(passenger.getGender());
        response.setSeatNumber(passenger.getBooking().getSeat()); // Seat from Booking
        return response;
    }
}