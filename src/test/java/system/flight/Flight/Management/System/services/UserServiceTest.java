package system.flight.Flight.Management.System.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import system.flight.dto.*;
import system.flight.entities.Role;
import system.flight.entities.User;
import system.flight.exception.*;
import system.flight.mapper.UserMapper;
import system.flight.repository.RoleRepository;
import system.flight.repository.UserRepository;
import system.flight.services.UserService;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private User mockUser;
    private Role mockRole;

    @BeforeEach
    void setup() {
        mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setUsername("testuser");
        mockUser.setEmailId("test@example.com");
        mockUser.setIsDeleted(false);

        mockRole = new Role();
        mockRole.setRoleId(1);
        mockRole.setRoleName("USER");
    }

    @Test
    void testRegisterUser_Success() {
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUsername("newuser");
        dto.setEmailId("new@example.com");
        dto.setPassword("password");
        dto.setRoleId(1);

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmailId(dto.getEmailId())).thenReturn(false);
        when(roleRepository.findById(dto.getRoleId())).thenReturn(Optional.of(mockRole));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            mockedMapper.when(() -> UserMapper.toEntity(any(), any(), any(), any())).thenReturn(mockUser);
            mockedMapper.when(() -> UserMapper.toDto(any())).thenReturn(new CreateUserResponseDTO());

            CreateUserResponseDTO response = userService.registerUser(dto);
            assertNotNull(response);
        }
    }

    @Test
    void testGetUserById_Success() {
        mockSecurityContext("testuser");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(1);
        assertEquals(mockUser.getUsername(), result.getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        mockSecurityContext("testuser");

        when(userRepository.findById(1)).thenReturn(Optional.empty());
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1));
    }

    @Test
    void testGetAllUsers_FiltersDeleted() {
        User deletedUser = new User();
        deletedUser.setIsDeleted(true);

        when(userRepository.findAll()).thenReturn(List.of(mockUser, deletedUser));

        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertFalse(result.get(0).getIsDeleted());
    }

    @Test
    void testDeleteUserById_Success() {
        mockSecurityContext("testuser");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        userService.deleteUserById(1);
        verify(userRepository).save(mockUser);
        assertTrue(mockUser.getIsDeleted());
    }

    @Test
    void testGetUserByUsername_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        User result = userService.getUserByUsername("testuser");
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetUserByUsername_NotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByUsername("unknown"));
    }

    // Utility to mock SecurityContextHolder
    private void mockSecurityContext(String username) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
    }
}