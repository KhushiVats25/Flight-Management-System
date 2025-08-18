package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AirlinesDTO {
    private int id;
    private String name;
    private String code;
    private String country;
    private Integer ownerId;
}


