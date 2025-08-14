package system.flight.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name="full_name", nullable=false, length = 70)
    private String fullName;

    @Column(name="date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name="gender", length=1)
    private String gender;

    @Column(name="username", nullable=false, unique=true, length = 30)
    private String username;

    @Column(name="password_salt", nullable = false, length = 10)
    private String passwordSalt;

    @Column(name="password_hash", nullable = false, length = 64)
    private String passwordHash;

    @Column(name="address", length = 150)
    private String address;

    @Column(name="email_id", nullable = false, unique=true, length=100)
    private String emailId;

    @Column(name= "phone_no", length = 10)
    private String phoneNo;



    @Column(name="created_at" , nullable=false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="auth_status" , nullable = false)
    private boolean authStatus;


    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @ElementCollection
    @CollectionTable(name="user_id_docs", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "doc_url",length = 100)
    private List<String> iDocs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id", nullable= false)
    private Role role;

}
