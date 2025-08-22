package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.*;

import system.flight.entities.User;
import system.flight.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CreateUserResponseDTO>> registerUser(@RequestBody CreateUserDTO userDTO) {
        CreateUserResponseDTO savedUser = userService.registerUser(userDTO);

        ApiResponseDTO<CreateUserResponseDTO> response = new ApiResponseDTO<>(
                201,
                "User registered successfully",
                savedUser
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO<User>> getUser(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "User retrieved successfully", user));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "All users retrieved successfully", users));
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<ApiResponseDTO<UserProfileResponseDTO>> updateUserProfile(
            @PathVariable int userId,
            @ModelAttribute UserProfileDTO userProfileDTO) {

        UserProfileResponseDTO updatedProfile = userService.updateUserProfile(userId, userProfileDTO);
        ApiResponseDTO<UserProfileResponseDTO> response =
                new ApiResponseDTO<>(200, "Profile updated successfully", updatedProfile);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "User deleted successfully", null));
    }

}
