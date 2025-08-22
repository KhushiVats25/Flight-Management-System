package system.flight.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role
{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "role_id")
        private int roleId;

        @Column(name = "role_name", nullable = false, unique = true, length = 50)
        private String roleName;

        @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
        @JsonManagedReference
        private List<User> users;

        @Column(name = "is_deleted")
        private Boolean isDeleted = false;

        public boolean isDeleted() {
                return Boolean.TRUE.equals(this.isDeleted);
        }


}
