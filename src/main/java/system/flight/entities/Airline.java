package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airlines")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Airline {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id_pk")
    private int airlineId;

    @Column(name="name",nullable = false, length=50)
    private String name;

    @Column(name="headquarter",length=50)
    private String headquartersCity;


    @Column(name = "code", length = 10)
    private String code;

    @ManyToOne
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public boolean isDeleted() {
        return Boolean.TRUE.equals(this.isDeleted);
    }

}

