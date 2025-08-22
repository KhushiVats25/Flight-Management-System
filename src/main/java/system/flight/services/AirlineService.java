package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import system.flight.dto.AirlinesDTO;
import system.flight.dto.ApiResponseDTO;
import system.flight.entities.Airline;
import system.flight.entities.User;
import system.flight.mapper.AirlineMapper;
import system.flight.repository.AirlineRepository;
import system.flight.utility.OwnershipUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private UserService userService;

    public ApiResponseDTO<AirlinesDTO> createAirline(AirlinesDTO dto) {
        if (dto.getOwnerId() == null) {
            return new ApiResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "Owner ID is required", null);
        }

        try {
            var owner = userService.getUserById(dto.getOwnerId());

            boolean exists = airlineRepository.existsByNameOrCode(dto.getName(), dto.getCode());
            if (exists) {
                return new ApiResponseDTO<>(HttpStatus.CONFLICT.value(),
                        "Airline already exists with name '" + dto.getName() + "' or code '" + dto.getCode() + "'",
                        null);
            }

            Airline airline = AirlineMapper.toEntity(dto, owner);
            Airline saved = airlineRepository.save(airline);
            return new ApiResponseDTO<>(HttpStatus.OK.value(), "Airline created successfully", AirlineMapper.toDTO(saved));

        } catch (RuntimeException e) {
            return new ApiResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Invalid Owner ID: " + dto.getOwnerId(), null);
        }
    }

    public ApiResponseDTO<List<AirlinesDTO>> getAllAirlines() {
            List<AirlinesDTO> airlines = airlineRepository.findAll()
                    .stream()
                    .filter(airline -> !Boolean.TRUE.equals(airline.getIsDeleted()))
                    .map(AirlineMapper::toDTO)
                    .collect(Collectors.toList());

            return new ApiResponseDTO<>(HttpStatus.OK.value(), "Airlines fetched successfully", airlines);

    }

    public ApiResponseDTO<AirlinesDTO> getAirlineById(int id) {
        return airlineRepository.findById(id)
                .filter(airline -> !Boolean.TRUE.equals(airline.getIsDeleted()))
                .map(airline -> new ApiResponseDTO<>(HttpStatus.OK.value(), "Airline fetched successfully", AirlineMapper.toDTO(airline)))
                .orElseGet(() -> new ApiResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Airline not found with ID: " + id, null));

    }

    public ApiResponseDTO<List<AirlinesDTO>> getAirlinesByName(String name) {
        List<Airline> airlines = airlineRepository.findAllByName(name)
                .stream()
                .filter(airline -> !Boolean.TRUE.equals(airline.getIsDeleted()))
                .collect(Collectors.toList());

        if (airlines.isEmpty()) {
            return new ApiResponseDTO<>(HttpStatus.NOT_FOUND.value(), "No airlines found with Name: " + name, null);
        }

        List<AirlinesDTO> airlineDTOs = airlines.stream()
                .map(AirlineMapper::toDTO)
                .collect(Collectors.toList());

        return new ApiResponseDTO<>(HttpStatus.OK.value(), "Airlines fetched successfully", airlineDTOs);

    }


    public ApiResponseDTO<AirlinesDTO> updateAirline(int id, AirlinesDTO dto) {

        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));

        User currentUser = userService.getCurrentAuthenticatedUser();

        OwnershipUtils.validateOwnership(airline.getOwner(), currentUser);

        return airlineRepository.findById(id)
                .map(existing -> {
                    if (dto.getName() != null) existing.setName(dto.getName());
                    if (dto.getHeadquartersCity() != null) existing.setHeadquartersCity(dto.getHeadquartersCity());
                    if (dto.getCode() != null) existing.setCode(dto.getCode());

                    Airline updated = airlineRepository.save(existing);
                    return new ApiResponseDTO<>(HttpStatus.OK.value(), "Airline updated successfully", AirlineMapper.toDTO(updated));
                })
                .orElseGet(() -> new ApiResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Airline not found with ID: " + id, null));
    }

    public ApiResponseDTO<Void> deleteAirline(int id) {
        if (!airlineRepository.existsById(id)) {
            return new ApiResponseDTO<>(HttpStatus.NOT_FOUND.value(), "Airline not found with ID: " + id, null);
        }

        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));

        User currentUser = userService.getCurrentAuthenticatedUser();

        OwnershipUtils.validateOwnership(airline.getOwner(), currentUser);
        airline.setIsDeleted(true);
        airlineRepository.save(airline);
        return new ApiResponseDTO<>(HttpStatus.OK.value(), "Airline successfully deleted", null);
    }


}
