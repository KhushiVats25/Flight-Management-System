//package system.flight.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import system.flight.entities.Role;
//import system.flight.entities.User;
////import system.flight.mapper.UserMapper;
//import system.flight.repository.RoleRepository;
//import system.flight.repository.UserRepository;
//import system.flight.utility.Utils;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
//
//    public UserResponseDTO registerUser(UserRegistrationDTO dto) throws IllegalArgumentException {
//
//
//        // Validate unique email
//        if (userRepository.existsByEmailId(dto.getEmailId())) {
//            throw new ResourceAlreadyExistsException("Email", "emailId", dto.getEmailId());
//        }
//
//        // Check whether role is present or not
//        Role role = roleRepository.findById(dto.getRoleId())
//                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + dto.getRoleId()));
//
//        // Generate password salt and hash
//        String salt = Utils.generateSalt();
//        String newPassword = salt + dto.getPassword();
//        String hashedPassword = Utils.generateHash(newPassword);
//
//        // Map DTO to User entity
//        User userToSave = UserMapper.toEntity(dto, role, salt, hashedPassword);
//
//        User savedUser = userRepository.save(userToSave);
//
//        // Return saved user converted to DTO
//        return UserMapper.toDto(savedUser );
//    }
//
//}
