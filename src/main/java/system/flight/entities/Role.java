package system.flight.entities;

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
        private List<User> users;

}
