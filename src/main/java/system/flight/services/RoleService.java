package system.flight.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.flight.dto.RolesDTO;
import system.flight.entities.Role;
import system.flight.exception.ResourceNotFoundException;
import system.flight.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public List<RolesDTO> getAllRoles(){
        List<Role>roles=roleRepository.findAll();
        if(roles.isEmpty()){
            throw new ResourceNotFoundException("Roles not found");

        }
        return roles.stream().
                map(r->new RolesDTO(r.getRoleId(),r.getRoleName()))
                .toList();
    }


    public RolesDTO getRoleById(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));

        return new RolesDTO(role.getRoleId(), role.getRoleName());
    }
    public RolesDTO updateRole(int id, RolesDTO updatedRoleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));

        role.setRoleName(updatedRoleDTO.getRoleName()); // Update fields as needed
        Role savedRole = roleRepository.save(role);

        return new RolesDTO(savedRole.getRoleId(), savedRole.getRoleName());
    }

    public void deleteRole(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));

        roleRepository.delete(role);
    }



}
