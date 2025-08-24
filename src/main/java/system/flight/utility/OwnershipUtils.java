package system.flight.utility;
import org.springframework.security.access.AccessDeniedException;
import system.flight.entities.User;



public class OwnershipUtils {
    //Logic for ownership validation
    public static void validateOwnership(User owner, User currentUser) {
        if (!(owner.getUserId() == currentUser.getUserId())) {
            throw new AccessDeniedException("You are not authorized to perform this action.");
        }
    }

    public static boolean isPrivilegedRole(int roleId) {
        return roleId == 1
                || roleId == 2
                || roleId == 3;
    }

    public static boolean isPrivilegedRoleAdmin(int roleId) {
        return roleId == 1;
    }

}
