package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDTO {
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String username;
    private String emailId;
    private String phoneNo;

}
