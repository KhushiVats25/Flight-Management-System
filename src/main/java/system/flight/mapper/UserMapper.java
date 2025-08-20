package system.flight.mapper;

import system.flight.dto.CreateUserDTO;
import system.flight.dto.CreateUserResponseDTO;
import system.flight.dto.UserProfileResponseDTO;
import system.flight.entities.Role;
import system.flight.entities.User;


import java.time.LocalDateTime;

public class UserMapper {

    public static User toEntity(CreateUserDTO dto, Role role, String passwordSalt, String passwordHash) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setGender(dto.getGender());
        user.setUsername(dto.getUsername());
        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);
        user.setEmailId(dto.getEmailId());
        user.setRole(role);
        user.setAddress(dto.getAddress());
        user.setPhoneNo(dto.getPhoneNo());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setAuthStatus(true);  // default unverified
        return user;
    }


    public static CreateUserResponseDTO toDto(User user) {
        CreateUserResponseDTO userResponseDTO = new CreateUserResponseDTO();

        userResponseDTO.setFullName(user.getFullName());
        userResponseDTO.setDateOfBirth(user.getDateOfBirth());
        userResponseDTO.setGender(user.getGender());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmailId(user.getEmailId());
        userResponseDTO.setPhoneNo(user.getPhoneNo());

        return userResponseDTO;
    }

    public static UserProfileResponseDTO toProfileResponseDTO(User user) {
        return new UserProfileResponseDTO(
//                user.getUserId(),
//                user.getFullName(),
                user.getAddress(),
                user.getPhoneNo(),
//                user.getEmailId(),
//                user.getGender(),
                user.getProfileImage() != null ? "path/to/image/storage/" + user.getUserId() + ".jpg" : null,
                user.getIDocs()
        );
    }

}
