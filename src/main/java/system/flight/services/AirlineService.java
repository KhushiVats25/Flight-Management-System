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
                .orElseThrow(() -> new RuntimeException("Airline not found"));
        return AirlineMapper.toDTO(airline);
    }

    public AirlinesDTO createAirline(AirlinesDTO dto) {

        if (dto.getOwnerId() == null) {
            throw new IllegalArgumentException("Owner ID is required");
        }


        User owner = userService.getUserById(dto.getOwnerId());

        Airline airline = AirlineMapper.toEntity(dto, owner);
        Airline saved = airlineRepository.save(airline);
        return AirlineMapper.toDTO(saved);
    }

    public AirlinesDTO updateAirline(int id, AirlinesDTO dto) {
        Airline existing = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found"));

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
        airlineRepository.deleteById(id);
    }
}
