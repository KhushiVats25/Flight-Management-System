package system.flight.mapper;

import system.flight.dto.BookingRequestDTO;
import system.flight.dto.PassengerDTO;
import system.flight.dto.PassengerInfoDTO;
import system.flight.entities.Booking;
import system.flight.entities.Passenger;
import system.flight.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class PassengerMapper {

    public static Passenger toEntity(BookingRequestDTO.PassengerDTO dto, Booking booking, User user) {
        return Passenger.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .seatNumber(dto.getSeatNumber())
                .booking(booking)
                .user(user)
                .build();
    }
    public static PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setPassengerId(passenger.getPassengerId());
        dto.setName(passenger.getName());
        dto.setAge(passenger.getAge());
        dto.setGender(passenger.getGender());
        dto.setSeatNumber(passenger.getSeatNumber());
        return dto;
    }

    public static PassengerInfoDTO mapToDTO(Passenger passenger) {
        return new PassengerInfoDTO(
                passenger.getPassengerId(),
                passenger.getName(),
                passenger.getAge(),
                passenger.getGender(),
                passenger.getSeatNumber()
        );
    }

    public static List<PassengerInfoDTO> mapToDTOList(List<Passenger> passengers) {
        return passengers.stream()
                .map(PassengerMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
