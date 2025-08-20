package system.flight.mapper;

import system.flight.dto.RolesDTO;
import system.flight.entities.Role;

public class RoleMapper {
 public static RolesDTO toDTO(Role role){
     return new RolesDTO(role.getRoleId(),role.getRoleName());
 }

 public static Role toEntity(RolesDTO roleDTO){
     Role role=new Role();
     role.setRoleId(roleDTO.getRoleId());
     role.setRoleName(roleDTO.getRoleName());
     return role;
 }

}
