package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDTO {

    private int userId;
    private String fullName;
    private String address;
    private String phoneNo;
    private String emailId;

//    private LocalDate dateOfBirth;

    private String gender;
    private String profileImageUrl;
    private List<String> idDocUrls;
}
