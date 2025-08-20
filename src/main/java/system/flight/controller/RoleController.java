package system.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.flight.dto.ApiResponseDTO;
import system.flight.dto.RolesDTO;
import system.flight.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RolesDTO>>> getAllRoles() {
        List<RolesDTO> rolesList = roleService.getAllRoles();
        ApiResponseDTO<List<RolesDTO>> apiResponseDTO = new ApiResponseDTO<>(
                HttpStatus.OK.value(),
                "Roles retrieved successfully",
                rolesList
        );
        return ResponseEntity.ok(apiResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RolesDTO>> getRoleById(@PathVariable int id) {
        RolesDTO rolesDTO = roleService.getRoleById(id);
        ApiResponseDTO<RolesDTO> responseDTO = new ApiResponseDTO<>(
                HttpStatus.OK.value(),
                "Role retrieved successfully",
                rolesDTO
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<RolesDTO>> updateRole(@PathVariable int id, @RequestBody RolesDTO updatedRoleDTO) {
        RolesDTO updatedRole = roleService.updateRole(id, updatedRoleDTO);
        ApiResponseDTO<RolesDTO> responseDTO = new ApiResponseDTO<>(
                HttpStatus.OK.value(),
                "Role updated successfully",
                updatedRole
        );
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        ApiResponseDTO<Void> responseDTO = new ApiResponseDTO<>(
                HttpStatus.OK.value(),
                "Role deleted successfully",
                null
        );
        return ResponseEntity.ok(responseDTO);
    }
}