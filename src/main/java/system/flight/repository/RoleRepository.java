package system.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.flight.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
        boolean existsByRoleName(String roleName);
}
