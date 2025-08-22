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
}
