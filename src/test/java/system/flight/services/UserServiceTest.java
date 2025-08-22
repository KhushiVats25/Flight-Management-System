package system.flight.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import system.flight.dto.CreateUserDTO;
import system.flight.dto.UserProfileDTO;
import system.flight.entities.Role;
import system.flight.entities.User;
import system.flight.exception.FileStorageException;
import system.flight.exception.ResourceAlreadyExistsException;
import system.flight.exception.ResourceNotFoundException;
import system.flight.repository.RoleRepository;
import system.flight.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private Role role;
    private User user;

    @BeforeEach
    void setup() {
        role = new Role();
        role.setRoleId(1);
        role.setRoleName("USER");

        user = new User();
        user.setUserId(1);
        user.setUsername("bollinasai");
        user.setEmailId("sai@example.com");
        user.setRole(role);
        user.setDateOfBirth(LocalDate.of(2000, 1, 1));
    }

    @Test
    void testRegisterUser_Success() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("bollinasai");
        dto.setEmailId("sai@example.com");
        dto.setPassword("securePass");
        dto.setRoleId(1);

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmailId(dto.getEmailId())).thenReturn(false);
        when(roleRepository.findById(dto.getRoleId())).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.registerUser(dto);

        assertNotNull(response);
        assertEquals("bollinasai", response.getUsername());
    }

    @Test
    void testRegisterUser_UsernameExists() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("bollinasai");

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () ->
                userService.registerUser(dto));
    }

    @Test
    void testRegisterUser_EmailExists() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("newuser");
        dto.setEmailId("sai@example.com");

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmailId(dto.getEmailId())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () ->
                userService.registerUser(dto));
    }

    @Test
    void testRegisterUser_RoleNotFound() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("newuser");
        dto.setEmailId("new@example.com");
        dto.setRoleId(99);

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmailId(dto.getEmailId())).thenReturn(false);
        when(roleRepository.findById(dto.getRoleId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.registerUser(dto));
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1);
        assertEquals("bollinasai", found.getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                userService.getUserById(1));
    }

    @Test
    void testUpdateUserProfile_Success() throws IOException {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setAddress("New Address");
        dto.setPhoneNo("9999999999");

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getBytes()).thenReturn("image".getBytes());
        dto.setProfileImage(mockFile);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.updateUserProfile(1, dto);

        assertNotNull(response);
        assertEquals("New Address", response.getAddress());
        assertEquals("9999999999", response.getPhoneNo());
        assertNotNull(response.getProfileImage());
        assertEquals("path/to/image/storage/1.jpg", response.getProfileImage());
    }

    @Test
    void testUpdateUserProfile_UserNotFound() {
        UserProfileDTO dto = new UserProfileDTO();
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.updateUserProfile(1, dto));
    }

    @Test
    void testUpdateUserProfile_FileStorageException() throws IOException {
        UserProfileDTO dto = new UserProfileDTO();
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getBytes()).thenThrow(new IOException("Failed"));
        dto.setProfileImage(mockFile);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(FileStorageException.class, () ->
                userService.updateUserProfile(1, dto));
    }

    @Test
    void testDeleteUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        assertDoesNotThrow(() -> userService.deleteUserById(1));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                userService.deleteUserById(1));
    }
}

