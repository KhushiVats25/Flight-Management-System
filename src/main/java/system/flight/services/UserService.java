package system.flight.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;
import system.flight.dto.CreateUserDTO;
import system.flight.dto.CreateUserResponseDTO;
import system.flight.dto.UserProfileDTO;
import system.flight.dto.UserProfileResponseDTO;
import system.flight.entities.Role;
import system.flight.entities.User;
import system.flight.exception.FileStorageException;
import system.flight.exception.ResourceAlreadyExistsException;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.UserMapper;
import system.flight.repository.RoleRepository;
import system.flight.repository.UserRepository;
import system.flight.utility.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public CreateUserResponseDTO registerUser(CreateUserDTO dto) throws IllegalArgumentException {

        // Validate unique username
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceAlreadyExistsException("User", "username", dto.getUsername());
        }

        // Validate unique email
        if (userRepository.existsByEmailId(dto.getEmailId())) {
            throw new ResourceAlreadyExistsException("Email", "emailId", dto.getEmailId());
        }

        // Check whether role is present or not
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + dto.getRoleId()));

        // Generate password salt and hash
        String salt = Utils.generateSalt();
        String newPassword = salt + dto.getPassword();
        String hashedPassword = Utils.generateHash(dto.getPassword(), salt);

        // Map DTO to User entity
        User userToSave = UserMapper.toEntity(dto, role, salt, hashedPassword);

        User savedUser = userRepository.save(userToSave);

        // Return saved user converted to DTO
        return UserMapper.toDto(savedUser );
    }


    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public UserProfileResponseDTO updateUserProfile(int userId, UserProfileDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + String.valueOf(userId) + " not found"));



//        user.setGender(dto.getGender());
//        user.setEmailId(dto.getEmailId());
//
//
//
//        user.setFullName(dto.getFullName());
        user.setAddress(dto.getAddress());
        user.setPhoneNo(dto.getPhoneNo());

        // Profile image
        if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
            try {
                user.setProfileImage(dto.getProfileImage().getBytes());
            } catch (IOException e) {
                throw new FileStorageException("Failed to save profile image");
            }
        }


        if (dto.getIDocs() != null && !dto.getIDocs().isEmpty()) {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : dto.getIDocs()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR + fileName);
                    Files.createDirectories(path.getParent());
                    Files.write(path, file.getBytes());
                    urls.add(path.toString());
                } catch (IOException e) {
                    throw new FileStorageException("Failed to save ID document: " + file.getOriginalFilename());
                }
            }
            user.setIDocs(urls);
        }

        User savedUser = userRepository.save(user);
        return UserMapper.toProfileResponseDTO(savedUser);
    }



    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Cannot delete. User not found with ID: " + userId));
        userRepository.delete(user);
    }

    }
