package system.flight.mapper;

import system.flight.entities.Role;
import system.flight.entities.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static User toEntity(UserRegistrationDTO dto, Role role, String passwordSalt, String passwordHash) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(dto.getGender());
        user.setUsername(dto.getUsername());
        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);
        user.setEmailId(dto.getEmailId());
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setAuthStatus(true);  // default unverified
        return user;
    }

}
