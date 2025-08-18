package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.AirlinesDTO;
import system.flight.entities.Airline;
import system.flight.entities.User;
import system.flight.mapper.AirlineMapper;
import system.flight.repository.AirlineRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private UserService userService;

    public List<AirlinesDTO> getAllAirlines() {
        return airlineRepository.findAll()
                .stream()
                .map(AirlineMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AirlinesDTO getAirlineById(int id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with ID: " + id));
        return AirlineMapper.toDTO(airline);
    }

    public AirlinesDTO createAirline(AirlinesDTO dto) {
        if (dto.getOwnerId() == null) {
            throw new IllegalArgumentException("Owner ID is required");
        }

        User owner;
        try {
            owner = userService.getUserById(dto.getOwnerId());
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid Owner ID: '" + dto.getOwnerId() + "'. Please enter a correct Owner ID.");
        }

        boolean exists = airlineRepository.existsByNameOrCode(dto.getName(), dto.getCode());
        if (exists) {
            throw new RuntimeException("Airline already exists with name '" + dto.getName() + "' or code '" + dto.getCode() + "'");
        }

        Airline airline = AirlineMapper.toEntity(dto, owner);
        Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toDTO(saved);
    }

    public AirlinesDTO updateAirline(int id, AirlinesDTO dto) {
        Airline existing = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with ID: " + id));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        if (dto.getCountry() != null) {
            existing.setHeadquartersCity(dto.getCountry());
        }

        if (dto.getCode() != null) {
            existing.setCode(dto.getCode());
        }

        Airline updated = airlineRepository.save(existing);
        return AirlineMapper.toDTO(updated);
    }

    public void deleteAirline(int id) {
        if (!airlineRepository.existsById(id)) {
            throw new RuntimeException("Airline not found with ID: " + id);
        }
        airlineRepository.deleteById(id);
    }
}
