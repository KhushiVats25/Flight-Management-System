package system.flight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {


    @JsonProperty("address")
    private String address;

    @JsonProperty("phoneNo")
    private String phoneNo;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("fullName")
    private String fullName;


//    @JsonFormat(pattern = "yyyy-MM-dd")
//
//    private LocalDate dateOfBirth;

    @JsonProperty("emailId")
    private String emailId;



    private MultipartFile profileImage;   // single image
    private List<MultipartFile> idDocs;
}
