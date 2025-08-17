package system.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CreateUserDTO {
        private String fullName;
        private LocalDate dateOfBirth;
        private String gender;
        private String username;
        private String password;
        private String address;
        private String emailId;
        private String phoneNo;
        private int roleId;
    }
